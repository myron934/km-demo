package com.netease.km.util;

import java.io.IOException;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.DecodeException;
import feign.codec.Decoder;

public class GsonDecoder implements Decoder {

	private final Gson gson;

	public GsonDecoder() {
		this.gson = new Gson();
	}

	public GsonDecoder(Gson gson) {
		this.gson = gson;
	}

	@Override
	public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
		if (response.status() == 404)
			return Util.emptyValueOf(type);
		if (response.body() == null)
			return null;
		if (byte[].class.equals(type)) {
			return Util.toByteArray(response.body().asInputStream());
		}
		try {
			return gson.fromJson(response.body().asReader(), type);
		} catch (JsonIOException e) {
			if (e.getCause() != null && e.getCause() instanceof IOException) {
				throw IOException.class.cast(e.getCause());
			}
			throw e;
		}
	}

}
