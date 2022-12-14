package com.project;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.dto.PostRequestDto;

@RestController
@RequestMapping("/access")
public class AccessController {
	
		@GetMapping("/hello")
	    public String hello() { //http://localhost:8080/access/post

	        return "Get Hello";
	    }
		
		@GetMapping("/get") //get
		public String get() {
			return "Get";
		}
		
		//http://localhost:8080/access/post
		@PostMapping(path="/post") //post
		public void post(@RequestBody PostRequestDto requestDto) {
				System.out.println(requestDto);
				
		}
		
		@PutMapping(path="/put") //put
		public void put(@RequestBody PostRequestDto requestDto) {
				System.out.println(requestDto);
				
		}
		
		@DeleteMapping("/delete/{userId}")
		public void delete(@PathVariable String userId, @RequestParam String account) {
			System.out.println(userId);
			System.out.println(account);
				//db가 없는 상태라 리소스 삭제 200으로 ok 발생
		}
		

}