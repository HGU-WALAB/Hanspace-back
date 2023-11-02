package com.example.hanspaceback.jwt;

import com.example.hanspaceback.domain.HanRole;
import lombok.Data;

@Data
public class HanSpaceMemberInfoResponse {
    private String email;
    private String name;
    private HanRole hanRole;

    public HanSpaceMemberInfoResponse(String loginId, String nickname, HanRole hanRole) {
        this.email = loginId;
        this.name = nickname;
        this.hanRole = hanRole;
    }
}
