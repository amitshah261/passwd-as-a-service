package com.example.braincorp.service;

import com.example.braincorp.error.EntityNotFoundException;
import com.example.braincorp.model.User;
import com.example.braincorp.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    @TestConfiguration
    static class UserServiceImplTestContextConfiguration {

        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
    }

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp() {
        Optional<User> user = Optional.of(new User("root", 0, 0, "root", "/root", "/bin/bash"));
        Long invalidUid = 123L;

        Mockito.when(userRepository.findById(user.get().getUid()))
                .thenReturn(user);
        Mockito.when(userRepository.findById(invalidUid))
                .thenReturn(Optional.empty());

        Mockito.when(userRepository.findAll())
                .thenReturn(Arrays.asList(user.get()));

        Mockito.when(userRepository.advancedSearch("0", "", "", "", "", ""))
                .thenReturn(Arrays.asList(user.get()));
        Mockito.when(userRepository.advancedSearch("", "root", "", "", "", ""))
                .thenReturn(Arrays.asList(user.get()));
        Mockito.when(userRepository.advancedSearch("1001", "root", "", "", "", ""))
                .thenReturn(Arrays.asList(user.get()));

        Mockito.when(userRepository.advancedSearch("1001", "root", "", "root", "/root", "/bin/bash"))
                .thenReturn(Arrays.asList(user.get()));
    }

    @Test
    public void whenValidUid_thenGroupShouldBeFound() throws EntityNotFoundException {
        Long uid = 0L;
        User found = userService.getUserById(uid);

        assertThat(found.getUid())
                .isEqualTo(uid);
    }

    @Test(expected = EntityNotFoundException.class)
    public void whenInvalidUid_thenShouldThrowEntityNotFoundException() throws EntityNotFoundException {
        Long uid = 123L;
        userService.getUserById(uid);
    }

    @Test
    public void getAllUsersTest(){
        List<User> user = Arrays.asList(new User("root", 0, 0, "root", "/root", "/bin/bash"));

        List<User> userList = userService.getAllUsers();
        assertThat(userList)
                .isEqualTo(user);
    }

    @Test
    public void getUsersByQueryOnlyUidTest(){
        List<User> ug =  userService.getUsersByQuery("0", "", "", "", "", "");
        List<User> user = Arrays.asList(new User("root", 0, 0, "root", "/root", "/bin/bash"));
        assertThat(ug.size()).isEqualTo(1);
        assertThat(ug).isEqualTo(user);
    }

    @Test
    public void getUsersByQueryOnlyNameTest() {
        List<User> ug =  userService.getUsersByQuery("", "root", "", "", "", "");
        List<User> user = Arrays.asList(new User("root", 0, 0, "root", "/root", "/bin/bash"));
        assertThat(ug.size()).isEqualTo(1);
        assertThat(ug).isEqualTo(user);
    }

    @Test
    public void getUsersByQueryUidAndNameTest() {
        List<User> ug =  userService.getUsersByQuery("1001", "root", "", "", "", "");
        List<User> user = Arrays.asList(new User("root", 0, 0, "root", "/root", "/bin/bash"));
        assertThat(ug.size()).isEqualTo(1);
        assertThat(ug).isEqualTo(user);
    }

    @Test
    public void getUsersByQueryAllExceptGidTest(){
        List<User> ug =  userService.getUsersByQuery("1001", "root", "", "root", "/root", "/bin/bash");
        List<User> user = Arrays.asList(new User("root", 0, 0, "root", "/root", "/bin/bash"));
        assertThat(ug.size()).isEqualTo(1);
        assertThat(ug).isEqualTo(user);
    }
}
