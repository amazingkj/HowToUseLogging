package com.project.logging.api;

import com.project.logging.dto.ReqResLogging;
import com.project.logging.service.AOPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
