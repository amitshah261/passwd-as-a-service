package com.example.braincorp.repository;

import com.example.braincorp.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where " +
             "(:uid = '' or u.uid = :uid) and " +
            "(:name = '' or u.name = :name) and " +
            "(:gid = '' or u.gid = :gid) and " +
            "(:comment = '' or u.comment = :comment) and " +
            "(:home = '' or u.home = :home) and " +
            "(:shell = '' or u.shell = :shell)")
    List<User> advancedSearch(@Param("uid") String uid,
                              @Param("name") String name,
                              @Param("gid") String gid,
                              @Param("comment") String comment,
                              @Param("home") String home,
                              @Param("shell") String shell);

}

