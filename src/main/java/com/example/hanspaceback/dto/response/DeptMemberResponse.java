package com.example.hanspaceback.dto.response;

import com.example.hanspaceback.domain.Department;
import com.example.hanspaceback.domain.DeptRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeptMemberResponse {
    private Long deptMemberId;
    private Long deptId;
    private Long memberId;
    private String approve;
    private DeptRole deptRole;
//    private DepartmentResponse departmentResponse;
}
