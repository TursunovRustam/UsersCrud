package com.example.backend.Controller;

import com.example.backend.Service.RoleService.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    private RoleServiceImpl roleService;

    @GetMapping
    public ResponseEntity<?> getRoles(){
        return ResponseEntity.ok(roleService.getRoles());
    }
}