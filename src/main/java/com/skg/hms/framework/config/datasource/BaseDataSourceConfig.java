package com.skg.hms.framework.config.datasource;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class BaseDataSourceConfig {

	SqlSessionFactory getSqlSessionFactory(DataSource dataSource) throws Exception {
		String mapperConfigPath = "classpath:mapper/mybatis/MapperConfig.xml";
		String mapperPath = "classpath:mapper/mybatis/sql/**/**/*.xml";
		
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource configResource = resolver.getResource(mapperConfigPath);
		Resource[] mapperResources = resolver.getResources(mapperPath);
		
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource);
		factoryBean.setConfigLocation(configResource);
		factoryBean.setMapperLocations(mapperResources);
		factoryBean.afterPropertiesSet();
		
		return factoryBean.getObject();
	}
}
