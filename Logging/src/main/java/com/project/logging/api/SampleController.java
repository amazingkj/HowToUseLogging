package com.project.logging.api;

import com.project.logging.dto.ReqResLogging;
import com.project.logging.dto.ReqResLoggingMsg;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SampleController {

   // @GetMapping("/sample")
   // pubilc String Sample(@RequestBody ReqResLogging ReqResLogging) {
   // }

    @PostMapping(path="/post") //post
    public ReqResLogging post(@RequestBody @Validated ReqResLogging reqResLogging) {

        return reqResLogging;

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

    @RequestMapping(value = "/*")
    ResponseEntity<?> responseEntity(HttpServletRequest httpServletRequest){

        //send 에 대한 Response
        return ResponseEntity.ok(httpServletRequest);
        /*<200 OK OK,com.project.logging.filter.CachedBodyHttpServletRequest@5fdeac69,[]>*/
    }


    @GetMapping(value = "/exception")
    ResponseEntity<?> exception() {
        throw new RuntimeException("런타임 에러");
    }


}
