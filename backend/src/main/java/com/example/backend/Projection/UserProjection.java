package com.example.backend.Projection;

import jakarta.persistence.Lob;
import org.springframework.beans.factory.annotation.Value;

public interface UserProjection {
    @Value("#{target.username}")
    String getUsername();
    @Value("#{target.role_name}")
    String getRole_Name();
}
