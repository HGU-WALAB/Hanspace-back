package com.example.hanspaceback.dto.request;

import com.example.hanspaceback.domain.HanRole;
import lombok.Data;

import java.util.Date;

@Data
public class MemberRequest {
    private String name;
    private String email;

    private Long deptId;
    private HanRole hanRole;
}
