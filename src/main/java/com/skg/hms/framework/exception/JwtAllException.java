package com.skg.hms.framework.exception;

public class JwtAllException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 90003L;
	private String resCode = "001";
	
	public JwtAllException() {
		
	}
	public JwtAllException(String message) {
		super(message);
	}
	public JwtAllException(String message, String resCode) {
		super(message);
		this.resCode = resCode;
	}
	public String getResCode() {
		return resCode;
	}
	public void setResCode(String resCode) {
		this.resCode = resCode;
	}
	public String toString() {
		return "JwtAllException(resCode=" + getResCode() + ")";
	}
}
