package com.skg.hms.framework.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RequestPagingDto {
	private String startDate;
	private String endDate;
	private Integer page;
	private Integer pageSize;
}
