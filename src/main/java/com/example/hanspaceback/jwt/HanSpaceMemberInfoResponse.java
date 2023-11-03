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
    public HanSpaceMemberInfoResponse(String loginId, String nickname, HanRole hanRole, List<DeptMemberResponse> deptMemberResponses) {
        this.email = loginId;
        this.name = nickname;
        this.hanRole = hanRole;
        this.deptMemberResponses = deptMemberResponses;
    }
//    private List<DeptRole> deptRoles;
//    public HanSpaceMemberInfoResponse(String loginId, String nickname, HanRole hanRole, List<DepartmentResponse> departmentResponses) {
//        this.email = loginId;
//        this.name = nickname;
//        this.hanRole = hanRole;
//        this.departmentResponses = departmentResponses;
//    }
//
//    public HanSpaceMemberInfoResponse(String loginId, String nickname, HanRole hanRole, List<DepartmentResponse> departmentResponses, List<DeptRole> deptRoles) {
//        this.email = loginId;
//        this.name = nickname;
//        this.hanRole = hanRole;
//        this.departmentResponses = departmentResponses;
//        this.deptRoles = deptRoles;
//    }

}
