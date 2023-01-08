package com.project.logging.dto;

import java.util.Map;


public class ReqResLoggingMsg {
    private String traceId;
    private String className;
    private String httpMethod;
    private String uri;
    private String method;
    private Map<String,Object> params;
    private String logTime;
    private String serverIp;
    private String deviceType;
    private String requestBody;
    private String responseBody;
    private long elapsedTime;

    //추가한 header 정보

    private String accept;
    private String acceptEncoding;
    private String acceptLanguage;
    private String referer;
    private String connection;

    private String contentType;

    private int StatusCode;

    private int ContentLengthByte;

    public int getContentLengthByte() {
        return ContentLengthByte;
    }

    public void setContentLengthByte(int contentLengthByte) {
        ContentLengthByte = contentLengthByte;
    }

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int statusCode) {
        StatusCode = statusCode;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String,Object> getParams() {
        return params;
    }

    public void setParams(Map<String,Object> params) {
        this.params = params;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public String getAcceptEncoding() {
        return acceptEncoding;
    }

    public void setAcceptEncoding(String acceptEncoding) {
        this.acceptEncoding = acceptEncoding;
    }

    public String getAcceptLanguage() {
        return acceptLanguage;
    }

    public void setAcceptLanguage(String acceptLanguage) {
        this.acceptLanguage = acceptLanguage;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }


    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return "{" +
                "traceId='" + traceId + '\'' +
                ", className='" + className + '\'' +
                ", httpMethod='" + httpMethod + '\'' +
                ", uri='" + uri + '\'' +
                ", method='" + method + '\'' +
                ", params=" + params +
                ", logTime='" + logTime + '\'' +
                ", serverIp='" + serverIp + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", requestBody='" + requestBody + '\'' +
                ", responseBody='" + responseBody + '\'' +
                ", elapsedTime=" + elapsedTime +
                ", accept='" + accept + '\'' +
                ", acceptEncoding='" + acceptEncoding + '\'' +
                ", acceptLanguage='" + acceptLanguage + '\'' +
                ", referer='" + referer + '\'' +
                ", connection='" + connection + '\'' +
                ", contentType='" + contentType + '\'' +
                ", StatusCode=" + StatusCode +
                ", ContentLengthByte=" + ContentLengthByte +
                '}';
    }
}

