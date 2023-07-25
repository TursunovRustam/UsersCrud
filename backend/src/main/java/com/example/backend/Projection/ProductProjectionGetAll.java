package com.example.backend.Projection;

import com.example.backend.Entity.AvatarImg;
import com.example.backend.Entity.Category;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

public interface ProductProjectionGetAll {
    @Value("#{target.product_id.toString()}")
    String getId();
    @Value("#{target.name}")
    String getName();
    @Value("#{target.code}")
    String getCode();
    @Value("#{target.price}")
    Integer getPrice();
    @Value("#{target.img_url}")
    String getImgUrlString();

    @SneakyThrows
    default AvatarImg getImgUrl() {
        String imgUrlString = getImgUrlString();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode imgUrlNode = objectMapper.readTree(imgUrlString);
            AvatarImg avatarImg = new AvatarImg();
            avatarImg.setId(UUID.fromString(imgUrlNode.get("id").asText()));
            avatarImg.setImgUrl(imgUrlNode.get("img_url").binaryValue());
            return avatarImg;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Value("#{target.balance}")
    Integer getBalance();
    @Value("#{target.category_id}")
    UUID getCategoryId();
    @Value("#{target.category_name}")
    String getCategoryName();
}