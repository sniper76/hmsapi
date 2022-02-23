package com.skg.hms.service.api.hmsmob.pay.service;

import com.skg.hms.service.api.hmsmob.pay.dto.OrderBookInputDto;
import com.skg.hms.service.api.hmsmob.pay.dto.OrderBookOutputDto;

public interface PayService {
	public OrderBookOutputDto selectList(OrderBookInputDto inDto) throws Exception;
}
