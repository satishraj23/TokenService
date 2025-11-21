package com.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.service.TokenService;

@RestController
public class TokenController {
	
	@Autowired
	TokenService tokenService;
	
	
	@GetMapping("/token")
	public String generateToken(){
		return tokenService.generateToken("satishraj");
	}

}
