package ru.itmentor.spring.boot_security.demo.service;

import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User getById(Long id);

    User getByUsername(String username);

    User save(User user);

    void delete(Long id);
}