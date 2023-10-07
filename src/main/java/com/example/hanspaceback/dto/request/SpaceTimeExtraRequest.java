package com.example.hanspaceback.dto.request;

import lombok.Data;

@Data
public class SpaceTimeExtraRequest {
    private Long spaceId;
    private String week;
    private String extraUnavailableStart;
    private String extraUnavailableEnd;
    private String extraStartDate;
    private String extraEndDate;
}
