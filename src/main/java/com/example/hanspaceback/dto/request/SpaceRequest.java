package com.example.hanspaceback.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class SpaceRequest {
    private Long deptId;
    private String name;
    private int headCount;
    private String availableStart;
    private String availableEnd;
    private String detail;
    private boolean availability;
    private String image;
}
