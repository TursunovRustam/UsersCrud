package com.example.backend.Service.AvatarImg;

import com.example.backend.Entity.AvatarImg;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface AvatarImgService {
    AvatarImg getAvatarImgById(UUID id);
    AvatarImg saveAvatarImg(MultipartFile file) throws IOException;
    AvatarImg editAvatarImg(MultipartFile file, UUID id);
}
