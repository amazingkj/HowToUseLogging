package com.project.logging.service;

import com.project.logging.dto.ReqResLogging;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestTemplateTestService {

    private ApiService<Response> apiService;

    @Autowired
    public RestTemplateTestService(ApiService<Response> apiService) {
        this.apiService = apiService;
    }


    public Response callPostExternalServer() {
        ReqResLogging rl = new ReqResLogging();
        rl.setName("jake");
        rl.setAge(10);
        String url1 = "vglnk";
        String url2 = "https://postman-echo.com/post";
        String rel = "nofollow https://postman-echo.com/post";

        return null;
        //return apiService.post("<a class="vglnk" href="https://postman-echo.com/post" rel="nofollow"><span>https</span><span>://</span><span>postman</span><span>-</span><span>echo</span><span>.</span><span>com</span><span>/</span><span>post</span></a>", HttpHeaders.EMPTY, person, Response.class).getBody();
    }
}
