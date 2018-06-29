package com.netease.km.context;

import java.util.UUID;

public class RequestContext {
	private String flowID; //一个uuid唯一标识
	private long beginTime; //请求的起始时间
	
	public RequestContext() {
		flowID=UUID.randomUUID().toString();
		beginTime=System.currentTimeMillis();
	}
	public RequestContext(String flowID) {
		this.flowID=flowID;
		beginTime=System.currentTimeMillis();
	}

	public String getFlowID() {
		return flowID;
	}

	public void setFlowID(String flowID) {
		this.flowID = flowID;
	}
	public long getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(long beginTime) {
		this.beginTime = beginTime;
	}
	
	
}
