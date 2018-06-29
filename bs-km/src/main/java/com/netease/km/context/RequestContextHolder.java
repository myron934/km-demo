package com.netease.km.context;

public class RequestContextHolder {
	private static final ThreadLocal<RequestContext> CONTEXT_HOLDER = new ThreadLocal<RequestContext>();
	
	public static RequestContext getContext(){
		RequestContext context=CONTEXT_HOLDER.get();
		if(null==context) {
			return new RequestContext();
		}
		return context;
	}
	public static void setContext(RequestContext context){
		CONTEXT_HOLDER.set(context);
	}
	
}
