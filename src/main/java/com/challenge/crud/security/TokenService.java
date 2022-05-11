package com.challenge.crud.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.challenge.crud.models.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	@Value("${challengecrud.jwt.expiration}")
	private String expiration;
	
	@Value("${challengecrud.jwt.secret}")
	private String secret;
	
	public String generateToken(Authentication authentication) {
		UserDetailsImplements loggedUser = (UserDetailsImplements) authentication.getPrincipal();
		
		Date moment = new Date();
		Date expirationDate = new Date(moment.getTime() + Long.parseLong(expiration));
		
		return Jwts.builder()
				.setIssuer("API_do_Desafio_GN")
				.setSubject(loggedUser.getId().toString())
				.setIssuedAt(moment)
				.setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}

	public boolean isTokenValid(String token) {
		
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}

	public Long getUserId(String token) {
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();			
		return Long.parseLong(claims.getSubject());
	}

}
