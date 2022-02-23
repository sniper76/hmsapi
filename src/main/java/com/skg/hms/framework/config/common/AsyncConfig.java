package com.skg.hms.framework.config.common;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class AsyncConfig {

	@Bean
	public Executor threadPooltaskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(10);
		taskExecutor.setMaxPoolSize(100);
		taskExecutor.setQueueCapacity(200);
		taskExecutor.setThreadNamePrefix("[iCIGNAL-Chatting]-");
		taskExecutor.initialize();
		return taskExecutor;
	}
}
