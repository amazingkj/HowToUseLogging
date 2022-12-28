package com.project.logging.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.logging.dto.ReqResLogging;
import com.project.logging.dto.ReqResLoggingMsg;
import com.project.logging.exception.CustomException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;


@Aspect
@Component
public class AspectELK { //AOP로 Request, Response와 엔드포인트 정보 저장

    private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final Logger log = LoggerFactory.getLogger("dev");
    private ObjectMapper objectMapper = new ObjectMapper();

    private String host = "";
    private String ip =  null;
    private String clientIp =  null;
    private String clientUrl =  null;
    private String TraceId = null;
    private String timeStamp = null;

    @Pointcut("execution(* com.project.logging.*.* (..))")
    private void ApiRestPointCut(){} //pointcut signature


    @AfterThrowing(value="ApiRestPointCut()", throwing="exception")
    public void afterThrowingTargetMethod(JoinPoint thisJoinPoint, Exception exception) throws Exception {

    } //예외처리


    @PostConstruct
    public void init() throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        this.host = addr.getHostName();
        this.ip = addr.getHostAddress();
    }
    @Around("bean(*Controller)")//메소드 수행 전후에 수행됨
    public Object controllerAroundLogging(ProceedingJoinPoint joinPoint) throws Throwable {


        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

            this.clientIp = request.getRemoteAddr();
            this.clientUrl = request.getRequestURL().toString();
            this.TraceId = request.getAttribute("traceId").toString();
            this.timeStamp = new SimpleDateFormat(TIMESTAMP_FORMAT).format(new Timestamp(System.currentTimeMillis()));


            final long start = System.currentTimeMillis();
            String className = joinPoint.getSignature().getDeclaringType().getName();
            String methodName = joinPoint.getSignature().getName();

            ReqResLoggingMsg msg = new ReqResLoggingMsg();

            try {

                msg.setTraceId(TraceId);
                msg.setClassName(className);
                msg.setHttpMethod(request.getMethod());
                msg.setUri(request.getRequestURI());
                msg.setMethod(methodName);
                msg.setParams(getParams(request).toMap());

                //msg.setParams(objectMapper.readValues(request.getParameterMap()));

                msg.setLogTime(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
                msg.setServerIp(ip);
                msg.setDeviceType(request.getHeader("x-custom-device-type"));
                msg.setRequestBody(String.valueOf(objectMapper.readTree(request.getInputStream().readAllBytes())));

                //log.info("{}", objectMapper.writeValueAsString(msg));
                //log.info("{}", mapper.writeValueAsString(msg));


            } catch (Exception e) {
                log.error("LoggerAspect error", e);
            }


            Object result = joinPoint.proceed();

                //ResponseBodyWrapper responseWrapper = new ResponseBodyWrapper((HttpServletResponse) response);
                //String responseMessage = responseWrapper.getDataStreamToString();


            String elapsedTime = String.valueOf(System.currentTimeMillis() - start);
            msg.setElapsedTime(elapsedTime + " ms");
            // msg.setRequestBody(
            msg.setParams(getParams(request).toMap());

            log.info("{}", objectMapper.writeValueAsString(msg));


            return result;

        } catch (Exception e) {

            CustomException ce = new CustomException();

            ce.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            ce.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
            ce.setTraceId(TraceId);
            ce.setMessage("Internal Server Error");

            ReqResLoggingMsg rr = new ReqResLoggingMsg();
            rr.setResponseBody(objectMapper.writeValueAsString(ce));

            log.info("error : {}", (objectMapper.writeValueAsString(rr)));

            throw e;
        }
    }

        private static JSONObject getParams(HttpServletRequest request) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String param = params.nextElement();
            String replaceParam = param.replaceAll("\\.", "-");
            jsonObject.put(replaceParam, request.getParameter(param));
        }
        return jsonObject;
    }


}
