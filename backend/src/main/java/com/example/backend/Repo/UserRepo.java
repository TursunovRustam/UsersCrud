package com.example.backend.Repo;

import com.example.backend.Entity.User;
import com.example.backend.Projection.UserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.UUID;

public interface UserRepo extends JpaRepository<User, UUID> {
    UserDetails findByUsername(String username);

    @Query(value = "SELECT u.username AS username,\n" +
            "       r.name     AS role_name\n" +
            "FROM users u\n" +
            "         INNER JOIN users_roles ur ON u.id = ur.user_id\n" +
            "         INNER JOIN role r ON r.id = ur.roles_id\n", nativeQuery = true)
    List<UserProjection> getUsers();
}
