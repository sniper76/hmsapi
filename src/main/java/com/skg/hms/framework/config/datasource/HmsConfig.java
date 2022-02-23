package com.skg.hms.framework.config.datasource;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.skg.hms.framework.annotation.DBSchema;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "com.skg.hms.service", annotationClass = DBSchema.class, sqlSessionTemplateRef = "hmsSqlSessionTemplate")
public class HmsConfig extends BaseDataSourceConfig {

	@Bean
	public SqlSessionFactory hmsSqlSessionFactory(@Qualifier(value="masterDataSource") DataSource dataSource) throws Exception {
		return this.getSqlSessionFactory(dataSource);
	}
	@Bean
	public SqlSessionTemplate hmsSqlSessionTemplate(@Qualifier(value="masterDataSource") DataSource dataSource) throws Exception {
		return new SqlSessionTemplate(this.hmsSqlSessionFactory(dataSource));
	}
	@Bean
	public DataSourceTransactionManager hmsTransactionManager(@Qualifier(value="masterDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
