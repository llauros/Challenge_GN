package com.challenge.crud.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.challenge.crud.models.User;
import com.challenge.crud.services.UserService;

@Service
public class AuthenticationService implements UserDetailsService {

	@Autowired
	private UserService service;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User result = service.findByEmail(username);
		
		if(result != null) {
			return new UserDetailsImplements(result);
		}
		
		throw new UsernameNotFoundException(username + " não encontrado!");
	}

}
