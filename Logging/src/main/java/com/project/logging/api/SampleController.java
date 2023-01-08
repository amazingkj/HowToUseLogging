package com.project.logging.api;

import com.project.logging.dto.nameAgeDTO;
import com.project.logging.service.RestTemplateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SampleController {
    private final RestTemplateService restTemplateService;

    public SampleController(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }

   // @GetMapping("/sample")
   // pubilc String Sample(@RequestBody ReqResLogging ReqResLogging) {
   // }
   @RequestMapping(value = "/*")
   ResponseEntity<?> responseEntity(HttpServletRequest httpServletRequest){

       //send 에 대한 Response
       return ResponseEntity.ok(httpServletRequest);
       /*<200 OK OK,com.project.logging.filter.CachedBodyHttpServletRequest@5fdeac69,[]>*/
   }

    @PostMapping(path="/post") //post
    public nameAgeDTO post(@RequestBody @Validated nameAgeDTO nameAgeDTO) {
        return nameAgeDTO;
    }

    @GetMapping(value = "/test")
    Map<String, String> test(HttpServletRequest httpServletRequest){
        Map<String, String> map = new HashMap();
        DateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date nowDate = new Date();
        String tempDate = sdFormat.format(nowDate);

        map.put("status", "ok");
        map.put("date", tempDate);
        return map;
    }

    @RequestMapping(value = "/exception")
    ResponseEntity<?> exception() {
        throw new RuntimeException("런타임 에러");
    }

    @RequestMapping(value = "/exception1")
    ResponseEntity<?> exception1(HttpServletRequest httpServletRequest){

        //send 에 대한 Response
        return ResponseEntity.internalServerError().body("");
        /*<200 OK OK,com.project.logging.filter.CachedBodyHttpServletRequest@5fdeac69,[]>*/
    }

    @RequestMapping(value = "/exception2")
    ResponseEntity<?> exception2() {
        throw new IllegalArgumentException("IllegalArgumentException");
    }

    // response.sendError는  filter다음에 동작

//    @GetMapping("/error-404")
//    public void error404(HttpServletResponse response) throws IOException {
//        response.sendError(404, "404 오류!");
//    }

    @GetMapping("/error-404")
    public void error404(HttpServletResponse response) throws IOException {
        response.sendError(404, "404 오류!");
    }

    @GetMapping("/error-400")
    public void error400(HttpServletResponse response) throws IOException {
        response.sendError(400, "400 오류!");
    }

    @GetMapping("/error-403")
    public void error403(HttpServletResponse response) throws IOException {
        response.sendError(403, "403 오류!");
    }

    @GetMapping("/resexception")
    public String responseStatus() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"error.bad", new IllegalArgumentException());
    }


/*
    @GetMapping("/req")
    public ResponseEntity<?> getHello(){
        return restTemplateService.genericExchange();
    }
*/

}
