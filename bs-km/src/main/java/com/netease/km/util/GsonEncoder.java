package com.netease.km.util;

import java.lang.reflect.Type;

import com.google.gson.Gson;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;

public class GsonEncoder implements Encoder {
	private final Gson gson;

	public GsonEncoder() {
		this.gson = new Gson();
	}
	public GsonEncoder(Gson gson) {
		this.gson = gson;
	}

	@Override
	public void encode(Object object, Type bodyType, RequestTemplate template) throws EncodeException {
		template.body(gson.toJson(object, bodyType));
	}

}
