package com.example.braincorp.repository;

import com.example.braincorp.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindById() {
        User aUser = new User("root", 2, 0, "root", "/root", "/bin/bash");
        entityManager.persist(aUser);
        entityManager.flush();

        Optional<User> found = userRepository.findById(aUser.getUid());

        assertThat(found.get().getUid())
                .isEqualTo(aUser.getUid());
    }

}
