package com.skg.hms.service.common.dto;

import com.skg.hms.framework.dto.HpsResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto<T> extends HpsResponseDto {
	private T responseData;
}
