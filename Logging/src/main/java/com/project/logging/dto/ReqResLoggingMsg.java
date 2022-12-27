package com.project.logging.dto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;


public class ReqResLoggingMsg {
    @JsonProperty(value = "traceId")
    private String traceId;
    @JsonProperty(value = "class")
    private String className;
    @JsonProperty(value = "http_method")
    private String httpMethod;
    @JsonProperty(value = "uri")
    private String uri;
    @JsonProperty(value = "method")
    private String method;
    @JsonProperty(value = "params")
    private Map<String, String> params;
    @JsonProperty(value = "log_time")
    private String logTime;

    @JsonProperty(value = "server_ip")
    private String serverIp;
    @JsonProperty(value = "device_type")
    private String deviceType;
    @JsonProperty(value = "request_body")
    private String requestBody;
    @JsonProperty(value = "response_body")
    private String responseBody;
    @JsonProperty(value = "elapsed_time")
    private String elapsedTime;


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

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
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

    public String getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(String elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
}

