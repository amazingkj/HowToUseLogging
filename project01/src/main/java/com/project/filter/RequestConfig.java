//package com.project.filter;
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.filter.AbstractRequestLoggingFilter;
//import org.springframework.web.filter.CommonsRequestLoggingFilter;
//
//import lombok.extern.slf4j.Slf4j;
//@Slf4j
//@Configuration
//public class RequestConfig extends AbstractRequestLoggingFilter{
//
//
//  @Bean
//  public CommonsRequestLoggingFilter commonsRequestLoggingFilter() {
//      CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
//      filter.setIncludeClientInfo(true); //클라이언트 주소와 세션 ID를 로그에 출력
//      filter.setIncludeHeaders(true); //헤더정보를 로그에 출력
//      filter.setIncludeQueryString(true); //queryString을 로그에 출력
//      filter.setIncludePayload(true); //body request 내용을 로그에 출력
//      filter.setMaxPayloadLength(100000);	//로그에 포함할 body request 사이즈 제한
//      filter.setAfterMessagePrefix("REQUEST DATA : ");
//      
//      return filter;
//  }
//  
//      @Override
//      protected boolean shouldLog(HttpServletRequest request) {
//          return log.isDebugEnabled();
//      }
//      /**
//       * Writes a log message before the request is processed.
//       */
//      @Override
//      protected void beforeRequest(HttpServletRequest request, String message) {
//          log.debug(message);
//      }
//      /**
//       * Writes a log message after the request is processed.
//       */
//      @Override
//      protected void afterRequest(HttpServletRequest request, String message) {
//          log.debug(message);
//      }
//  }
//
