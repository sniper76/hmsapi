package com.skg.hms.framework.exception;

import java.util.List;

import com.skg.hms.framework.dto.ErrorFieldDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DataBindingException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 91001L;

	public DataBindingException() {
		super();
	}
	
	public DataBindingException(String message) {
		super(message);
	}
	
	public DataBindingException(String message, List<ErrorFieldDto> fieldErrorList) {
		super(message, fieldErrorList);
	}
}
