package com.challenge.crud.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.crud.models.User;
import com.challenge.crud.parameters.UserParameter;
import com.challenge.crud.presenters.UserPresenter;
import com.challenge.crud.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService service;

	@GetMapping
	public ResponseEntity<UserPresenter> findByAttributes(
			@RequestParam(value = "user-email", required = false) String userEmail,
			@RequestParam(value = "user-name", required = false) String userName) {

		List<User> result = this.service.findByAttributes(userEmail, userName);

		if (result != null) {
			return new ResponseEntity(result.stream().map(a -> new UserPresenter(a)).collect(Collectors.toList()),
					HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserPresenter> findById(@PathVariable Long id) {
		User result = this.service.findById(id);

		if (result != null) {
			return new ResponseEntity(new UserPresenter(result), HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@PostMapping
	public ResponseEntity<UserPresenter> create(@RequestBody UserParameter parameter) {

		if (parameter != null) {

			User model = this.service.create(parameter.toModel());

			if (model != null) {
				return new ResponseEntity(new UserPresenter(model), HttpStatus.CREATED);
			} else {
				return new ResponseEntity(HttpStatus.BAD_REQUEST);
			}
		}

		return new ResponseEntity(HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserPresenter> update(@PathVariable Long id, @RequestBody UserParameter parameter) {

		if (parameter != null) {
			User user = parameter.toModel();
			user.setId(id);

			User result = this.service.update(user);

			if (result != null) {
				return new ResponseEntity(new UserPresenter(result), HttpStatus.CREATED);
			} else {
				return new ResponseEntity(HttpStatus.NO_CONTENT);
			}
		}

		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable Long id) {
		boolean result = this.service.deleteById(id);

		if (result) {
			return new ResponseEntity(HttpStatus.OK);
		}

		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

}
