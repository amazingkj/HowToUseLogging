package com.project.logging.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.logging.dto.ReqResLoggingMsg;
import com.project.logging.exception.CustomException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


@Aspect
@Component
public class AspectELK { //AOP로 Request, Response와 엔드포인트 정보 저장

    private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final Logger log = LoggerFactory.getLogger("dev");
    private ObjectMapper objectMapper = new ObjectMapper();
    private ReqResLoggingMsg msg = new ReqResLoggingMsg();

    private CustomException ce = new CustomException();

    private String host = "";
    private String ip = "";
    private String clientIp = "";
    private String clientUrl = "";
    private String TraceId = "";
    private String timeStamp = "";

    @Pointcut("execution(* com.project.logging.*.* (..))")
    private void ApiRestPointCut(){} //pointcut signature


    @AfterThrowing(value="ApiRestPointCut()", throwing="exception")
    public void afterThrowingTargetMethod(JoinPoint thisJoinPoint, Exception exception) throws Exception {

        ce.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        ce.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        ce.setTraceId(TraceId);
        ce.setMessage("Internal Server Error - aop");

        // ReqResLoggingMsg rr = new ReqResLoggingMsg();
        msg.setResponseBody(objectMapper.writeValueAsString(ce));
  /*{\"name\":\"jiin\",\"age\":\"40\"},*/
        /* 이 부분이 발동 하는지 확인되지 않음 */
        log.info("error in aop 별도 처리 : {}", (objectMapper.writeValueAsString(msg)));
    } //예외처리


    @PostConstruct
    public void init() throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        this.host = addr.getHostName();
        this.ip = addr.getHostAddress();
    }
    @Around("bean(*Controller)")//메소드 수행 전후에 수행됨
    public Object controllerAroundLogging(ProceedingJoinPoint joinPoint) throws Throwable {

        Object result ="";
        //ReqResLoggingMsg msg = new ReqResLoggingMsg();

        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            HttpServletResponse response =
                    ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getResponse();

                        this.clientIp = request.getRemoteAddr();
            this.clientUrl = request.getRequestURL().toString();
            this.TraceId = request.getAttribute("traceId").toString();
            this.timeStamp = new SimpleDateFormat(TIMESTAMP_FORMAT).format(new Timestamp(System.currentTimeMillis()));


            final long start = System.currentTimeMillis();
            String className = joinPoint.getSignature().getDeclaringType().getName();
            String methodName = joinPoint.getSignature().getName();

           // User-Agent: <product> / <product-version> <comment>

            String accept=request.getHeader("Accept");
            String acceptEncoding=request.getHeader("Accept-Encoding");
            String acceptLanguage=request.getHeader("Accept-Language");
            String referer=request.getHeader("Referer");
            String connection=request.getHeader("Connection");

            String contentType=request.getHeader("Content-Type");



            try {

                msg.setTraceId(TraceId);
                msg.setClassName(className);
                msg.setHttpMethod(request.getMethod());
                msg.setUri(request.getRequestURI());
                msg.setMethod(methodName);
                msg.setParams(getParams(request));

                msg.setLogTime(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
                msg.setServerIp(ip);
                msg.setDeviceType(request.getHeader("user-agent"));
                //user-agent
                byte[] body = request.getInputStream().readAllBytes();
                String body2 = new String(body, StandardCharsets.UTF_8);
                String replaceBody = body2.replaceAll("\\n", "");

                msg.setRequestBody(replaceBody);

                msg.setAccept(accept);
                msg.setAcceptEncoding(acceptEncoding);
                msg.setAcceptLanguage(acceptLanguage);
                msg.setReferer(referer);
                msg.setConnection(connection);
                msg.setContentType(contentType);

                //log.info("{}", objectMapper.writeValueAsString(msg));


            } catch (Exception e) {
                log.error("LoggerAspect error {}", e);
            }

            result = joinPoint.proceed();

            String elapsedTime = String.valueOf(System.currentTimeMillis() - start);
            msg.setElapsedTime(elapsedTime + " ms");
            msg.setParams(getParams(request));

//            if(result != null) {
//                msg.setResponseBody(result.toString());
//
//        }

            /* 에러 발생 시에는 log response body 값이 200 0k 0k,~ 로 발생해서 아쉬웠음 */
            /*<200 OK OK,com.project.logging.filter.CachedBodyHttpServletRequest@5fdeac69,[]>*/

            /*object인 result를 controller에 리턴되는 값으로 형변환 */
            if( result instanceof ResponseEntity<?>) {
                msg.setResponseBody((String) ((ResponseEntity<?>) result).getBody());
            }



            //기본값
            log.info("{}", objectMapper.writeValueAsString(msg));

            return result;

        } catch (Exception e) {

            //CustomException ce = new CustomException();

            ce.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            ce.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
            ce.setTraceId(TraceId);
            ce.setMessage("Internal Server Error - aop");

           // ReqResLoggingMsg rr = new ReqResLoggingMsg();
            msg.setResponseBody(objectMapper.writeValueAsString(ce));

            log.info("error in aop : {}", (objectMapper.writeValueAsString(msg)));
            /* aop try 구문의 기본값 로그와 filter 에러 로그가 함께 나오게 할 수는 없을까? */
            throw e;
        }

    }

        private static Map<String,Object> getParams(HttpServletRequest request) throws JSONException {
        Map<String,Object> map = new HashMap<>();
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String param = params.nextElement();
            String replaceParam = param.replaceAll("\\.", "-");
            map.put(replaceParam, request.getParameter(param));
        }

        return map;
    }


}
