package com.example.hanspaceback.dto.request;

import lombok.Data;

@Data
public class RegularReserveRequest {
    private Long spaceId;
//    private Long memberId;
    private Long reserveReserveId;
    private String[] reserveDate;
    private String startTime;
    private String endTime;
    private int headCount;
    private String purpose;
    private String detail;
    private String status;
    private String extraInfoAns;

    private String week;
    private String startDate;
    private String endDate;
    private int reserveCount;
}
