package com.project.logging.aop;
import com.project.logging.dto.ReqResLoggingMsg;
import com.project.logging.dto.CustomException;

import static net.logstash.logback.argument.StructuredArguments.kv;
import static net.logstash.logback.argument.StructuredArguments.v;

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
    private ReqResLoggingMsg msg = new ReqResLoggingMsg();


    private String host = "";
    private String ip = "";
    private String clientIp = "";
    private String clientUrl = "";
    private String TraceId = "";
    private String timeStamp = "";

    private String ug ="";

    @Pointcut("execution(* com.project.logging.*.* (..))")
    private void ApiRestPointCut(){} //pointcut signature

    @AfterThrowing(value="ApiRestPointCut()", throwing="exception")
    public void afterThrowingTargetMethod(JoinPoint thisJoinPoint, Exception exception) throws Exception {

        System.out.println("===============================");
        System.out.println("AfterThrowing 들렀다");
        System.out.println("===============================");
    } //예외처리

    @PostConstruct
    public void init() throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        this.host = addr.getHostName();
        this.ip = addr.getHostAddress();
    }

    @Around("bean(*Controller)")//메소드 수행 전후에 수행됨
    public Object controllerAroundLogging(ProceedingJoinPoint joinPoint) throws Throwable {

        Object result = "";
        //ReqResLoggingMsg msg = new ReqResLoggingMsg();

        HttpServletResponse response = null;

        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();

            this.clientIp = request.getRemoteAddr();
            this.clientUrl = request.getRequestURL().toString();
            this.TraceId = request.getAttribute("traceId").toString();
            this.timeStamp = new SimpleDateFormat(TIMESTAMP_FORMAT).format(new Timestamp(System.currentTimeMillis()));
            this.ug = request.getHeader("user-agent");

            final long start = System.currentTimeMillis();
            String className = joinPoint.getSignature().getDeclaringType().getName();
            if( "org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController".equals(className) ) {
                return result;
            }
            String methodName = joinPoint.getSignature().getName();

            // User-Agent: <product> / <product-version> <comment>

            String accept = request.getHeader("Accept");
            String acceptEncoding = request.getHeader("Accept-Encoding");
            String acceptLanguage = request.getHeader("Accept-Language");
            String referer = request.getHeader("Referer");
            String connection = request.getHeader("Connection");
            String contentType = request.getHeader("Content-Type");


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
            String replaceBody1 = body2.replaceAll("\\n", "");
            String replaceBody2 = replaceBody1.replaceAll("\\r", "");

            msg.setRequestBody(String.valueOf(v("req", replaceBody2)));



            msg.setAccept(accept);
            msg.setAcceptEncoding(acceptEncoding);
            msg.setAcceptLanguage(acceptLanguage);
            msg.setReferer(referer);
            msg.setConnection(connection);
            msg.setContentType(contentType);
            msg.setContentLengthByte(request.getContentLength());
            //log.info("{}", objectMapper.writeValueAsString(msg));
            /*중요 */

            result = joinPoint.proceed();

            String elapsedTime = String.valueOf(System.currentTimeMillis() - start);
            msg.setElapsedTime(elapsedTime);//ms
            msg.setParams(getParams(request));

            System.out.println("--------------------------------------");
            System.out.println(response.getStatus());
            System.out.println("--------------------------------------");
            msg.setStatusCode(response.getStatus());

            if( response.getStatus() / 100 == 2 || response.getStatus() / 100 == 3 )
            {
                if (result != null) {
                    msg.setResponseBody(result.toString());

                    System.out.println("false");
                }

            } else if(response.getStatus() / 100 == 4){
                CustomException ce = new CustomException();

                ce.setStatus(HttpStatus.NOT_FOUND);
                ce.setCode(String.valueOf(response.getStatus()));
                ce.setTraceId(TraceId);
                ce.setMessage("4XX");
                msg.setResponseBody(String.valueOf(ce));
            }

            System.out.println("===============================");
            System.out.println("정상이거나 ");
            System.out.println("===============================");

            //기본값
            log.info("{},{}", v("commonMessage", msg), v("useragent", ug));

        } catch (Exception e) {

            CustomException ce = new CustomException();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            ce.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            ce.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
            ce.setErrorName(e.getClass().getSimpleName());
            ce.setTraceId(TraceId);
            ce.setMessage("5XX");


            msg.setResponseBody(String.valueOf(kv("ErrorInclude", ce)));
            msg.setStatusCode(response.getStatus());


            System.out.println("===============================");
            System.out.println("에러가 있거나");
            System.out.println("===============================");

            log.info("{},{}", v("commonMessage", msg), v("useragent", ug));

            throw e;

        }finally{


        }
        return result;
    }

    private static Map<String,Object> getParams(HttpServletRequest request) throws JSONException {
        Map<String,Object> map = new HashMap<>();
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String param = params.nextElement();
            String replaceParam = param.replaceAll("\\n", "");
            map.put(replaceParam, request.getParameter(param));
        }

        return map;
    }


}