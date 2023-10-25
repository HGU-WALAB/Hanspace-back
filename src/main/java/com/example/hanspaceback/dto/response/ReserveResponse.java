package com.example.hanspaceback.dto.response;

import lombok.Data;

@Data
public class ReserveResponse {
    // reserveId, spaceName, reserveDate, regDate, startTime, endTime, MemberName, purpose, detail, approve(status),
    // extraInfo, extraInfoAns, headCount,
    private Long reserveId;
    private Long spaceId;
    private Long memberId;
    private Long longReserveId;
    private String reserveDate;
    private String startTime;
    private String endTime;
    private int headCount;
//    private String groupName;
    private String purpose;
    private String detail;
//    private String phoneNumber;
    private String status;
    private String extraInfoAns;
}
