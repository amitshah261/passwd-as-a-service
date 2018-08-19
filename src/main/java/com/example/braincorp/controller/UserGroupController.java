package com.example.braincorp.controller;

import com.example.braincorp.error.EntityNotFoundException;
//import com.example.braincorp.model.User;
import com.example.braincorp.model.User;
import com.example.braincorp.model.UserGroup;
import com.example.braincorp.service.UserGroupService;
import com.example.braincorp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RestController
public class UserGroupController {

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public List<UserGroup> getGroups() {
        return userGroupService.getAllGroups();
    }

    @RequestMapping(value = "/groups/{gid}", method = RequestMethod.GET)
    public UserGroup getGroup(@PathVariable("gid") long gid) throws EntityNotFoundException {
        return userGroupService.getGroupById(gid);
    }

    @RequestMapping (value = "/groups/query", method = RequestMethod.GET)
    public List<UserGroup> getGroupsByQuery(@RequestParam Optional<Long> gid,
                                      @RequestParam Optional<String> name,
                                      @RequestParam (value = "member") Optional<String[]> members) {
        String gidValue = (gid.isPresent())? String.valueOf(gid.get()) : "";
        String nameValue = (name.isPresent())? name.get() : "";
        List<String> membersList = (members.isPresent())? Arrays.asList(members.get()) : null;
        return userGroupService.getGroupsByQuery(gidValue, nameValue, membersList);
    }

    @RequestMapping (value = "/users/{uid}/groups", method = RequestMethod.GET)
    public List<UserGroup> getGroupsByQuery(@PathVariable("uid") long uid) throws EntityNotFoundException{
        User user = userService.getUserById(uid);
        String name = user.getName();
        System.out.println(name);
        return userGroupService.getGroupsByMemberId(Arrays.asList(name));
    }

}
