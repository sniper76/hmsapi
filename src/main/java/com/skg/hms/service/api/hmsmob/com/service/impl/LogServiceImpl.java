package com.skg.hms.service.api.hmsmob.com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skg.hms.service.api.hmsmob.com.mapper.LogMapper;
import com.skg.hms.service.api.hmsmob.com.service.LogService;
import com.skg.hms.service.common.dto.ApiDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LogServiceImpl implements LogService {
	
	@Autowired
	private LogMapper logMapper;

	@Override
	public void insertLog(ApiDto apiDto) {
		log.info("LogServiceImpl logMapper : {}", logMapper);
		logMapper.insertLog(apiDto);
	}

}
