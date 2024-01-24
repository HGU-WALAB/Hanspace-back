package com.example.hanspaceback.dto.response;

import com.example.hanspaceback.domain.HanRole;
import lombok.Data;

@Data
public class MemberResponse {
    private Long memberId;
    private String name;
    private String email;

    private Long deptId;
    private HanRole hanRole;
    private String sId;
    private String password;
}
