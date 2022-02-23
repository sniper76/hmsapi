package com.skg.hms.framework.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HpsRequestDto {
	private String serviceCode;
	private String chnl;
	private String version;
	private String trcNo;
	private RequestPagingDto pageInfo;
}
