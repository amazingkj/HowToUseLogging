package com.project.logging.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.logging.exception.CustomException;
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
    protected static final ObjectMapper objectMapper= new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String traceId = UUID.randomUUID().toString();

        try {
            CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(request);
            cachedBodyHttpServletRequest.setAttribute("traceId", traceId);


            log.info("요청");
            filterChain.doFilter(cachedBodyHttpServletRequest, response); //이후 아웃
            log.info("응답");



        } catch (Exception e) {

            // log.error("그냥 에러 {}",objectMapper.writeValueAsString(e.getMessage()));

            //response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

            CustomException ce = new CustomException();

            ce.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
//            ce.setStatus(HttpStatus.valueOf(response.getStatus()));
//            ce.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
            ce.setCode(String.valueOf(HttpStatus.valueOf(response.getStatus())));
            ce.setErrorName(e.getClass().getSimpleName());
            ce.setTraceId(traceId);
//            ce.setMessage("Internal Server Error");
            ce.setMessage(e.getCause().getMessage());

            try {
            log.error("커스텀 에러 {}", objectMapper.writeValueAsString(ce));

                System.out.println("==========================");
                System.out.println(objectMapper.writeValueAsString(ce));
                System.out.println("==========================");

           } catch (IOException ie) {
            log.warn("IOException Occur");
                throw new RuntimeException();
            }



        }


    }

}