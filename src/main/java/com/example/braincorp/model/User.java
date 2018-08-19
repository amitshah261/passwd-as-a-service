package com.example.braincorp.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    private long uid;

    private long gid;
    private String name;
    private String comment;
    private String home;
    private String shell;

    public User(){}

    public User(String name, long uid, long gid, String comment, String home, String shell){
        this.uid = uid;
        this.gid = gid;
        this.name = name;
        this.comment = comment;
        this.home = home;
        this.shell = shell;
    }

    public long getUid(){
        return uid;
    }

    public void setUid(long uid){
        this.uid = uid;
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

    public String getComment(){
        return comment;
    }

    public void setComment(String comment){
        this.comment = comment;
    }

    public String getHome(){
        return home;
    }
    public void setHome(String home){
        this.home = home;
    }

    public String getShell(){
        return shell;
    }

    public void setShell(String shell){
        this.shell = shell;
    }

    @Override
    public boolean equals(Object anotherUser){
        User user = (User)anotherUser;
        return (this.uid == user.getUid() &&
                this.gid == user.getGid() &&
                this.name.equals(user.getName()) &&
                this.comment.equals(user.getComment()) &&
                this.home.equals(user.getHome()) &&
                this.shell.equals(user.getShell()));
    }

    @Override
    public String toString(){
        return "{Uid: " + this.uid + " Gid: " + this.gid + " Name: " + this.name + " Comment: " + this.comment + " Home: " + this.home + " Shell: " + this.shell + "}";
    }
}
