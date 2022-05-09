package com.challenge.crud.services;

import java.util.List;

import com.challenge.crud.models.User;

public interface UserService {
	
    public User create(User user);

    public User update(User user);
    
    public User findById(Long id);
	
	public List<User> findByAttributes(String userEmail, String userName);

    public boolean deleteById(Long id);
	
}
