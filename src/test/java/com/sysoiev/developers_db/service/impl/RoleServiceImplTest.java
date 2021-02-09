package com.sysoiev.developers_db.service.impl;

import com.sysoiev.developers_db.builder.CommonBuilder;
import com.sysoiev.developers_db.model.Role;
import com.sysoiev.developers_db.repository.RoleRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static com.sysoiev.developers_db.builder.CommonBuilder.list;
import static com.sysoiev.developers_db.builder.RoleBuilder.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    public void getAll() {
        Role userRole = roleDb("USER_ROLE").build();
        Role adminRole = roleDb("USER_ADMIN").id(CommonBuilder.id("2")).build();
        List<Role> roles = list(userRole, adminRole);

        when(roleRepository.findAll()).thenReturn(roles);

        List<Role> actualResult = roleService.list();

        Assert.assertEquals(roles.size(), actualResult.size());

        verify(roleRepository, times(1)).findAll();
    }
}
