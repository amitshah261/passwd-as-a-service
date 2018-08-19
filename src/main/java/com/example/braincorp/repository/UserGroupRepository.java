package com.example.braincorp.repository;

import com.example.braincorp.model.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userGroupRepository")
public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
    @Query("select distinct g from UserGroup g left join g.members m where " +
            "(:gid = '' or g.gid = :gid) and " +
            "(:name = '' or g.name = :name) and " +
            "((:members) IS NULL or (m IN :members))" )// or g.gid in (select distinct g.gid from g where g.members intersects :members))
    List<UserGroup> advancedSearch( @Param("gid") String gid,
                                    @Param("name") String name,
                                    @Param("members") List<String> members);
}