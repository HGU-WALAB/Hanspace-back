package com.example.hanspaceback.dto.response;

import lombok.Data;

@Data
public class DeptMemberResponse {
    private Long deptMemberId;
    private Long deptId;
    private Long memberId;
    private String approve;
    private String permission;
}
