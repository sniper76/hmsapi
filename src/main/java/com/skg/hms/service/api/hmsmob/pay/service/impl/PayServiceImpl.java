package com.skg.hms.service.api.hmsmob.pay.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skg.hms.service.api.hmsmob.pay.dto.OrderBookInputDto;
import com.skg.hms.service.api.hmsmob.pay.dto.OrderBookOutputDto;
import com.skg.hms.service.api.hmsmob.pay.mapper.PayMapper;
import com.skg.hms.service.api.hmsmob.pay.service.PayService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PayServiceImpl implements PayService {
	
	@Autowired
	private PayMapper payMapper;

	@Override
	public OrderBookOutputDto selectList(OrderBookInputDto inDto) throws Exception {
		log.info("PayServiceImpl inDto : {}", inDto);
		List<OrderBookOutputDto> lst = payMapper.selectList(inDto);
		OrderBookOutputDto resDto = new OrderBookOutputDto();
		resDto.setDataList(lst);
		resDto.setMbrId(inDto.getMbrId());
		resDto.setTransNo(inDto.getTransNo());
		return resDto;
	}

}
