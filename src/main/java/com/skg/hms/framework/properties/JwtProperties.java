package com.skg.hms.framework.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Component
@ConfigurationProperties(prefix = JwtProperties.PROPERTIES_PREFIX)
public class JwtProperties {
	public static final String PROPERTIES_PREFIX = "jwt";
	private String key;
	private String expiredTime;
}
