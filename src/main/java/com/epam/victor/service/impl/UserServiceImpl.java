package com.epam.victor.service.impl;

import com.epam.victor.model.User;
import com.epam.victor.repository.impl.UserRepository;
import com.epam.victor.service.UserService;
import com.epam.victor.service.exception.IdAlreadyExistException;
import com.epam.victor.service.exception.IdNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new IdNotFoundException("User with id " + id + "not found"));
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new EmailNotFoundException("User " + email + " not found"));
    }

    @Override
    public List<User> getByName(String name, int pageSize, int pageNum) {
        return userRepository.findAllOfPageWithCondition(pageSize, pageNum, u -> u.getName().equals(name));
    }

    @Override
    public User create(User user) {
        Long id = user.getId();
        if (id == null){
            id = userRepository.newId();
        }
        else if (userRepository.existsById(id)){
            throw new IdAlreadyExistException("User with id " + id + " already exist");
        }
        userRepository.create(user);
        return userRepository.findById(id).get();
    }

    @Override
    public User update(User user) {
        userRepository.update(user);
        return userRepository.findById(user.getId()).get();
    }

    @Override
    public boolean delete(long userId) {
        if (!userRepository.existsById(userId)){
            return false;
        }
        userRepository.delete(userId);
        return true;
    }
}
