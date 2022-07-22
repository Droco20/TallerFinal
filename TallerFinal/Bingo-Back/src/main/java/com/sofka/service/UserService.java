package com.sofka.service;

import com.sofka.controller.UserController;
import com.sofka.dao.UserDao;
import com.sofka.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 establece las operaciones en la base de datos
 */

@Service
public abstract class UserService implements IUserService {

    @Autowired // permite inyectar Usuario Dao
    private UserDao UserDao;
    private UserService userDao;

    @Override
    @Transactional(readOnly = true)
    public List<User> list() {
        List<User> users;
        try {
            users = (List<User>)userDao.findAll();
        } catch (Exception exc) {
            exc.getLocalizedMessage();
            throw exc;
        }
        return users;
    }

    protected abstract Object findAll();

    @Override
    @Transactional
    public User save(UserController user) {
        return userDao.save(user);
    }

    @Override
    @Transactional
    public User update(Long id, User user) {
        user.setId(id);
        return userDao.save(user);
    }

 

    @Transactional
    public void updateEmail(Long id, User user){
        userDao.updateEmail(id, user.getEmail());
    }

    protected abstract void updateEmail(Long id, String email);

    @Override
    @Transactional
    public void delete(UserController user) {
        userDao.delete(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUser(User user) {
        return userDao.findById(user.getId());
    }

    protected abstract Optional<User> findById(Long id);

}
