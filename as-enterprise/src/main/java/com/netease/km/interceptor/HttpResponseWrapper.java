package com.netease.km.interceptor;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class HttpResponseWrapper extends HttpServletResponseWrapper{
	private PrintWriter cachedWriter;
    private CharArrayWriter bufferedWriter;

    public HttpResponseWrapper(HttpServletResponse response) throws IOException {
        super(response);
        bufferedWriter = new CharArrayWriter();
        cachedWriter = new PrintWriter(bufferedWriter);
    }

    public PrintWriter getWriter() throws IOException {
        return cachedWriter;
    }

    public String getResult() {
        return bufferedWriter.toString();
    }
}
