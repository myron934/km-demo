package com.netease.km.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebMvc
public class InterceptorConfig implements WebMvcConfigurer{
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
		 //全局
//        registry.addInterceptor(new RequestInInterceptor()).addPathPatterns("/**").excludePathPatterns("/matrix/**");
//        registry.addInterceptor(new SecurityInterceptor()).addPathPatterns("/secure/*");
    }
}
