package com.netease.km.configuration;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.netease.km.common.DataSourceKey;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@MapperScan(basePackages = "com.netease.km.mapper", sqlSessionTemplateRef = "sqlSessionTemplate")
public class DataSourceConfig {

	@Autowired
	private Environment env;

	@Bean("commondataSource")
	@Primary
	@ConfigurationProperties(prefix = "common.datasource")
	public DataSource commonDataSource() {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName(env.getProperty("common.datasource.driver-class-name"));
		dataSource.setJdbcUrl(env.getProperty("common.datasource.url"));
		dataSource.setUsername(env.getProperty("common.datasource.username"));
		dataSource.setPassword(env.getProperty("common.datasource.password"));
//		 DataSource dataSource = DataSourceBuilder.create().type(HikariDataSource.class).build();
		return dataSource;
	}

	@Bean("neteaseDataSource")
	// @ConfigurationProperties(prefix = "netease.spring.datasource")
	public DataSource neteaseDataSource() {
		HikariDataSource neteaseDataSource = new HikariDataSource();
		neteaseDataSource.setDriverClassName(env.getProperty("netease.datasource.driver-class-name"));
		neteaseDataSource.setJdbcUrl(env.getProperty("netease.datasource.url"));
		neteaseDataSource.setUsername(env.getProperty("netease.datasource.username"));
		neteaseDataSource.setPassword(env.getProperty("netease.datasource.password"));
		return neteaseDataSource;
		// return DataSourceBuilder.create().build();
	}

	@Bean("dynamicDataSource")
	public DataSource dynamicDataSource() {
		DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
		Map<Object, Object> dataSourceMap = new HashMap<>(2);
		dataSourceMap.put(DataSourceKey.common.name(), commonDataSource());
		dataSourceMap.put(DataSourceKey.test.name(), commonDataSource());
		dataSourceMap.put(DataSourceKey.test2.name(), neteaseDataSource());
		dataSourceMap.put(DataSourceKey.netease.name(), neteaseDataSource());
		// 默认数据源
		dynamicRoutingDataSource.setDefaultTargetDataSource(commonDataSource());
		dynamicRoutingDataSource.setTargetDataSources(dataSourceMap);
		return dynamicRoutingDataSource;
	}

	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		return bean.getObject();
	}

	@Bean(name = "transactionManager")
	public DataSourceTransactionManager transactionManager(@Qualifier("dynamicDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean(name = "sqlSessionTemplate")
	public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory)
			throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

}
