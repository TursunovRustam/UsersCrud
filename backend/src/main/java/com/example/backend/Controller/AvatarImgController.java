package com.example.backend.Controller;

import com.example.backend.Service.AvatarImg.AvatarImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/avatarImg")
public class AvatarImgController {
    @Autowired
    private AvatarImgService avatarImgService;

    @GetMapping("{id}")
    public ResponseEntity<?> getAvatarImg(@PathVariable String id){
        return ResponseEntity.ok(avatarImgService.getAvatarImgById(UUID.fromString(id)));
    }

    @PostMapping
    public ResponseEntity<?> saveAvatarImg(@RequestParam MultipartFile file) throws IOException {
        return ResponseEntity.ok(avatarImgService.saveAvatarImg(file).getId());
    }

    @PutMapping("{imgId}")
    public ResponseEntity<?> changeAvatarImg(@RequestParam MultipartFile file, @PathVariable String imgId) {
        return ResponseEntity.ok(avatarImgService.editAvatarImg(file, UUID.fromString(imgId)).getId());
    }
}