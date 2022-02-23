package com.skg.hms.framework.exception;

import java.util.List;
import java.util.stream.Collectors;

import com.skg.hms.framework.dto.ErrorFieldDto;
import com.skg.hms.framework.utils.MessageUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BaseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 90000L;
	
	private List<ErrorFieldDto> fieldErrorList;
	
	public BaseException() {
		super();
	}

	public BaseException(String message) {
		super(message);
	}
	
	public BaseException(String message, List<ErrorFieldDto> fieldErrorList) {
		super(message);
		this.fieldErrorList = fieldErrorList;
	}
	
	public String getErrorFieldMessages() {
		return this.fieldErrorList.stream()
		.map(sourceDto -> MessageUtils.getMessage(sourceDto.getReason()))
		.collect(Collectors.joining("\n"));
	}
}
