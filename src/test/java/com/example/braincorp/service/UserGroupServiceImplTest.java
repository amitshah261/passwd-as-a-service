package com.example.braincorp.service;

import com.example.braincorp.error.EntityNotFoundException;
import com.example.braincorp.model.UserGroup;
import com.example.braincorp.repository.UserGroupRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
public class UserGroupServiceImplTest {

    @TestConfiguration
    static class UserGroupServiceImplTestContextConfiguration {

        @Bean
        public UserGroupService userGroupService() {
            return new UserGroupServiceImpl();
        }
    }

    @Autowired
    private UserGroupService userGroupService;

    @MockBean
    private UserGroupRepository userGroupRepository;

    @Before
    public void setUp() {
        Optional<UserGroup> group = Optional.of(new UserGroup("docker", 1001, Arrays.asList("abc", "dwoodlins")));
        Long invalidGid = 0L;

        Mockito.when(userGroupRepository.findById(group.get().getGid()))
                .thenReturn(group);
        Mockito.when(userGroupRepository.findById(invalidGid))
                .thenReturn(Optional.empty());

        Mockito.when(userGroupRepository.findAll())
                .thenReturn(Arrays.asList(group.get()));

        Mockito.when(userGroupRepository.advancedSearch("1001", "", new ArrayList<>()))
                .thenReturn(Arrays.asList(group.get()));
        Mockito.when(userGroupRepository.advancedSearch("", "", Arrays.asList("abc")))
                .thenReturn(Arrays.asList(group.get()));
        Mockito.when(userGroupRepository.advancedSearch("1001", "", Arrays.asList("abc")))
                .thenReturn(Arrays.asList(group.get()));

        Mockito.when(userGroupRepository.advancedSearch("", "", Arrays.asList("abc", "dwoodlins")))
                .thenReturn(Arrays.asList(group.get()));
    }

    @Test
    public void whenValidGid_thenGroupShouldBeFound() throws EntityNotFoundException {
        Long gid = 1001L;
        UserGroup found = userGroupService.getGroupById(gid);

        assertThat(found.getGid())
                .isEqualTo(gid);
    }

    @Test(expected = EntityNotFoundException.class)
    public void whenInvalidGid_thenShouldThrowEntityNotFoundException() throws EntityNotFoundException {
        Long gid = 0L;
        userGroupService.getGroupById(gid);
    }

    @Test
    public void getAllGroupsTest(){
        List<UserGroup> group = Arrays.asList(new UserGroup("docker", 1001, Arrays.asList("abc", "dwoodlins")));

        List<UserGroup> userGroupList = userGroupService.getAllGroups();
        assertThat(userGroupList)
                .isEqualTo(group);
    }

    @Test
    public void getGroupsByQueryOnlyGidTest(){
        List<UserGroup> ug =  userGroupService.getGroupsByQuery("1001", "", new ArrayList<>());
        List<UserGroup> group = Arrays.asList(new UserGroup("docker", 1001, Arrays.asList("abc", "dwoodlins")));
        assertThat(ug.size()).isEqualTo(1);
        assertThat(ug).isEqualTo(group);
    }

    @Test
    public void getGroupsByQueryOnlyMembersTest() {
        List<UserGroup> ug =  userGroupService.getGroupsByQuery("", "", Arrays.asList("abc"));
        List<UserGroup> group = Arrays.asList(new UserGroup("docker", 1001, Arrays.asList("abc", "dwoodlins")));
        assertThat(ug.size()).isEqualTo(1);
        assertThat(ug).isEqualTo(group);
    }

    @Test
    public void getGroupsByQueryGidMembersTest() {
        List<UserGroup> ug =  userGroupService.getGroupsByQuery("1001", "", Arrays.asList("abc"));
        List<UserGroup> group = Arrays.asList(new UserGroup("docker", 1001, Arrays.asList("abc", "dwoodlins")));
        assertThat(ug.size()).isEqualTo(1);
        assertThat(ug).isEqualTo(group);
    }

    @Test
    public void getGroupsByMemberIdTest(){
        List<UserGroup> ug =  userGroupService.getGroupsByMemberId(Arrays.asList("abc", "dwoodlins"));
        List<UserGroup> group = Arrays.asList(new UserGroup("docker", 1001, Arrays.asList("abc", "dwoodlins")));
        assertThat(ug.size()).isEqualTo(1);
        assertThat(ug).isEqualTo(group);
    }
}
