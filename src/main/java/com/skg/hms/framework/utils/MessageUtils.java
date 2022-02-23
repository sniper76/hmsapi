package com.skg.hms.framework.utils;

import java.util.Locale;

import org.springframework.context.support.MessageSourceAccessor;

public class MessageUtils {
	static private MessageSourceAccessor messageSourceAccessor;
	
	public void setMessageSourceAccessor(MessageSourceAccessor msa) {
		MessageUtils.messageSourceAccessor = msa;
	}

	public static String getMessage(String code) {
		return getMessage(code, new Object[] {});
	}

	public static String getMessage(String code, Object[] args) {
		return messageSourceAccessor.getMessage(code, args);
	}

	public static String getMessage(String code, Object[] args, Locale locale) {
		return messageSourceAccessor.getMessage(code, args, locale);
	}

	public static String getMessage(String code, Locale locale) {
		return messageSourceAccessor.getMessage(code, locale);
	}
	
	public static String getMessage(String code, String lang) {
		return messageSourceAccessor.getMessage(code, new Locale(lang));
	}
}
