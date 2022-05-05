package com.challenge.crud.services;

import java.util.List;

import com.challenge.crud.models.User;

public interface UserService {
	
    public List<User> findAll();

    public User findById(Long id);

    public User create(User user);

    public User update(User user);

    public boolean deleteById(Long id);
	
}
