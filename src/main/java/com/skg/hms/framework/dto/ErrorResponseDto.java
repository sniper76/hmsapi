package com.skg.hms.framework.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class ErrorResponseDto extends HpsResponseDto {
	@Builder
	public ErrorResponseDto(String resCode, String detailMsg, String trcNo, String token) {
		this.setResCode(resCode);
		this.setDetailMsg(detailMsg);
		this.setTrcNo(trcNo);
	}
}
