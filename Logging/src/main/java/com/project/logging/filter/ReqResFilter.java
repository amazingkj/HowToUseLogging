package com.project.logging.filter;

import com.project.logging.dto.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import static net.logstash.logback.argument.StructuredArguments.v;

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

        String traceId = UUID.randomUUID().toString().substring(0,8);
        //눈으로 식별이 잘 안되는 관계로 앞부분만 사용

        try {
            CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(request);
            cachedBodyHttpServletRequest.setAttribute("traceId", traceId);
            //IN
//            log.info("요청");
            filterChain.doFilter(cachedBodyHttpServletRequest, response);
            //OUT


        } catch (Exception e) {

            //filter에서 캐치한 에러는 서버 에러기 때문에 항상 500
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()); //서버에러 500으로 상태 갱신

            CustomException ce = new CustomException();
            //System.out.println(HttpStatus);
            ce.setStatus(HttpStatus.valueOf(response.getStatus()));
            //ce.setStatus(HttpStatus.);
            ce.setCode(String.valueOf(HttpStatus.valueOf(response.getStatus())).substring(0,3));
            ce.setErrorName(e.getClass().getSimpleName());
            ce.setTraceId(traceId);
            ce.setMessage(e.getCause().getMessage());

            //v(ce);
            //objectMapper.writeValueAsString(ce);

            //filter에서 받는 로그는 참고용이다//

            log.error("{}", v("filterError",ce));

        }

    }

}