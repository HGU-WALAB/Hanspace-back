package com.example.hanspaceback.jwt;

import lombok.Data;

@Data
public class LoginRequest {
    private String name;
    private String email;
}
