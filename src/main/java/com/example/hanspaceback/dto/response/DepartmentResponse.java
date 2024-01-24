package com.example.hanspaceback.dto.response;

import com.example.hanspaceback.domain.DeptRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepartmentResponse {
    private Long deptId;
    private String siteName;
    private String deptName;
    private String logoImage;
    private boolean userAccept;
    private int maxRserveCount;
    private String link;
    private String extraInfo;
    private String deptImage;
    private int memberCount;
    private int spaceCount;
    private DeptRole deptRole;
    private List<DeptMemberResponse> deptMemberResponse;
}
