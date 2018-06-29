package com.netease.km.aspect;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.Gson;
import com.netease.km.context.RequestContext;
import com.netease.km.context.RequestContextHolder;

@Component
@Aspect
public class RequestInLogAspect {

	private static Logger logger = LoggerFactory.getLogger("com.netease.km.controller");


	/**
	 * 针对controller上面的自动日志记录切面. 在controller类或者方法上加上 ControllerAutoLog 注解即可
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(com.netease.km.aspect.ControllerAutoLog) || @within(com.netease.km.aspect.ControllerAutoLog)")
	public Object aroundController(ProceedingJoinPoint pjp ) throws Throwable {
		MethodSignature joinPointObject  = (MethodSignature) pjp.getSignature();
		Method method = joinPointObject.getMethod();
		ControllerAutoLog autoLog;
		boolean flag = method.isAnnotationPresent(ControllerAutoLog.class);
		if(flag) {
//			从方法上拿到 ControllerAutoLog 注解
			autoLog=method.getAnnotation(ControllerAutoLog.class); 
		} else {
//			从类上拿 ControllerAutoLog 注解
			autoLog = AnnotationUtils.findAnnotation(pjp.getSignature().getDeclaringType(), ControllerAutoLog.class);
		}
		if(null!=autoLog&&"false".equals(autoLog.required())){
			return pjp.proceed();
		}
		// 方法执行之前
		HttpServletRequest request = ((ServletRequestAttributes) org.springframework.web.context.request.RequestContextHolder
				.getRequestAttributes()).getRequest();
		String ctxStr = request.getParameter("_ctx");
		RequestContext ctx = null;
		if (null != ctxStr) {
			ctx =  new Gson().fromJson(ctxStr, RequestContext.class);
		} else {
			ctx = new RequestContext();
		}
		RequestContextHolder.setContext(ctx);

		String ip = request.getRemoteHost() + ":" + request.getRemotePort();
		long beginTime = ctx.getBeginTime();
		long endTime = System.currentTimeMillis(); // 结束时间
		Gson gson = new Gson();
		Map<String, Object> logInfo = new HashMap<String, Object>();
		logInfo.put("ip", ip);
		logInfo.put("time", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS").format(beginTime));
		logInfo.put("url", request.getRequestURI());
		logInfo.put("cost", "" + (endTime - beginTime) + " ms");
		logInfo.put("context", ctx);
		logInfo.put("request", request.getParameterMap());
		Object rsp;
		try {
			rsp = pjp.proceed();// 执行方法体, 拿到返回值
			logInfo.put("response", rsp);
			return rsp;
		} catch (Throwable e) {
			logInfo.put("error", e.getMessage());
			throw e;
		} finally {
			logger.info(gson.toJson(logInfo));
		}
	}
	
	
	@Around("@annotation(com.netease.km.aspect.FeignClientAutoLog) || @within(com.netease.km.aspect.FeignClientAutoLog)")
	public Object aroundFeignClient(ProceedingJoinPoint pjp ) throws Throwable {
		MethodSignature joinPointObject  = (MethodSignature) pjp.getSignature();
		Method method = joinPointObject.getMethod();
		FeignClientAutoLog autoLog;
		boolean flag = method.isAnnotationPresent(FeignClientAutoLog.class);
		if(flag) {
//			从方法上拿到 ControllerAutoLog 注解
			autoLog=method.getAnnotation(FeignClientAutoLog.class); 
		} else {
//			从类上拿 ControllerAutoLog 注解
			autoLog = AnnotationUtils.findAnnotation(pjp.getSignature().getDeclaringType(), FeignClientAutoLog.class);
		}
		if(null!=autoLog&&"false".equals(autoLog.required())){
			return pjp.proceed();
		}
		Object[] args=pjp.getArgs();
		for(Object arg:args) {
			if(arg instanceof RequestContext) {
				
			}
		}
		// 方法执行之前
		RequestContext ctx = null;
		ctx = new RequestContext();
		RequestContextHolder.setContext(ctx);
		return pjp.proceed();

//		String ip = request.getRemoteHost() + ":" + request.getRemotePort();
//		long beginTime = ctx.getBeginTime();
//		long endTime = System.currentTimeMillis(); // 结束时间
//		Gson gson = new Gson();
//		Map<String, Object> logInfo = new HashMap<String, Object>();
//		logInfo.put("ip", ip);
//		logInfo.put("time", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS").format(beginTime));
//		logInfo.put("url", request.getRequestURI());
//		logInfo.put("cost", "" + (endTime - beginTime) + " ms");
//		logInfo.put("context", ctx);
//		logInfo.put("request", request.getParameterMap());
//		Object rsp;
//		try {
//			rsp = pjp.proceed();// 执行方法体, 拿到返回值
//			logInfo.put("response", rsp);
//			return rsp;
//		} catch (Throwable e) {
//			logInfo.put("error", e.getMessage());
//			throw e;
//		} finally {
//			logger.info(gson.toJson(logInfo));
//		}
	}
}
