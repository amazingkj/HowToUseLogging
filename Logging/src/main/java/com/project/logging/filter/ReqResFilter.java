package com.project.logging.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import static net.logstash.logback.argument.StructuredArguments.kv;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
public class ReqResFilter extends OncePerRequestFilter {
    protected static final Logger log = LoggerFactory.getLogger("dev");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //  ContentCachingRequestWrapper httpServletRequest = new ContentCachingRequestWrapper((HttpServletRequest) request);
        //  ContentCachingResponseWrapper httpServletResponse = new ContentCachingResponseWrapper((HttpServletResponse) response);

       /* //in
            String traceId = UUID.randomUUID().toString();
             log.info("요청 {}", traceId);

            request.setAttribute("traceId", traceId);
            filterChain.doFilter(request, response);
            //이후 아웃

            log.info("응답 {}", traceId);
            //response.contentType = MediaType.APPLICATION_JSON_VALUE;
            //response.status = HttpStatus.INTERNAL_SERVER_ERROR.value();

        }*/
        //in

        String traceId = UUID.randomUUID().toString();


        try {
            CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(request);
            cachedBodyHttpServletRequest.setAttribute("traceId", traceId);

            String url = request.getRequestURI();

            //log.info("getStatus : {}", response.getStatus());
            // log.info("요청 {}", traceId);

            filterChain.doFilter(cachedBodyHttpServletRequest, response); //이후 아웃

            //log.info("응답 {}", traceId);
            // int httpStatus = response.getStatus();
            //String resContent = new String(response.getContentAsByteArray());
            //log.info("resContent : {}", kv("httpStatus", httpStatus));

            // response.copyBodyToResponse();//중요
            // log.info("resContent", kv("httpStatus",httpStatus), kv("resContent",resContent));


        } catch (Exception e) {

            log.error(e.getMessage());

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            CustomException customException = new CustomException(
                     status = HttpStatus.INTERNAL_SERVER_ERROR,
                    code = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                   traceId = traceId,
                   message = "Internal Server Error");

            customException.

//


        }


    }

}