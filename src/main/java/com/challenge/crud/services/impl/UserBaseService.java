package com.challenge.crud.services.impl;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.challenge.crud.entities.UserEntity;
import com.challenge.crud.models.User;
import com.challenge.crud.repositories.UserRepository;
import com.challenge.crud.services.UserService;

@Service
public class UserBaseService implements UserService {

	@Autowired
	private UserRepository repository;

	@Override
	@CacheEvict(value = "userList", allEntries = true)
	public User create(User user) {

		if (user != null) {
			if (validatePassword(user.getPassword())) {
				
				Optional<UserEntity> result = repository.findByEmail(user.getEmail());
				
				if(result.isEmpty()) {
					
					BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
					String encryptedPassword = encoder.encode(user.getPassword());
					user.setPassword(encryptedPassword);
					
					return repository.save(new UserEntity(user)).toModel();
				}			
			}
		}

		return null;
	}

	@Override
	@CacheEvict(value = "userList", allEntries = true)
	public User update(User user) {

		if (user != null) {

			if (!validatePassword(user.getPassword())) {
				return null;
			}

			return repository.findById(user.getId()).map(result -> {

				result.setName(user.getName());
				result.setEmail(user.getEmail());
				
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				String encryptedPassword = encoder.encode(user.getPassword());
				
				result.setPassword(encryptedPassword);

				return repository.save(result).toModel();
			}).orElseGet(() -> {
				return null;
			});
		}

		return null;
	}

	@Override
	public User findById(Long id) {
		Optional<UserEntity> entity = repository.findById(id);

		if (entity.isPresent()) {
			return entity.get().toModel();
		}
		return null;
	}
	
	@Override
	@Cacheable(value = "userList")
	public Page<User> findByAttributes(String userEmail, String userName, Pageable pageable) {

		Page<UserEntity> entity = null;

		if (userName == null && userEmail == null) {
			entity = this.repository.findAll(pageable);
		} else {
			if (userName != null) {
				userName = userName.concat("%");
			}

			entity = this.repository.findUserByEmailAndName(userEmail, userName, pageable);
		}

		if (entity != null && !entity.isEmpty()) {
			return entity.map(item -> item.toModel());
		} else {
			return null;
		}
	}

	@Override
	@CacheEvict(value = "userList", allEntries = true)
	public boolean deleteById(Long id) {

		Optional<UserEntity> result = repository.findById(id);

		if (result.isPresent()) {
			repository.deleteById(id);
			return true;
		}
		return false;
	}

	private boolean validatePassword(String password) {

		if (password != null && password.length() <= 30) {

			// Verificar se ?? isso que o enunciado pede
			for (int i = 1; i < password.length(); i++) {
				if (password.charAt(i) == password.charAt(i - 1)) {
					return false;
				}
			}
			
			String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%??&^(*)_+=!?+-/.<>])(?=\\S+$).{9,20}$";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(password);

			return m.matches();
		}
		return false;
	}

	@Override
	public User findByEmail(String email) {
		Optional<UserEntity> entity = repository.findByEmail(email);

		if (entity.isPresent()) {
			return entity.get().toModel();
		}
		return null;
	}

}
