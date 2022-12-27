package com.project.logging.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AOPServiceImpl implements AOPService{
    public HashMap<String, Object> index(HashMap<String, Object> param) {
        return param;
    }

}
