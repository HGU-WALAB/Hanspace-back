package com.example.hanspaceback.jwt;

import lombok.Data;

@Data
public class HanSpaceTokenResponse {
    private String token;

    public HanSpaceTokenResponse(String token) {
        this.token = token;
    }
}
