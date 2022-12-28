package com.project.logging.exception;


import org.springframework.http.HttpStatus;

public class CustomException {

    private HttpStatus status;
    private String code;
    private String traceId;
    private String message;


    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "CustomException{" +
                "status:'" + status + '\'' +
                ", code:'" + code + '\'' +
                ", traceId:'" + traceId + '\'' +
                ", message:'" + message + '\'' +
                '}';
    }
}
