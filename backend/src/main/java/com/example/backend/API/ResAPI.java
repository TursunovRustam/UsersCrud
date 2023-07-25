package com.example.backend.API;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResAPI {
    private String message;
    private Boolean isSuccess;
    private Object data;
}
