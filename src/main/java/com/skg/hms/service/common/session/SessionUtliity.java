package com.skg.hms.service.common.session;

import java.security.PrivateKey;

import javax.servlet.http.HttpServletRequest;

import com.skg.hms.framework.utils.NetworkUtils;
import com.skg.hms.service.common.session.dto.UserInfo;

public class SessionUtliity {
	public static final String SESSION_USER_INFO = "USER_INFO";
	public static final String SESSION_PRIVATE_KEY = "S_PRIVATE_KEY";
	
	public static UserInfo getUserInfo(HttpServletRequest req) {
		UserInfo userInfo = new UserInfo();
		userInfo.setUsrId("appApi");
		userInfo.setUsrNm("appApi");
		userInfo.setLognId("appApi");
		userInfo.setClientIp(NetworkUtils.getClientIP(req));
		return userInfo;
	}
	
	public static void setUserInfo(UserInfo userInfo, HttpServletRequest req) {
		req.getSession().setAttribute(SESSION_USER_INFO, userInfo);
	}
	
	public static void removeUserInfo(HttpServletRequest req) {
		req.getSession().removeAttribute(SESSION_USER_INFO);
	}
	
	public static String getSessionId(HttpServletRequest req) {
		return req.getSession().getId();
	}
	
	public static int getMaskFlg(HttpServletRequest req) {
		return 1;
	}
	
	public static void savePrivateKey(HttpServletRequest req, PrivateKey privateKey) {
		req.getSession(true).setAttribute(SESSION_PRIVATE_KEY, privateKey);
	}
	
	public static PrivateKey getRsaPrivateKey(HttpServletRequest req) {
		return (PrivateKey) req.getSession(false).getAttribute(SESSION_PRIVATE_KEY);
	}
}
