package com.project.logging.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.logging.dto.ReqResLoggingMsg;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Aspect
@Component
public class AspectELK { //AOP로 Request, Response와 엔드포인트 정보 저장

    private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final Logger log = LoggerFactory.getLogger("dev");
    private ObjectMapper mapper = new ObjectMapper();

    private String host = "";
    private String ip = "";
    private String clientIp = "";
    private String clientUrl = "";


    @Pointcut("execution(* com.project.logging..*(..))")
    private void ApiRestPointCut(){} //pointcut signature

//    @AfterThrowing("bean(*ServiceImpl)", throwing="exception")
//    public void afterThrowingTargetMethod(JoinPoint thisJoinPoint, Exception exception) throws Exception {
//
//    } //예외처리


    @PostConstruct
    public void init() throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        this.host = addr.getHostName();
        this.ip = addr.getHostAddress();
    }

   // @Around("bean(*Controller)")//메소드 수행 전후에 수행됨
    public Object controllerAroundLogging(ProceedingJoinPoint joinPoint) throws Throwable{
        String timeStamp = new SimpleDateFormat(TIMESTAMP_FORMAT).format(new Timestamp(System.currentTimeMillis()));
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        this.clientIp = request.getRemoteAddr();
        this.clientUrl = request.getRequestURL().toString();

            String traceId = request.getAttribute("traceId").toString();

            String className = joinPoint.getSignature().getDeclaringTypeName(); // 1.
            String methodName = joinPoint.getSignature().getName(); // 2.
          //  String params = getParams(request);// 3.

            String deviceType = request.getHeader("x-custom-device-type");
            String serverIp = InetAddress.getLocalHost().getHostAddress();


        ReqResLoggingMsg msg = new ReqResLoggingMsg();

        msg.setTraceId(traceId);
        msg.setClassName(className);
        msg.setHttpMethod(request.getMethod());
        msg.setUri(request.getRequestURI());
        msg.setMethod(methodName);
       // msg.setParams(mapper.readValues(request.getParameterMap()));

        msg.setLogTime(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        msg.setServerIp(serverIp);
        msg.setDeviceType(deviceType);
        msg.setRequestBody(mapper.writeValueAsString(request.getParameterMap()));
      //      msg.setHostname(host);
      //      msg.setHostIp(ip);
       //     msg.setClientIp(clientIp);
       //     msg.setClientUrl(clientUrl);
      //      msg.setCallFunction(callFunction);
       //     msg.setType("CONTROLLER_REQ");
            log.info("{}", mapper.writeValueAsString(msg));
      //  log.info("{}", mapper.writeValueAsString(msg));


        Object result = joinPoint.proceed();

        timeStamp = new SimpleDateFormat(TIMESTAMP_FORMAT).format(new Timestamp(System.currentTimeMillis()));
        msg.setLogTime(timeStamp);
        msg.setDeviceType(request.getHeader("CONTROLLER_RES"));
         //   msg.setTimestamp(timeStamp);
        //    msg.setType("CONTROLLER_RES");
         //   msg.setParameter(mapper.writeValueAsString(result));
        log.info("{}", mapper.writeValueAsString(msg));

        return result;
    }

    private Map<String,String> getParams(HttpServletRequest request){


        return null;
        }



}
