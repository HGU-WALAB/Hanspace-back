package com.example.hanspaceback.dto.response;

import lombok.Data;

@Data
public class SpaceTimeExtraResponse {
    private Long spaceTimeExtraId;
    private Long spaceId;
    private String week;
    private String extraUnavailableStart;
    private String extraUnavailableEnd;
    private String extraStartDate;
    private String extraEndDate;
}
