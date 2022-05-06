package com.challenge.crud.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
	public List<User> findAll() {
		List<UserEntity> entity = this.repository.findAll();

		if (entity != null && !entity.isEmpty()) {
			return entity.stream().map(item -> item.toModel()).collect(Collectors.toList());
		} else {
			return null;
		}
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
	public User create(User user) {

		// return repository.save(new UserEntity(user)).toModel();

		if (user != null) {

			if (validatePassword(user.getPassword())) {

				UserEntity entity = repository.save(new UserEntity(user));
				return entity.toModel();
			}
		}

		return null;
	}

	@Override
	public User update(User user) {

		if (user != null) {

			if ( !validatePassword(user.getPassword()) ) {
				return null;
			}

			return repository.findById(user.getId()).map(result -> {

				result.setName(user.getName());
				result.setEmail(user.getEmail());
				result.setPassword(user.getPassword());

				return repository.save(result).toModel();
			}).orElseGet(() -> {
				return null;
			});

		}

		return null;
	}

	@Override
	public boolean deleteById(Long id) {

		Optional<UserEntity> result = repository.findById(id);

		if (result.isPresent()) {
			repository.deleteById(id);
			return true;
		}
		return false;
	}

	private boolean validatePassword(String password) {

		if (password != null && password.length() <= 20) {

			// Verificar se é isso que o enunciado
			for (int i = 1; i < password.length(); i++) {
				if (password.charAt(i) == password.charAt(i - 1)) {
					return false;
				}
			}

			String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%¨&^(*)_+=!?+-/.<>])(?=\\S+$).{9,20}$";

			Pattern p = Pattern.compile(regex);

			Matcher m = p.matcher(password);

			return m.matches();
		}

		return false;
	}

}
