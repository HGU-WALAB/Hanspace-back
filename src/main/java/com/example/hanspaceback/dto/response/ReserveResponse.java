package com.example.hanspaceback.dto.response;

import lombok.Data;

@Data
public class ReserveResponse {
    private Long reserveId;
    private Long spaceId;
    private Long memberId;
    private Long longReserveId;
    private String[] reserveDate;
    private String startTime;
    private String endTime;
    private int headCount;
    private String groupName;
    private String purpose;
    private String phoneNumber;
    private String approve;
    private String extraInfoAns;
}
