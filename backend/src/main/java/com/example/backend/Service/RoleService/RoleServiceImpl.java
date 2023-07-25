package com.example.backend.Service.RoleService;

import com.example.backend.Entity.Role;
import com.example.backend.Repo.RoleRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleRepo roleRepo;

    @Override
    public List<Role> getRoles() {
        return roleRepo.findAll();
    }
}
