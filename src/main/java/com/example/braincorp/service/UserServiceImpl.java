package com.example.braincorp.service;

import java.util.List;
import java.util.Optional;

import com.example.braincorp.error.EntityNotFoundException;
import com.example.braincorp.model.User;
import com.example.braincorp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User getUserById(long uid) throws EntityNotFoundException {
        Optional<User> user = userRepository.findById(uid);

        if(!user.isPresent()){
            throw new EntityNotFoundException(User.class, "uid", String.valueOf(uid));
        }

        return user.get();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getUsersByQuery(String uid, String name, String gid, String comment, String home, String shell){
        return userRepository.advancedSearch(uid, name, gid, comment, home, shell);

    }
}