package com.challenge.crud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.challenge.crud.entities.UserEntity;
import com.challenge.crud.repositories.UserRepository;

@Component
@Configuration
@Profile("dev")
public class TestConfig implements CommandLineRunner  {

	@Autowired
	private UserRepository repository;
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		UserEntity u1 = new UserEntity("admin", "admin@admin.com", "123456");
		
		//repository.save(u1);
	}

}

