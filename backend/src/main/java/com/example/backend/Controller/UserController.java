package com.example.backend.Controller;

import com.example.backend.Payload.LoginReq;
import com.example.backend.Payload.UserReq;
import com.example.backend.Service.UserService.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserReq userData) {
        // Your registration logic here...
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReq loginData) throws Exception {
        return ResponseEntity.ok(userService.login(loginData));
    }

    @GetMapping("/getRole")
    public ResponseEntity<?> getRole(@RequestHeader("token") String token){
        return ResponseEntity.ok(userService.getUserByUsername(token).getAuthorities());
    }
}