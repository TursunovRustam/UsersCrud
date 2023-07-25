package com.example.backend.Repo;

import com.example.backend.DTO.AvatarImgDTO;
import com.example.backend.Entity.AvatarImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AvatarImgRepo extends JpaRepository<AvatarImg, UUID> {
}
