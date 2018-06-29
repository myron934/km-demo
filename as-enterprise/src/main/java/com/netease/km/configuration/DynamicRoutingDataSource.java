package com.netease.km.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.netease.km.context.DynamicDataSourceContextHolder;

public class DynamicRoutingDataSource extends AbstractRoutingDataSource{
	private static final Logger logger = LoggerFactory.getLogger(DynamicRoutingDataSource.class);
	@Override
	protected Object determineCurrentLookupKey() {
		String dataSource= DynamicDataSourceContextHolder.getDataSource();
		logger.info("current data source is [{}]", dataSource);
		return dataSource;
	}

}
