package com.example.hanspaceback.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
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
    private String purpose;
    private String status;
    private String extraInfoAns;
    private String invitedMemberEmail;
}
