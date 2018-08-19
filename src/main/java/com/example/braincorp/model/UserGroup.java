package com.example.braincorp.model;

import java.util.List;

import javax.persistence.*;

@Entity
public class UserGroup {
    @Id
    long gid;

    String name;

    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(
            name="MEMBERS",
            joinColumns=@JoinColumn(name="UserGroupGid")
    )
    List<String> members;

    public UserGroup(){}

    public UserGroup(String name, long gid, List<String> members){
        this.name = name;
        this.gid = gid;
        this.members = members;
    }

    public long getGid(){
        return gid;
    }

    public void setGid(long gid){
        this.gid = gid;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public List<String> getMembers(){
        return members;
    }

    public void setMembers(List<String> members){
        this.members = members;
    }

    @Override
    public boolean equals(Object group){
        UserGroup userGroup = (UserGroup)group;
        return (this.gid == userGroup.getGid()
                && this.name.equals(userGroup.getName())
                && this.members.equals(userGroup.getMembers()));
    }

    @Override
    public String toString(){
        return "{Gid: " + this.gid + " Name: " + this.name + " Members: " + this.members + "}";
    }

}
