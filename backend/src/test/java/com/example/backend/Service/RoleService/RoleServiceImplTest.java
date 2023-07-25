package com.example.backend.Service.RoleService;

import com.example.backend.Entity.Role;
import com.example.backend.Projection.UserProjection;
import com.example.backend.Repo.RoleRepo;
import com.example.backend.Service.UserService.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RoleServiceImplTest {
    @Mock
    RoleRepo roleRepo;
    RoleServiceImpl underTest;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new RoleServiceImpl(roleRepo);
    }

    @Test
    void itShouldGetRoles() {
        List<Role> roles = List.of(new Role(), new Role());
        when(roleRepo.findAll()).thenReturn(roles);
        List<Role> resRoles = underTest.getRoles();
        Assertions.assertEquals(resRoles, roles);
    }
}