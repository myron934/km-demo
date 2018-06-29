package com.netease.km.context;


public class DynamicDataSourceContextHolder {
//	private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceContext.class);
	private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<String>();
	
	public static void setDataSource(String key) {
		CONTEXT_HOLDER.set(key);
	}
	
	public static String getDataSource() {
		return CONTEXT_HOLDER.get();
	}
}
