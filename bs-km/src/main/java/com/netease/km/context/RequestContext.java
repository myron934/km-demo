package com.netease.km.context;

import java.util.UUID;

import com.google.gson.Gson;

public class RequestContext {
	private String flowID; //一个uuid唯一标识
	
	public RequestContext() {
		flowID=UUID.randomUUID().toString();
	}
	public RequestContext(String flowID) {
		this.flowID=flowID;
	}
	
	public static String decode(RequestContext ctx) {
		return new Gson().toJson(ctx);
	}

	public String getFlowID() {
		return flowID;
	}

	public void setFlowID(String flowID) {
		this.flowID = flowID;
	}
	
}
