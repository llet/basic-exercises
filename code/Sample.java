package com.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Sample {
	// 设置APPID/AK/SK
	String[] headers = {"Content-Type", "application/json"};
	public static final String URL = "http://vop.baidu.com/server_api";
	public static final String local = "http://localhost";
	
	public static final String APP_ID = "11664912";
	public static final String API_KEY = "LXcmGzgbQGS5XYoHNTxRveRL";
	public static final String SECRET_KEY = "upBiY3Nquuwq0EOV94tGbPy9NG1VWPmp";
	public static void main(String[] args) throws JsonProcessingException{
		System.out.println("---------------------------");
		HashMap<Object,Object> map = new HashMap<>();
		map.put("format", "pcm");
		map.put("rate", 16000);
		map.put("dev_pid", 1536);
		map.put("channel", 1);
		map.put("token", "24.cba6353fdee3f06d3f88d8539d9ca767.2592000.1536664386.282335-11664912");
		map.put("cuid", "baidu_workshop");
		map.put("len", 4096);
		map.put("speech", getBase64());
		ObjectMapper mapper = new ObjectMapper(); //转换器
        String json=mapper.writeValueAsString(map); //将对象转换成json  
        System.out.println(json);
        
        
		HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate template = new RestTemplate();
		HttpEntity<String> httpEntity = new HttpEntity<String>(json,requestHeaders);
		try {
			ResponseEntity<String> entity = template.postForEntity(URL, httpEntity, String.class);
			System.out.println("entity:"+entity);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
	}
	
	public static String getBase64(){
		String filePath = "C:\\Users\\Administrator\\Desktop\\下载\\16k.pcm";
		byte[] b = null;
		try {
			b = Files.readAllBytes(Paths.get(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Base64.getEncoder().encodeToString(b);
	}
}