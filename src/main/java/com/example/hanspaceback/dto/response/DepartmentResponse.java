package com.example.hanspaceback.dto.response;

import lombok.Data;

@Data
public class DepartmentResponse {
    private Long deptId;
    private String siteName;
    private String deptName;
    private String logo;
    private String color;
    private boolean userAccept;
    private int maxRserveCount;
    private String link;
    private String extraInfo;
    private String siteInfoTitle;
    private String siteInfoDetail;
}
