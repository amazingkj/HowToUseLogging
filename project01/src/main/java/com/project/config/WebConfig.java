package com.project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer{
	
	private final Interceptor interceptor;
	

	 @Override
	    public void addInterceptors(InterceptorRegistry registry) {
	        registry.addInterceptor(interceptor)
	                .addPathPatterns("/**");
	                //.excludePathPatterns("/css/**", "/images/**", "/js/**"); //static 폴더 내 정적 리소스 파일 제외
	        		// 정적 리소스 파일을 왜 제외해야하는지? 
	    }

}
