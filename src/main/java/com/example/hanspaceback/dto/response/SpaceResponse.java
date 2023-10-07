package com.example.hanspaceback.dto.response;

import com.example.hanspaceback.domain.SpaceTimeExtra;
import lombok.Data;

@Data
public class SpaceResponse {
    private Long spaceId;
    private String name;
    private int headCount;
    private String startTime;
    private String endTime;
    private String detail;
    private boolean availability;
    private String image;
    private String unusableDate;
    private Long departmentId;
//    private Long spaceWeekTimeId;
//    private SpaceTimeExtra spaceTimeExtra;
}
