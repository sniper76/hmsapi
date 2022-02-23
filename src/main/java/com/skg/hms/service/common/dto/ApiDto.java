package com.skg.hms.service.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApiDto {
	private String mbrshPgmId;
	private String apiId;
	private String tracNo;
	private String ansCd;
	private String ansMsg;
	private Integer reqSeq;
	private String reqDtm;
	private String reqKey;
	private String reqData;
	private String resData;
}
