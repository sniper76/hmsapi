package com.skg.hms.service.api.hmsmob.pay.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skg.hms.service.api.hmsmob.pay.dto.OrderBookInputDto;
import com.skg.hms.service.api.hmsmob.pay.dto.OrderBookOutputDto;
import com.skg.hms.service.api.hmsmob.pay.service.PayService;
import com.skg.hms.service.common.aop.ApiLog;
import com.skg.hms.service.common.dto.RequestDto;
import com.skg.hms.service.common.dto.ResponseDto;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping({"/apis/hmsmob/pay"})
public class PayController {
	
	@Autowired
	private PayService payService;

	@ApiLog(apiId = "orderBook", reqKey = "mbrId")
	@ApiOperation("상태별 orderBook 조회")
	@ApiImplicitParams({
		  @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
		})
	@PostMapping(value = "/orderBook", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseDto<OrderBookOutputDto> orderBook(@RequestBody RequestDto<OrderBookInputDto> reqDto
			, HttpServletRequest request, HttpServletResponse response) {
		/**
{
  "resCode": "0000",
  "detailMsg": "정상",
  "trcNo": null,
  "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJITVMgVG9rZW4iLCJleHAiOjE2NTQyNDg0NDgsInVzZXIiOnsibWJySWQiOiJ0ZXN0VXNlciJ9fQ.z35xgFI3Hd9zgbuTHIZKCpMWYeXuJ_6Fun1JGHD0V8o"
}
		 */
		ResponseDto<OrderBookOutputDto> resDto = new ResponseDto<OrderBookOutputDto>();
		log.info("orderBook reqDto : {}", reqDto);
		try {
			resDto.setResponseData((OrderBookOutputDto)payService.selectList((OrderBookInputDto)reqDto.getRequestData()));
			log.info("orderBook resDto : {}", resDto);
		}
		catch (Exception e) {
			log.error("error : {}", e.getLocalizedMessage());
			resDto.setResCode("9999");
			resDto.setTrcNo(reqDto.getTrcNo());
			resDto.setDetailMsg("시스템 내부 처리 오류");
		}
		return resDto;
	}

	@ApiLog(apiId = "hello", reqKey = "mbrId")
	@ApiOperation("상태별 hello 조회")
	@GetMapping(value = "/hello")
	public ResponseDto<OrderBookOutputDto> hello(HttpServletRequest request, HttpServletResponse response) {
		ResponseDto<OrderBookOutputDto> resDto = new ResponseDto<OrderBookOutputDto>();
		try {
			resDto.setResponseData((OrderBookOutputDto)payService.selectList(null));
			log.info("hello resDto : {}", resDto);
		}
		catch (Exception e) {
			log.error("error : {}", e.getLocalizedMessage());
			resDto.setResCode("9999");
			resDto.setTrcNo("");
			resDto.setDetailMsg("시스템 내부 처리 오류");
		}
		return resDto;
	}
}
