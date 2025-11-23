package com.security.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.security.service.TokenService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "*")
public class TokenController {
	
	@Autowired
	TokenService tokenService;
	
	
	@PostMapping("/login")
	public Map<String, String> login(@RequestBody LoginUser loginUser, HttpServletResponse httpresponse) {
		
		
		if(loginUser.getUsername().equals(loginUser.getPassword())) {
			String token =  generateToken(loginUser.getUsername());
			
			Map<String,String> response = new HashMap<>();
			response.put("token", token);
				          
	        httpresponse.addHeader(
	                "Set-Cookie",
	                "access_token=" + token +
	                "; HttpOnly; Secure; Path=/; Max-Age=3600; SameSite=Strict"
	            );
			
			return response;
		}
		
		return Map.of("error","Invalid Login");
	}
	
	
	private String generateToken(String username){
		return tokenService.generateToken(username);
	}

}
class LoginUser{
	String username;
	String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
