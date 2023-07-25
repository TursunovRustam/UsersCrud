package com.example.backend.Beans;

import com.example.backend.Entity.Role;
import com.example.backend.Entity.User;
import com.example.backend.Repo.RoleRepo;
import com.example.backend.Repo.UserRepo;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDataLoaderBean implements CommandLineRunner {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    public UserDataLoaderBean(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        String roleAdmin = "ROLE_ADMIN";
        String roleCashier = "ROLE_CASHIER";
        String roleSuperAdmin = "ROLE_SUPER_ADMIN";
        Role newRoleAdmin;
        Role newRoleCashier;
        Role newRoleSuperAdmin;
        if (roleRepo.findByName(roleAdmin) == null && roleRepo.findByName(roleCashier) == null
                && roleRepo.findByName(roleSuperAdmin) == null) {
            newRoleAdmin = roleRepo.save(new Role(null, "ROLE_ADMIN"));
            newRoleCashier = roleRepo.save(new Role(null, "ROLE_CASHIER"));
            newRoleSuperAdmin = roleRepo.save(new Role(null, "ROLE_SUPER_ADMIN"));
            List<Role> rolesForSuperAdmin = new ArrayList<>();
            rolesForSuperAdmin.add(newRoleSuperAdmin);
            String username = "ecommerce-admin";
            String password = "root123";
            userRepo.save(new User(null, username, passwordEncoder.encode(password), rolesForSuperAdmin));
        }
    }
}
