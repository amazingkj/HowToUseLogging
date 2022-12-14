package com.project.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class RequestConfig {


  @Bean
  public CommonsRequestLoggingFilter commonsRequestLoggingFilter() {
      CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
      filter.setIncludeClientInfo(true); //클라이언트 주소와 세션 ID를 로그에 출력
      filter.setIncludeHeaders(true); //헤더정보를 로그에 출력
      filter.setIncludeQueryString(true); //queryString을 로그에 출력
      filter.setIncludePayload(true); //body request 내용을 로그에 출력
      filter.setMaxPayloadLength(100000);	//로그에 포함할 body request 사이즈 제한
      filter.setAfterMessagePrefix("REQUEST DATA : ");
      
      return filter;
  }

}