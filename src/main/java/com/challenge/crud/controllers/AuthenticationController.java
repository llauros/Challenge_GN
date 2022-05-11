package com.challenge.crud.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.crud.parameters.UserAuthParameter;
import com.challenge.crud.presenters.TokenPresenter;
import com.challenge.crud.security.TokenService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping
	public ResponseEntity<TokenPresenter> authenticate(@RequestBody @Valid UserAuthParameter parameter) {

		UsernamePasswordAuthenticationToken loginData = parameter.convert();		
		
		try {
			Authentication authentication = authManager.authenticate(loginData);
			
			String token = tokenService.generateToken(authentication);
			
			System.out.println(token);
			
			return ResponseEntity.ok(new TokenPresenter(token, "Bearer"));
		} catch (AuthenticationException e) {
			
			return ResponseEntity.badRequest().build();
		}
	
	}
}
