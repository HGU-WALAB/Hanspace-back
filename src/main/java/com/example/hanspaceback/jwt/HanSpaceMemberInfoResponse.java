package com.example.hanspaceback.jwt;

import com.example.hanspaceback.domain.DeptRole;
import com.example.hanspaceback.domain.HanRole;
import com.example.hanspaceback.dto.response.DepartmentResponse;
import com.example.hanspaceback.dto.response.DeptMemberResponse;
import lombok.Data;

import java.util.List;

@Data
public class HanSpaceMemberInfoResponse {
    private String email;
    private String name;
    private HanRole hanRole;
    private List<DepartmentResponse> departmentResponses;
    private List<DeptMemberResponse> deptMemberResponses;

    public HanSpaceMemberInfoResponse(String loginId, String name, HanRole hanRole, List<DepartmentResponse> departmentResponses) {
        this.email = loginId;
        this.name = name;
        this.hanRole = hanRole;
        this.departmentResponses = departmentResponses;
    }
}
