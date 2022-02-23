package com.skg.hms.service.api.hmsmob.com.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.skg.hms.framework.annotation.DBSchema;
import com.skg.hms.service.common.dto.ApiDto;

@Mapper
@DBSchema
@Repository
public interface LogMapper {
	public void insertLog(ApiDto apiDto);
}
