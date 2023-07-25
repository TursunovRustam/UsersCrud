package com.example.backend.Service.UserService;

import com.example.backend.Entity.User;
import com.example.backend.Payload.LoginReq;
import com.example.backend.Payload.UserReq;
import com.example.backend.Projection.UserProjection;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    List<UserProjection> getUsers();
    User register(UserReq userData);
    String login(LoginReq loginData) throws Exception;

    UserDetails getUserByUsername(String token);
}
