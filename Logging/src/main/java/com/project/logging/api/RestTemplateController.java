package com.project.logging.api;

import com.project.logging.service.RestTemplateTestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class RestTemplateController {

    private final RestTemplateTestService restTemplateTestService;

    public ResponseEntity<Response> restTemplateTest1(){
        return ResponseEntity.ok(restTemplateTestService.callPostExternalServer());
    }
}
