package com.example.backend.Service.AvatarImg;

import com.example.backend.API.ResourceNotFoundException;
import com.example.backend.Entity.AvatarImg;
import com.example.backend.Repo.AvatarImgRepo;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class AvatarImgServiceImpl implements AvatarImgService{
    private final AvatarImgRepo avatarImgRepo;

    @Override
    public AvatarImg getAvatarImgById(UUID id) {
        return avatarImgRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("AvatarImg not found with id: " + id));
    }

    @Override
    public AvatarImg saveAvatarImg(MultipartFile file) throws IOException {
        AvatarImg photo = new AvatarImg();
        photo.setImgUrl(file.getBytes());
        return avatarImgRepo.save(photo);
    }

    @SneakyThrows
    @Override
    public AvatarImg editAvatarImg(MultipartFile file, UUID id) {
        AvatarImg photo = new AvatarImg();
        photo.setImgUrl(file.getBytes());
        photo.setId(id);
        return avatarImgRepo.save(photo);
    }
}