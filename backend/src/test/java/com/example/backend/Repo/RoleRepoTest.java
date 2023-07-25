package com.example.backend.Repo;

import com.example.backend.Entity.Category;
import com.example.backend.Entity.Product;
import com.example.backend.Entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoleRepoTest {
    @Autowired
    private RoleRepo roleRepo;
    private Role roleAdmin;
    private String roleName;

    @BeforeEach
    void generateProduct(){
        roleAdmin = roleRepo.save(new Role(
                null,
                "ROLE_STUPID"
        ));
        roleName = roleAdmin.getName();
    }

    @Test
    void itShouldFindByName() {
        Role byName = roleRepo.findByName(roleName);
        assertEquals(roleAdmin, byName);
    }
}