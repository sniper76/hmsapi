package com.skg.hms.framework.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ErrorFieldDto {
	private String field;
	private String value;
	private String type;
	private String reason;
}
