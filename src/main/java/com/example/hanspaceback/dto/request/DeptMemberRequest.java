package com.example.hanspaceback.dto.request;

import lombok.Data;

@Data
public class DeptMemberRequest {
    private Long deptId;
    private Long memberId;
    private String approve;
    private String permission;
}
