package com.project.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.dto.PostRequestDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@JsonPropertyOrder
@Component
public class RequestFilter2 implements Filter {

	@Override
	    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	        //전처리
	    	ContentCachingRequestWrapper httpServletRequest = new ContentCachingRequestWrapper((HttpServletRequest) request);
	        ContentCachingResponseWrapper httpServletResponse = new ContentCachingResponseWrapper((HttpServletResponse) response);

	       
	        chain.doFilter(httpServletRequest, httpServletResponse);
	        
	        String url = httpServletRequest.getRequestURI();
	       
	        
	        //JsonNode
	        ObjectMapper objectMapper = new ObjectMapper();
			JsonNode responseJson = objectMapper.readTree(httpServletResponse.getContentAsByteArray());
			JsonNode requestJson = objectMapper.readTree(httpServletRequest.getContentAsByteArray());
			
			log.info(objectMapper.treeToValue(responseJson, PostRequestDto.class).toString());
			log.info(objectMapper.treeToValue(requestJson, PostRequestDto.class).toString());
			
			 
			System.out.println(requestJson);
			System.out.println(responseJson);
			
			// header >> ***추가하기
			
			
	        //후처리 - reqContent 
	        String reqContent = new String(httpServletRequest.getContentAsByteArray());
	        log.info("request url : {}, requestBody : {}", url, reqContent);
	       
	        // -resContent >> url json 으로 넣어주기 
	        String resContent = new String(httpServletResponse.getContentAsByteArray());
	        int httpStatus = httpServletResponse.getStatus();
	        
	        httpServletResponse.copyBodyToResponse(); //중요 
	        
	        log.info("response url : {}, responseBody : {}", httpStatus, resContent);
	        
	        //objectMapper.configure(SerializationConfig.Feature.INDENT_OUTPUT,true);
	          
	    }

}
