package com.challenge.crud.services;

import org.springframework.data.domain.Page;

import com.challenge.crud.models.User;

public interface UserService {
	
    public User create(User user);

    public User update(User user);
    
    public User findById(Long id);
	
	public Page<User> findByAttributes(String userEmail, String userName, int page, int size);

    public boolean deleteById(Long id);
	
}
