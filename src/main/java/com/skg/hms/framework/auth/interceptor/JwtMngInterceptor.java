package com.skg.hms.framework.auth.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.skg.hms.framework.auth.JwtUtils;
import com.skg.hms.framework.exception.JwtAllException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtMngInterceptor implements HandlerInterceptor {

	@Autowired
	private JwtUtils jwtUtils;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String uri = request.getRequestURI();
		String token = request.getHeader("Authorization");
		log.info("URI : {}", uri);
		if(token == null || "".equals(token)) {
			throw new JwtAllException("Token이 존재하지 않습니다.", "002");
		}
		this.jwtUtils.verifyToken(token);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
