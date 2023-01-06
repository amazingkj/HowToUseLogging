//package com.project.logging.exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.HttpServerErrorException;
//import org.springframework.web.client.UnknownHttpStatusCodeException;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.net.BindException;
//
//@RestControllerAdvice
//public class ErrorAdvice {
//    private CustomException ce = new CustomException();
//    //private HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//    //private String TraceId = request.getAttribute("traceId").toString();
//    /*common error*/
//
//    /*
//    @ExceptionHandler(value = {BindException.class})
//    public ResponseEntity<CustomException> errorHandler(BindException e) {
//        return ResponseEntity.badRequest().body(
//               // new CustomException(e.getAllErrors().get(0).getDefaultMessage(), 400)
//               new CustomException(e.getAllErrors().get(0).getDefaultMessage(), 400)
//        );
//    }
//     */
//    @ExceptionHandler(value = {IllegalArgumentException.class})
//    public ResponseEntity<CustomException> errorHandler(IllegalArgumentException e) {
//        ce.setStatus(HttpStatus.BAD_REQUEST);
//        ce.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
//        ce.setErrorName(e.getClass().getSimpleName());
//        ce.setMessage("BAD_REQUEST");
//        System.out.println("handler BAD_REQUEST");
//        System.out.println(ResponseEntity.badRequest().body(ce));
//        return ResponseEntity.badRequest().body(ce);
//
////        return ResponseEntity.badRequest().body(
////
////                //HttpStatus status, String code, String errorName, String traceId, String message
////                new CustomException(e.getMessage(), 400)
////        );
//    }
//    /*
//        @ExceptionHandler(value = {NullPointerException.class})
//        public ResponseEntity<CustomException> errorHandler(NullPointerException e){
//            return ResponseEntity.badRequest().body(
//                    new CustomException(e.getMessage(), 400)
//            );
//        }
//
//        /*Http error*/
//     /*
//    @ExceptionHandler(value = {HttpClientErrorException.class})
//    public ResponseEntity<CustomException> errorHandler(HttpClientErrorException e){
//
//        ce.setStatus(HttpStatus.NOT_FOUND);
//        ce.setCode(String.valueOf(HttpStatus.NOT_FOUND.value()));
//        ce.setErrorName(e.getClass().getSimpleName());
//        ce.setTraceId(TraceId);
//        ce.setMessage("NOT_FOUND");
//        System.out.println("handler NOT_FOUND");
//        System.out.println(ResponseEntity.badRequest().body(ce));
//
//        return ResponseEntity.badRequest().body(ce);
//    }
//
//
//    /*
//    @ExceptionHandler(value = {HttpClientErrorException.class})
//    public ResponseEntity<CustomException> errorHandler(HttpClientErrorException e){
//        return ResponseEntity.badRequest().body(
//                new CustomException(e.getMessage(), 404)
//        );
//    }
//    @ExceptionHandler(value = {HttpServerErrorException.class})
//    public ResponseEntity<CustomException> errorHandler(HttpServerErrorException e){
//        return ResponseEntity.badRequest().body(
//                new CustomException(e.getMessage(), 500)
//        );
//    }
//    @ExceptionHandler(value = {UnknownHttpStatusCodeException.class})
//    public ResponseEntity<CustomException> errorHandler(UnknownHttpStatusCodeException e){
//        return ResponseEntity.badRequest().body(
//                new CustomException(e.getMessage(), 400)
//        );
//    */
//}
