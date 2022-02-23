package com.skg.hms.framework.utils;

import org.springframework.core.env.Environment;

import lombok.Getter;

public class ProfileUtils {

	@Getter
	static private PROFILE currentProfile;
	
	public void setEnvironment(Environment env) {
		for (String profile : env.getActiveProfiles()) {
			currentProfile = PROFILE.fromProfileName(profile);
		}
	}
	
	static private Boolean checkProfile(PROFILE profile) {
		return profile.equals(currentProfile);
	}
	
	static public Boolean isLocal() {
		return checkProfile(PROFILE.LOCAL);
	}
	
	static public Boolean isDev() {
		return checkProfile(PROFILE.DEV);
	}
	
	static public Boolean isQA() {
		return checkProfile(PROFILE.QA);
	}
	
	static public Boolean isReal() {
		return checkProfile(PROFILE.REAL);
	}
	
	static public Boolean isProd() {
		return checkProfile(PROFILE.PROD01) || checkProfile(PROFILE.PROD02);
	}
	
	static public Boolean isEtc() {
		return checkProfile(PROFILE.ETC);
	}
	
	public enum PROFILE {
		LOCAL,
		DEV,
		QA,
		REAL,
		PROD,
		PROD01,
		PROD02,
		ETC;
		
		static public PROFILE fromProfileName(String profileName) {
			PROFILE profile = PROFILE.ETC;
			
			for (PROFILE tempProfile : PROFILE.values()) {
				if(tempProfile.name().equals(profileName.toUpperCase())) {
					profile = tempProfile;
				}
			}
			return profile;
		}
	}
}
