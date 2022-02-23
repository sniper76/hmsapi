package com.skg.hms.service.common.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.firewall.FirewalledRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skg.hms.framework.utils.DateUtils;
import com.skg.hms.service.api.hmsmob.com.service.LogService;
import com.skg.hms.service.common.dto.ApiDto;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class LogAspect {

	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private LogService logService;
	
	@Around("@annotation(com.skg.hms.service.common.aop.ApiLog)")
	public Object requestLogging(ProceedingJoinPoint joinPoint) throws Throwable {
		JsonNode jsonNode = null;
		String requestBody = "";
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		try {
			HttpServletRequest newRequest = null;
			if(request != null) {
				if(request instanceof FirewalledRequest) {
					newRequest = (HttpServletRequest)((FirewalledRequest)request).getRequest();
				}
				else {
					newRequest = request;
				}
			}
			ContentCachingRequestWrapper rWrapper = (ContentCachingRequestWrapper)newRequest;
			requestBody = mapper.readTree(rWrapper.getContentAsByteArray()).toString();
		}
		catch (Exception e) {
			log.error("Api ContentCachingRequestWrapper Error : {}", e.getLocalizedMessage());
		}
		ApiLog apiLog = ((MethodSignature)joinPoint.getSignature()).getMethod().getAnnotation(ApiLog.class);
		try {
			Object result = joinPoint.proceed();
			jsonNode = mapper.valueToTree(result);
			return result;
		}
		finally {
			String reqKey = strCut(jsonNode.get("responseData").get(apiLog.reqKey()));
			if("".equals(reqKey.trim()) || "null".equals(reqKey.trim())) {
				StringBuffer sb = new StringBuffer();
				for(int i=0; i<10; i++) {
					sb.append(String.valueOf((int)(Math.random()*10)));
				}
				reqKey = sb.toString();
			}
			log.info("reqKey : {}", reqKey);
			
			ApiDto apiDto = new ApiDto();
			apiDto.setMbrshPgmId("H");
			apiDto.setApiId(apiLog.apiId());
			apiDto.setTracNo(strCut(jsonNode.get("trcNo")));
			apiDto.setAnsCd(strCut(jsonNode.get("resCode")));
			apiDto.setAnsMsg(strCut(jsonNode.get("detailMsg")));
			apiDto.setReqDtm(DateUtils.getFormatDateTime());
			apiDto.setReqKey(reqKey);
			apiDto.setReqData(requestBody);
			apiDto.setResData(strCut(jsonNode.get("responseData").toString()));

			log.info("logService : {}", logService);
			try {
				//mapper.insertLog
				logService.insertLog(apiDto);
			}
			catch (Exception e) {
				log.error("Api Log Error : {}", e.getLocalizedMessage());
			}
		}
	}
	
	private String strCut(Object obj) {
		return strCut(String.valueOf(obj));
	}
	
	/**
	 * 앞 뒤 따움표 제거
	 */
	private String strCut(String str) {
		String retStr = str;
		if(retStr.substring(0, 1).equals("\"")) {
			retStr = retStr.substring(1);
			
			if(retStr.substring(retStr.length()-1).equals("\"")) {
				retStr = retStr.substring(0, retStr.length()-1);
			}
		}
		return retStr;
	}
}
