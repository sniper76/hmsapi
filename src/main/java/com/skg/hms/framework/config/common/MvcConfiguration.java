package com.skg.hms.framework.config.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.skg.hms.framework.auth.interceptor.JwtMngInterceptor;

@Configuration
@EnableWebMvc
@ComponentScan
public class MvcConfiguration implements WebMvcConfigurer {
	@Autowired
	private JwtMngInterceptor jwtMngInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(this.jwtMngInterceptor)
		.addPathPatterns("/apis/hmsmob/**")
		.excludePathPatterns("/apis/hmsmob/comm/reqLogin","/apis/hmsmob/comm/checkVersion")
		.excludePathPatterns("/apis/hmsmob/comm/login","/apis/hmsmob/comm/jwt","/apis/hmsmob/comm/jwt-check","/apis/hmsmob/comm/test");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("resources/**")
		.addResourceLocations("classpath:/static/resources/")
		.setCachePeriod(60 * 60 * 24 * 365);
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		resolver.setViewClass(JstlView.class);
		registry.viewResolver(resolver);
	}	

//	@Override
//	protected void configureViewResolvers(ViewResolverRegistry registry) {
////		super.configureViewResolvers(registry);
//	}
//
//	@Override
//	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
////		super.addResourceHandlers(registry);
//	}
//	
//	@Override
//	protected void addInterceptors(InterceptorRegistry registry) {
////		super.addInterceptors(registry);
//	}

}
