package com.example.braincorp.service;

import java.util.List;

import com.example.braincorp.error.EntityNotFoundException;
import com.example.braincorp.model.UserGroup;

public interface UserGroupService {
    UserGroup getGroupById(long gid) throws EntityNotFoundException;
    List<UserGroup> getAllGroups();
    List<UserGroup> getGroupsByQuery(String gid, String name, List<String> members);
    List<UserGroup> getGroupsByMemberId(List<String> members);
}