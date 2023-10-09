package com.bankinc.credibanco.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankinc.credibanco.request.LoginRequest;
import com.bankinc.credibanco.request.RegisterRequest;
import com.bankinc.credibanco.response.AuthResponse;
import com.bankinc.credibanco.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	private static final Logger log=LoggerFactory.getLogger(AuthController.class);
	
    private final AuthService authService;
    
    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request)
    { 
    	log.info("controller::: "+ request);
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request)
    {
    	log.info("controller register::: "+ request);
        return ResponseEntity.ok(authService.register(request));
    }
}
