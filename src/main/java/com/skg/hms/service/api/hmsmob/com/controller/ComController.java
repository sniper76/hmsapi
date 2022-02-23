package com.skg.hms.service.api.hmsmob.com.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skg.hms.framework.auth.JwtUtils;
import com.skg.hms.framework.dto.JwtResponseDto;
import com.skg.hms.framework.dto.UserDto;
import com.skg.hms.service.common.dto.RequestDto;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping({"/apis/hmsmob/comm"})
public class ComController {

	@Autowired
	private JwtUtils jwtUtils;
	
	@PostMapping(value = "/jwt")
	public JwtResponseDto createJwt(@RequestParam(required=false) String expTerm) {
		log.info("jwt expTerm : {}", expTerm);
		UserDto userDto = new UserDto();
		userDto.setMbrId("testUser");
		
		JwtResponseDto jwtDto = JwtResponseDto.builder().resCode("0000").detailMsg("정상")
				.token(jwtUtils.createToken(userDto, expTerm)).build();
		
		return jwtDto;
	}
	
	@PostMapping(value = "/jwt-check")
	public JwtResponseDto verifyJwt(@RequestParam String token) {
		Claims claims = jwtUtils.verifyToken(token);
		
		if(claims != null) {
			jwtUtils.checkRefreshToken(claims);
		}
		JwtResponseDto jwtDto = JwtResponseDto.builder().resCode("0000").detailMsg("정상")
				.build();
		
		return jwtDto;
	}

	@PostMapping(value = "/test", consumes = "application/json")
	public Map<String, Object> hpsTest(@RequestBody RequestDto<UserDto> testDto, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = convertDtoToMap(testDto);
		return map;
	}
	
	@SuppressWarnings("unchecked")
	private <T> Map<String, Object> convertDtoToMap(T t) {
		Map<String, Object> oriMap = new ObjectMapper().convertValue(t, Map.class);
		Map<String, Object> rtnMap = new HashMap<>();
		
		rtnMap.putAll(oriMap);
		rtnMap.putAll((Map<String, Object>)oriMap.get("requestData"));
		rtnMap.remove("requestData");
		
		return rtnMap;
	}
}
