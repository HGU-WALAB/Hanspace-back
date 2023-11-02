package com.example.hanspaceback.jwt;

import com.example.hanspaceback.domain.HanRole;
import lombok.Data;

@Data
public class MemberInfoResponse {
    private String email;
    private String name;
    private HanRole hanRole;

    public MemberInfoResponse(String loginId, String nickname, HanRole hanRole) {
        this.email = loginId;
        this.name = nickname;
        this.hanRole = hanRole;
    }
}
