package com.skg.hms.service.api.hmsmob.pay.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.skg.hms.framework.annotation.DBSchema;
import com.skg.hms.service.api.hmsmob.pay.dto.OrderBookInputDto;
import com.skg.hms.service.api.hmsmob.pay.dto.OrderBookOutputDto;

@Mapper
@DBSchema
@Repository
public interface PayMapper {

	public List<OrderBookOutputDto> selectList(OrderBookInputDto inDto);
}
