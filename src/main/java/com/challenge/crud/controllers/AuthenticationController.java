package com.challenge.crud.controllers;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.crud.parameters.UserAuthParameter;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	@PostMapping
	public ResponseEntity<?> authenticate(@RequestBody @Valid UserAuthParameter parameter) {
		
		return null;
	}
}
