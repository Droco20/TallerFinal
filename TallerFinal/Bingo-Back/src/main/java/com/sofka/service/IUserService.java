package com.sofka.service;

import com.sofka.controller.UserController;
import com.sofka.domain.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    public List<User> list();

    public User save(User user);


    @Transactional
    User save(UserController user);

    @Transactional
    User update(Long id, User user);

    @Transactional
    void delete(UserController user);

    @Transactional(readOnly = true)
    Optional<User> findUser(User user);
}
