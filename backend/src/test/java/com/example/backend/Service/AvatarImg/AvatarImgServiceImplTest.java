package com.example.backend.Service.AvatarImg;

import com.example.backend.Entity.AvatarImg;
import com.example.backend.Repo.AvatarImgRepo;
import com.example.backend.Service.AvatarImg.AvatarImgServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

class AvatarImgServiceImplTest {
    @Mock
    private AvatarImgRepo avatarImgRepo;
    private AvatarImgServiceImpl underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new AvatarImgServiceImpl(avatarImgRepo);
    }

    @Test
    void itShouldGetAvatarImgById() {
        // Mocked input data
        UUID id = UUID.randomUUID();
        AvatarImg mockedAvatarImg = new AvatarImg();
        Mockito.when(avatarImgRepo.findById(id)).thenReturn(Optional.of(mockedAvatarImg));

        // Calling the method under test
        AvatarImg result = underTest.getAvatarImgById(id);

        // Assertions
        Assertions.assertEquals(mockedAvatarImg, result);
    }

    @Test
    void itShouldSaveImg() throws IOException {
        // Mocked input data
        MultipartFile file = new MockMultipartFile("file", new byte[]{});
        AvatarImg mockedAvatarImg = new AvatarImg();
        Mockito.when(avatarImgRepo.save(Mockito.any(AvatarImg.class))).thenReturn(mockedAvatarImg);

        // Calling the method under test
        AvatarImg result = underTest.saveAvatarImg(file);

        // Assertions
        Assertions.assertEquals(mockedAvatarImg, result);
    }

    @Test
    void itShouldEditImg() {
        // Mocked input data
        UUID id = UUID.randomUUID();
        MultipartFile file = new MockMultipartFile("file", new byte[]{});
        AvatarImg mockedAvatarImg = new AvatarImg();
        Mockito.when(avatarImgRepo.save(Mockito.any(AvatarImg.class))).thenReturn(mockedAvatarImg);

        // Calling the method under test
        AvatarImg result = underTest.editAvatarImg(file, id);

        // Assertions
        Assertions.assertEquals(mockedAvatarImg, result);
//        Assertions.assertEquals(id, mockedAvatarImg.getId());
    }
}
