package com.project.logging.dto;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
@RequiredArgsConstructor
public class CustomException {

    private HttpStatus status;
    private String code;
    private String errorName;
    private String traceId;
    private String message;

    public CustomException(HttpStatus status, String code, String errorName, String traceId, String message) {
        this.status = status;
        this.code = code;
        this.errorName = errorName;
        this.traceId = traceId;
        this.message = message;
    }


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

    public String getErrorName() {
        return errorName;
    }

    public void setErrorName(String errorName) {
        this.errorName = errorName;
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
        return "{" +
                "status=" + status +
                ", code='" + code + '\'' +
                ", errorName='" + errorName + '\'' +
                ", traceId='" + traceId + '\'' +
                ", message='" + message + '\'' +
                '}';

    }
}
