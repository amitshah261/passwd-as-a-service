package com.example.braincorp.repository;

import com.example.braincorp.model.UserGroup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserGroupRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Test
    public void testFindById() {
        UserGroup aUserGroup = new UserGroup("dockers", 1001, Arrays.asList("abc", "dwoodlins"));
        entityManager.persist(aUserGroup);
        entityManager.flush();

        Optional<UserGroup> found = userGroupRepository.findById(aUserGroup.getGid());

        assertThat(found.get().getGid())
                .isEqualTo(aUserGroup.getGid());
    }

}
