package com.example.hanspaceback.jwt;

import lombok.Data;

@Data
public class HanSpaceLoginRequest {
    private String email;
    private String sId;
    private String password;
}
