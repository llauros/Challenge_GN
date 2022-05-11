package com.challenge.crud.parameters;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class UserAuthParameter {
	
	private String email;
	private String password;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}   
	public void setPassword(String password) {
		this.password = password;
	}
	
	public UsernamePasswordAuthenticationToken convert() {
		System.out.println(email + " D " + password);
		return new UsernamePasswordAuthenticationToken(email, password);
	}


	
	

}
