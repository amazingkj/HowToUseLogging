package com.project.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.project.controller.AccessController;
import com.project.exception.ErrorCode;
import com.project.exception.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice(basePackageClasses = AccessController.class)
public class TestControllerAdvisor {

		//	  @ExceptionHandler(value = TestException.class)
		//    public String handleDemoExceptionForGlobal(TestException e) { 
		//	  log.error(e.getMessage());
		//        return "/error/404";
		//   }
	  
//	    @ExceptionHandler(TestException.class)
//	    public String handleTestException(TestException test){
//	        log.error("handleTestException",test);
//	       
//	        return test.getMessage();
//	    }

	    @ExceptionHandler(value=Exception.class)
	    public ResponseEntity<String> handleException(Exception ex){
	    	System.out.println("★★★★★★★★★★★★★★★★★★★★★★★★");
	    	System.out.println(ex.getClass().getName());
	    	System.out.println(ex.getLocalizedMessage());
	        log.error("Error_Exception",ex);
	    	System.out.println("★★★★★★★★★★★★★★★★★★★★★★★★-");
	     
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
	    }
	    

	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<ErrorResponse> handleEmailDuplicateException(Exception ex2){
	        log.error("Exception",ex2);
	        ErrorResponse response = new ErrorResponse(ex2.getErrorCode());
	        return new ResponseEntity<>(response, HttpStatus.valueOf(ex2.getErrorCode().getStatus()));
	    }

	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<ErrorResponse> handleException2(Exception ex3){
	        log.error("handleException",ex3);
	        ErrorResponse response = new ErrorResponse(ErrorCode.INTER_SERVER_ERROR);
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    @ExceptionHandler(MethodArgumentNotValidException.class)
	    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
	        log.error("handleMethodArgumentNotValidException", e);
	        final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }
	    
}
	