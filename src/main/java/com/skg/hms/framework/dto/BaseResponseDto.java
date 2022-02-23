package com.skg.hms.framework.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BaseResponseDto {
	private Boolean status;
	private String message;
}
