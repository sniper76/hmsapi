package com.skg.hms.framework.config.datasource;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.skg.hms.framework.properties.BaseDatabaseProperties;
import com.skg.hms.framework.utils.ObjectUtils;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import net.sf.log4jdbc.log.slf4j.Slf4jSpyLogDelegator;
import net.sf.log4jdbc.sql.jdbcapi.DataSourceSpy;

//@Configuration
public class DataSourceConfig {

	@Autowired
	@Qualifier(value = "masterDatabaseProperties")
	private BaseDatabaseProperties masterDatabaseProperties;
	
	@Bean
	@Primary
	public DataSource masterDataSource() {
		System.out.println("######");
		System.out.println(this.masterDatabaseProperties.toString());
		System.out.println("######");
		DataSourceSpy dataSourceSpy = new DataSourceSpy(new HikariDataSource(this.setHikariConfig(this.masterDatabaseProperties)));
		dataSourceSpy.setLogDelegator(new Slf4jSpyLogDelegator());
		return dataSourceSpy;
	}
	
	private <T extends BaseDatabaseProperties> HikariConfig setHikariConfig(T databasePropreties) {
		HikariConfig hikariConfig = new HikariConfig();
		
		if(ObjectUtils.isEmpty(databasePropreties.getJndiName())) {
			hikariConfig.addDataSourceProperty("username", databasePropreties.getUserName());
			hikariConfig.addDataSourceProperty("password", databasePropreties.getPassword());			
			hikariConfig.setDriverClassName(databasePropreties.getDriverClassName());
			hikariConfig.setJdbcUrl(databasePropreties.getUrl());
			hikariConfig.setMinimumIdle(databasePropreties.getMinimumIdle());
			hikariConfig.setMaximumPoolSize(databasePropreties.getMaximumPoolSize());
			hikariConfig.setMaxLifetime(databasePropreties.getMaxLifetime());
			hikariConfig.setIdleTimeout(databasePropreties.getIdleTimeout());
			hikariConfig.setConnectionTimeout(databasePropreties.getConnectionTimeout());
		}
		else {
			hikariConfig.setDataSourceJNDI(databasePropreties.getJndiName());
		}
		return hikariConfig;
	}
}
