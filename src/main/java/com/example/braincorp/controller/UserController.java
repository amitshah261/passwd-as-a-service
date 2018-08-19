package com.example.braincorp.controller;

import java.util.List;
import java.util.Optional;

import com.example.braincorp.error.EntityNotFoundException;
import com.example.braincorp.model.User;
import com.example.braincorp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @RequestMapping(value = "/users/{uid}", method = RequestMethod.GET)
    public User getUser(@PathVariable("uid") long uid) throws EntityNotFoundException {
        return userService.getUserById(uid);
    }

    @RequestMapping (value = "/users/query", method = RequestMethod.GET)
    public List<User> getUsersByQuery(@RequestParam Optional<Long> uid,
                                  @RequestParam Optional<String> name,
                                  @RequestParam Optional<Long> gid,
                                  @RequestParam Optional<String> comment,
                                  @RequestParam Optional<String> home,
                                  @RequestParam Optional<String> shell) {
        String uidValue = (uid.isPresent())? String.valueOf(uid.get()) : "";
        String nameValue = (name.isPresent())? name.get() : "";
        String gidValue = (gid.isPresent())? String.valueOf(gid.get()) : "";
        String commentValue = (comment.isPresent())? comment.get() : "";
        String homeValue = (home.isPresent())? home.get() : "";
        String shellValue = (shell.isPresent())? shell.get() : "";

        return userService.getUsersByQuery(uidValue, nameValue, gidValue, commentValue, homeValue, shellValue);
    }
}
