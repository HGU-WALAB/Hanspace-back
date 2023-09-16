package com.example.hanspaceback.dto.response;

import lombok.Data;

@Data
public class SpaceResponse {
    private Long spaceId;
    private String name;
    private int headCount;
    private String startTime;
    private String endTime;
    private String detail;
    private String lableColor;
    private boolean availability;
    private String image;
    private String unusableDate;
    private Long departmentId;
}
