package com.example.hanspaceback.dto.request;

import com.example.hanspaceback.domain.DeptRole;
import lombok.Data;

@Data
public class DeptMemberRequest {
    private Long deptId;
    private Long memberId;
    private String approve;
    private DeptRole deptRole;
}
