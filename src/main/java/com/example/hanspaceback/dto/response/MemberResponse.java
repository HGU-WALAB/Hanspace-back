package com.example.hanspaceback.dto.response;

import lombok.Data;

@Data
public class MemberResponse {
    private Long memberId;
    private String name;
    private String email;

    private Long deptId;
}
