package com.project.logging.fillter;

import com.project.logging.api.SampleController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
public class ReqResFilter extends OncePerRequestFilter {

   protected static final Logger log = LoggerFactory.getLogger(SampleController.class);

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
      //  ContentCachingRequestWrapper httpServletRequest = new ContentCachingRequestWrapper((HttpServletRequest) request);
      //  ContentCachingResponseWrapper httpServletResponse = new ContentCachingResponseWrapper((HttpServletResponse) response);

        //in
            String traceId = UUID.randomUUID().toString();
             log.info("요청 {}", traceId);

            request.setAttribute("traceId", traceId);
            filterChain.doFilter(request, response); //이후 아웃

            log.info("응답 {}", traceId);
            //response.contentType = MediaType.APPLICATION_JSON_VALUE;
            //response.status = HttpStatus.INTERNAL_SERVER_ERROR.value();

        }

     /*       try {
                request.setAttribute("traceId", traceId);
                filterChain.doFilter(request, response); //이후 아웃
            }catch (Exception e){

                log.error(e.getMessage());
                //response.contentType = MediaType.APPLICATION_JSON_VALUE;
                //response.status = HttpStatus.INTERNAL_SERVER_ERROR.value();

        }}
        */



}