package com.project.controller;

import org.slf4j.Logger;


import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping("/")
public class StartController {
	
	@GetMapping("/start")
	public String start() {
		return "스프링 부트 시작";
		
	}
	private final Logger logger = LoggerFactory.getLogger(StartController.class);
	@GetMapping("")
	public void log() {
		logger.info("로깅 발생!");
		
	}
	
	
	@RequestMapping("/log")
	@ResponseBody
	public String main(Model model) {
		log.trace("TRACE LEVEL TEST");
		log.debug("DEBUG LEVEL TEST");
		log.info("INFO LEVEL TEST");
		log.warn("WARN LEVEL TEST");
		log.error("ERROR LEVEL TEST");
		
		return "확인!";
		
	}
	/*
	 * MDC : 멀티쓰레드 환경에서 로그를 남길 때
	 * put-clear 가 한 쌍 
	 */
	
	@GetMapping("/mdc")
	public String mdc() {
		log.info("Hello logback");

		   
	   	MDC.put("userid", "user01");
	   	MDC.put("event", "event01");
    	MDC.put("transactionId", "transactionUser");
    	log.info("mdc test");
	    	

    	log.trace("mdc ------ Trace");
		log.debug("mdc ------ DEBUG");
		log.info("mdc ------ INFO");
		log.warn("mdc ------ WARN");
		log.error("mdc ------ ERROR");
		
	 	MDC.clear();
	 	log.info("after mdc.clear");
		return "mdc";
		
	}
	
	

}