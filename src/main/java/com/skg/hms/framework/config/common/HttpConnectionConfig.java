package com.skg.hms.framework.config.common;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

@Configuration
public class HttpConnectionConfig {

	@Bean
	public OkHttpClient okHttpClient() {
		return new OkHttpClient().newBuilder()
				.retryOnConnectionFailure(false)
				.connectionPool(pool())
				.connectTimeout(3, TimeUnit.SECONDS)
				.readTimeout(10, TimeUnit.SECONDS)
				.writeTimeout(10, TimeUnit.SECONDS)
				.build();
	}
	
	@Bean
	public ConnectionPool pool() {
		return new ConnectionPool(10, 5, TimeUnit.MINUTES);
	}
}
