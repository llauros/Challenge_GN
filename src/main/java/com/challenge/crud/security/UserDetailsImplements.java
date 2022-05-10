package com.challenge.crud.security;

import java.util.Collection;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.challenge.crud.models.User;

public class UserDetailsImplements implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private String email;
	private String password;
	
	public UserDetailsImplements(User model) {
		if(model != null) {
			this.email = model.getEmail();
			this.password = model.getPassword();
		}
	}
	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Profile> profiles;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.profiles;
	}
	@Override
	public String getPassword() {
		return this.password;
	}
	@Override
	public String getUsername() {
		return this.email;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
}
