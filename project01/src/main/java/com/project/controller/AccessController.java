package com.project.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.dto.PostRequestDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/access")
public class AccessController {
		
	
		@GetMapping("/hello")
	    public String hello() { 
			throw new RuntimeException();
	        
	    }
		

	    @GetMapping(value = "/test")
	    public String test(@RequestParam(required = false) String param) throws Exception {

	        if(param.equals("1")) {
	            throw new RuntimeException("테스트 예외 입니다.");
	        } else if(param.equals("2")) {
	            //ArithmeticException 발생
	            int div = 1/0;
	        }
	        return "success";
	    }
		
		
//		@ExceptionHandler(value=RuntimeException.class)
//		public String handleDemoException(RuntimeException e){
//			log.error(e.getMessage());
//			return "404";
//		}
//		
//		
		
		@GetMapping("/get") //get
		public String get() {
			return "Get";
		}
		
		//http://localhost:8080/access/post
		@PostMapping(path="/post") //post
		public PostRequestDto post(@RequestBody PostRequestDto requestDto) {
				log.info("requestDto : {}",requestDto);
				
				throw new RuntimeException();
				//return requestDto;
				
		}
		
		//http://localhost:8080/access/post
		@PostMapping(path="/post2") //post
		public PostRequestDto post2(@RequestBody PostRequestDto requestDto) {
				log.info("requestDto : {},{} ",requestDto,requestDto);
				return requestDto;		
		}
		
		@PutMapping(path="/put") //put
		public void put(@RequestBody PostRequestDto requestDto) {
			log.info("requestDto : {}",requestDto);
				
		}
		
		@DeleteMapping("/delete/{userId}")
		public void delete(@PathVariable String userId, @RequestParam String account) {
			System.out.println(userId);
			System.out.println(account);
			log.info("delete acount : {},{} ",userId, account);
				//db가 없는 상태라 리소스 삭제 200으로 ok 발생
		}
		
		
		@RequestMapping("/logtest")
		public String logTest() {
			String name = "Spring";
			System.out.println("name = " + name);
			log.info(" info log={}", name);
			
			
			return "ok";
			
		}
		

}