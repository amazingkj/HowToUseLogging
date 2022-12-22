package com.project.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private int status;
    private String message;
    private String code;
    
	public ErrorResponse(int status, String message, String code) {
		super();
		this.status = status;
		this.message = message;
		this.code = code;
	}

//    public ErrorResponse(ErrorCode errorCode){
//        this.status = errorCode.getStatus();
//        this.message = errorCode.getMessage();
//        this.code = errorCode.getErrorCode();
//    }
//    
    
}