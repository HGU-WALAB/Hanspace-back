package com.example.hanspaceback.dto.request;

import lombok.Data;

@Data
public class DepartmentRequest {
    private String siteName;
    private String deptName;
//    private String logo;
    private boolean userAccept;
    private int maxRserveCount;
    private String link;
    private String extraInfo;
//    private String deptImage;
}
