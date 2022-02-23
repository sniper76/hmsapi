package com.skg.hms.framework.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BaseDatabaseProperties {
	private String driverClassName;
	private String url;
	private String userName;
	private String password;
	private String testQuery;
	private String poolName;
	private int minimumIdle;
	private int maximumPoolSize;
	private long maxLifetime;
	private long idleTimeout;
	private long connectionTimeout;
	private String jndiName;
}
