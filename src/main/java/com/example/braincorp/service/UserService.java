package com.example.braincorp.service;

import java.util.List;

import com.example.braincorp.error.EntityNotFoundException;
import com.example.braincorp.model.User;

public interface UserService {
    User getUserById(long uid) throws EntityNotFoundException;
    List<User> getAllUsers();
    List<User> getUsersByQuery(String uid, String name, String gid, String comment, String home, String shell);
}