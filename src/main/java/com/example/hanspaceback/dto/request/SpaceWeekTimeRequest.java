package com.example.hanspaceback.dto.request;

import lombok.Data;

@Data
public class SpaceWeekTimeRequest {
    private Long spaceId;
    private String monStartTime;
    private String monEndTime;
    private String tueStartTime;
    private String tueEndTime;
    private String wedStartTime;
    private String wedEndTime;
    private String thuStartTime;
    private String thuEndTime;
    private String friStartTime;
    private String friEndTime;
    private String satStartTime;
    private String satEndTime;
    private String sunStartTime;
    private String sunEndTime;
}
