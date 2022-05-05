package com.challenge.crud.services.impl;

import java.util.List;
import java.util.Optional;
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
        UserEntity entity = repository.save(new UserEntity(user));

        return entity.toModel();
    }

    @Override
    public User update(User model) {

        return repository.findById(model.getId()).map(result -> {

            result.setName(model.getName());
            result.setEmail(model.getEmail());
            result.setPassword(model.getPassword());

            return repository.save(result).toModel();
        }).orElseGet(() -> {
            return null;
        });

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

}
