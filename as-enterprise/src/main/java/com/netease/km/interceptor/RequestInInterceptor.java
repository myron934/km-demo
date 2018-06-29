package com.netease.km.interceptor;

import java.lang.annotation.Annotation;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.gson.Gson;
import com.netease.km.aspect.ControllerAutoLog;

public class RequestInInterceptor extends HandlerInterceptorAdapter {
	
	private static final ThreadLocal<Long> startTime = new ThreadLocal<Long>(); 
//	private static final ThreadLocal<Integer> isLogged = new ThreadLocal<Integer>(); 
	private static Logger logger = LoggerFactory.getLogger("com.netease.km.controller");
	/**
	 * 执行前调用
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		HandlerMethod h = (HandlerMethod) handler;  
		ControllerAutoLog requestLog = findAnnotation(h, ControllerAutoLog.class);
        //默认自动记录日志, 显式声明了且required=false时 不记录日志
        if(requestLog!=null&&"false".equals(requestLog.required())){
        	return true;
        }
        long beginTime = System.currentTimeMillis();  
        request.setAttribute("beginTime", beginTime);  
        startTime.set(beginTime);
		return true;
	}
	/**
	 * 接口执行成功后调用
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable ModelAndView modelAndView) throws Exception {
		
	}

	/**
	 * 不管成功失败, 执行结束就调用
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable Exception ex) throws Exception {
		doLog(request,response,handler,ex);
		startTime.remove();
          
	}
	
	private <T extends Annotation> T findAnnotation(HandlerMethod handler, Class<T> annotationType) {
        T annotation = handler.getBeanType().getAnnotation(annotationType);
        if (annotation != null) return annotation;
        return handler.getMethodAnnotation(annotationType);
	}
	
	private void doLog(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex)  throws Exception{
		if (!(handler instanceof HandlerMethod))  {
			return;
		}
		HandlerMethod h = (HandlerMethod) handler;  
		ControllerAutoLog requestLog = findAnnotation(h, ControllerAutoLog.class);
        //默认自动记录日志, 显式声明了且required=false时 不记录日志
        if(requestLog!=null&&"false".equals(requestLog.required())){
        	return;
        }
        long beginTime = startTime.get();//得到线程绑定的局部变量（开始时间）
        long endTime = System.currentTimeMillis();  //结束时间  
		Gson gson = new Gson();  
		Map<String,Object> logInfo=new HashMap<String,Object>();
		Object temp=request.getAttribute("flowID");
		String flowID;
		if(null!=temp) {
			flowID=(String)temp;
		} else {
			flowID=UUID.randomUUID().toString();
		}
		logInfo.put("time", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS").format(beginTime));
		logInfo.put("url", request.getRequestURI());
		logInfo.put("cost", ""+(endTime-beginTime)+" ms");
		logInfo.put("flowID", flowID);
		logInfo.put("request", request.getParameterMap());
		if(ex!=null) {
			logInfo.put("error", ex.getMessage());
		}else {
			HttpResponseWrapper responseWrapper=new HttpResponseWrapper(response);
			System.out.println("result="+responseWrapper.getResult());
		}
        logger.info(gson.toJson(logInfo));
	}
       
}
