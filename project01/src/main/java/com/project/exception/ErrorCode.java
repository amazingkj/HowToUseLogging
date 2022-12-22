package com.project.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorCode{
	// Common
    INVALID_INPUT_VALUE(400, "COMMON-ERR-400", " Invalid Input Value"),   
    HANDLE_ACCESS_DENIED(403, "COMMON-ERR-403", "Access is Denied"),
	NOT_FOUND(404,"COMMON-ERR-404","PAGE NOT FOUND"),
	METHOD_NOT_ALLOWED(405, "COMMON-ERR-405", " Invalid Input Value"),    
    INTER_SERVER_ERROR(500,"COMMON-ERR-500","INTER SERVER ERROR")
    ;

    private int status;
    private String errorCode;
    private String message;
    

    
    
    
}