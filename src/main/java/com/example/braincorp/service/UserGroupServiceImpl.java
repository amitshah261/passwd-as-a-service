package com.example.braincorp.service;

import com.example.braincorp.error.EntityNotFoundException;
import com.example.braincorp.model.UserGroup;
import com.example.braincorp.repository.UserGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("userGroupService")
public class UserGroupServiceImpl implements UserGroupService{

    @Autowired
    UserGroupRepository userGroupRepository;

    public UserGroup getGroupById(long gid) throws EntityNotFoundException{
        Optional<UserGroup> group = userGroupRepository.findById(gid);

        if(!group.isPresent()){
            throw new EntityNotFoundException(UserGroup.class, "gid", String.valueOf(gid));
        }

        return group.get();
    }

    public List<UserGroup> getAllGroups(){
        return userGroupRepository.findAll();
    }

    public List<UserGroup> getGroupsByQuery(String gid, String name, List<String> members){
        return userGroupRepository.advancedSearch(gid, name, members);
    }

    public List<UserGroup> getGroupsByMemberId(List<String> members){
        return userGroupRepository.advancedSearch("", "", members);
    }
}
