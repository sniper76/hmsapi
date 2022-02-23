package com.skg.hms.framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Order(value=0)
@Component
public class CustomRequestFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		ContentCachingRequestWrapper rWapper = new ContentCachingRequestWrapper((HttpServletRequest)request);
		log.info("==============CustomRequestFilter==============");
		chain.doFilter(rWapper, response);
	}

}
