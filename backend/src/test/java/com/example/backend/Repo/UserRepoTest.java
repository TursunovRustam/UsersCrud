package com.example.backend.Repo;

import com.example.backend.Entity.Category;
import com.example.backend.Entity.Product;
import com.example.backend.Entity.Role;
import com.example.backend.Entity.User;
import com.example.backend.Projection.UserProjection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepoTest {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    private Role roleCashier;
    private User user;
    private String username = "";
    private String id;

    @BeforeEach
    void generateProduct(){
        roleCashier = roleRepo.save(new Role(
                null,
                "ROLE_CASHIER"
        ));
        user = userRepo.save(new User(
                null,
                "timur",
                "100",
                List.of(roleCashier)
        ));
        username = user.getUsername();
        id = user.getId().toString();
    }

    @Test
    void itShouldFindByUsername() {
        User byUsername = (User)userRepo.findByUsername(username);
        assertEquals(user, byUsername);
    }

    @Test
    void itShouldGetUsers() {
        List<UserProjection> users = userRepo.getUsers();
        Assertions.assertNotEquals(null, users);
    }

}