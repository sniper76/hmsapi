package com.skg.hms.framework.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.context.i18n.LocaleContextHolder;

public class DateUtils {

	public static String getCurrentDate() {
		return getFormatDate();
	}
	
	public static String getFormatDate() {
		return getFormatDate("yyyyMMdd");
	}
	
	public static String getFormatDate(String pattern) {
		return getFormatDate(LocalDate.now(), pattern);
	}
	
	public static String getFormatDate(LocalDate now, String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, LocaleContextHolder.getLocale());
		
		return now.format(formatter);
	}
	
	public static String getFormatDateTime() {
		return getFormatDateTime("yyyyMMddHHmmss");
	}
	
	public static String getFormatDateTime(String pattern) {
		return getFormatDateTime(LocalDateTime.now(), pattern);
	}
	
	public static String getFormatDateTime(LocalDateTime now, String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, LocaleContextHolder.getLocale());
		
		return now.format(formatter);
	}
	
	public static LocalDate convertString2Date(String dateStr) {
		return convertStirng2Date(dateStr, "yyyyMMdd");
	}
	
	public static LocalDate convertStirng2Date(String dateStr, String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, LocaleContextHolder.getLocale());
		
		return LocalDate.parse(dateStr, formatter);
	}
}
