package com.project;

import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.contrib.json.classic.JsonLayout;

public class CustomLayout extends JsonLayout{

	@Override
	protected void addCustomDataToJsonMap(Map<String, Object> map, ILoggingEvent event) {
		//로그로 출력할 메시지를 최종 출력하기 전에 MAP에 내용 저장 
		long timestampMillis = event.getTimeStamp();
		map.put("timestampSeconds", timestampMillis / 1000);
        map.put("timestampNanos", (timestampMillis % 1000) * 1000000);
        map.put("severity", String.valueOf(event.getLevel()));
        map.put("original_message", event.getMessage());
        map.remove("message");
        
        //파싱을 해서 파싱한 내용을 key-value형태로 저장 
        StringTokenizer st=new StringTokenizer(event.getMessage(),",");
        Map<String,String> json = new TreeMap<String, String>();
        
        while(st.hasMoreTokens()) { 
        	String elmStr = st.nextToken();
        	StringTokenizer elmSt = new StringTokenizer(elmStr,":");
        	String key = elmSt.nextToken();
        	String value = elmSt.nextToken();
        	json.put(key, value);
        	
        } 
        
    
	String msg;    
       try {
        //Map에 저장한 Json을 다시 String으로 변환 	
    	   msg  = new ObjectMapper().writeValueAsString(json);
        		
        	}catch (JsonProcessingException e) {
        		
        		e.printStackTrace();
        		
        	}
        
       //String변환한 json 문자열을 Json 내용을 Json으로 넣는다.
        map.put("jsonpayloadMessege",json);
        
        
	}

	
	
}
