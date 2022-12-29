package com.project.logging.api;

import com.project.logging.dto.ReqResLogging;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
        map.put("status", "ok");
        map.put("date", "2022-12-28");
        return map;
    }

    @RequestMapping(value = "/*")
    ResponseEntity<?> responseEntity(HttpServletRequest httpServletRequest){

        //send 에 대한 Response
        //검색어 thread id 변경
        return ResponseEntity.ok(httpServletRequest);
    }


    @GetMapping(value = "/exception")
    ResponseEntity<?> exception() {
        throw new RuntimeException("런타임 에러");
    }


}
