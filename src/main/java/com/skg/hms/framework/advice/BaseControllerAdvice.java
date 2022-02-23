package com.skg.hms.framework.advice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.web.firewall.FirewalledRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.skg.hms.framework.dto.BaseResponseDto;
import com.skg.hms.framework.dto.ErrorResponseDto;
import com.skg.hms.framework.exception.JwtAllException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class BaseControllerAdvice {

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public Object handleException(HttpServletRequest request, HttpServletResponse response, Exception e) {
		log.error("handleException : {}", e.getLocalizedMessage());
		String requestURL = request.getRequestURI();
		
		if(requestURL.startsWith("/apis/hmsmob")) {
			return errorHmsmobi(request, e);
		}
		BaseResponseDto resDto = new BaseResponseDto();
		resDto.setStatus(false);
		resDto.setMessage(e.getMessage());
		return resDto;
	}

	@ExceptionHandler(value = NoHandlerFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	public BaseResponseDto requestHandlingNoHandleException(HttpServletRequest request, HttpServletResponse response, Exception e) {
		BaseResponseDto resDto = new BaseResponseDto();
		resDto.setStatus(false);
		resDto.setMessage(e.getMessage());
		return resDto;
	}

	private ErrorResponseDto errorHmsmobi(HttpServletRequest request, Exception e, String msg) {
		String trcNo = "";
		ObjectMapper mapper = new ObjectMapper();
		try {
			HttpServletRequest newRequest = null;
			if (request != null) {
				if(request instanceof FirewalledRequest) {
					newRequest = (HttpServletRequest)((FirewalledRequest) request).getRequest();
				}
				else {
					newRequest = request;
				}
			}
			ContentCachingRequestWrapper rWapper = (ContentCachingRequestWrapper)newRequest;
			
			JsonNode jsonNode = mapper.readTree(rWapper.getContentAsByteArray());
			if (rWapper.getContentType() != null && rWapper.getContentType().contains("appliaction/json")
				&& jsonNode != null && jsonNode.toString().length() != 0) {
				trcNo = jsonNode.get("trcNo").toString();
			}
		}
		catch(Exception el) {
			log.error("errorHmsmobi error : {}", el.getLocalizedMessage());
		}
		return ErrorResponseDto.builder().resCode("9999").detailMsg(msg).trcNo(trcNo).build();
	}
	
	private ErrorResponseDto errorHmsmobi(HttpServletRequest request, Exception e) {
		return errorHmsmobi(request, e, e.getLocalizedMessage());
	}

	@ExceptionHandler(value = {HttpMessageNotReadableException.class, MismatchedInputException.class})
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Object handleJsonParseException(HttpServletRequest request, Exception e) {
		log.error("handleJsonParseException : {}", e.getLocalizedMessage());
		String requestURL = request.getRequestURI();
		String msg = "요청 데이터의 형식이 올바르지 않습니다.";
		
		if(requestURL.startsWith("/apis/syrup/")) {
			return errorHmsmobi(request, e);
		}
		else if(requestURL.startsWith("/apis/muffin/")) {
			return errorHmsmobi(request, e);
		}
		if(requestURL.startsWith("/apis/hmsmob")) {
			return errorHmsmobi(request, e, msg);
		}
		BaseResponseDto resDto = new BaseResponseDto();
		resDto.setStatus(false);
		resDto.setMessage(msg);
		return resDto;
	}

	@ExceptionHandler({JwtAllException.class})
	@ResponseBody
	public Object handleJwtException(HttpServletRequest request, HttpServletResponse response, JwtAllException e) {
		log.error("handleJsonParseException : {}", e.getLocalizedMessage());
		String trcNo = "";
		String responseBody = "";
		
		try {
			responseBody = getRequestBody(request);
			if (request.getContentType() != null && request.getContentType().contains("application/json")
					&& !"".equals(responseBody)) {
				ObjectMapper mapper = new ObjectMapper();
				JsonNode jsonNode = mapper.readTree(responseBody);
				trcNo = strCut(jsonNode.get("trcNo"));
			}
		}
		catch(Exception el) {
			log.error("handleJwtException : {}", el.getLocalizedMessage());
		}
		return ErrorResponseDto.builder()
				.resCode(e.getResCode())
				.detailMsg(e.getLocalizedMessage())
				.trcNo(trcNo)
				.build();
	}
	
	private String getRequestBody(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		InputStream is = null;
		try {
			is = request.getInputStream();
			if(is != null) {
				br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
				char[] c = new char[128];
				int b = -1;
				while ((b = br.read(c)) > 0) {
					sb.append(c, 0, b);
				}
			}
			else {
				sb.append("");
			}
		}
		catch(IOException io) {
			
		}
		finally {
			if(is != null) {
				try {
					is.close();
				}
				catch(IOException ioe) {
					
				}
			}
			if(br != null) {
				try {
					br.close();
				}
				catch(IOException ioe) {
					
				}
			}
		}
		return sb.toString();
	}
	
	private String strCut(Object obj) {
		return strCut(String.valueOf(obj));
	}
	
	private String strCut(String str) {
		String retStr = str;
		if( retStr.subSequence(0, 1).equals("\"") ) {
			retStr = retStr.substring(1);
			
			if( retStr.substring(retStr.length()-1).equals("\"") ) {
				retStr = retStr.substring(0, retStr.length()-1);
			}
		}
		return retStr;
	}
}
