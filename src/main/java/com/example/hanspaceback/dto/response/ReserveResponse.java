package com.example.hanspaceback.dto.response;

import com.example.hanspaceback.domain.Reserve;
import lombok.Data;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReserveResponse {
    // reserveId, spaceName, reserveDate, regDate, startTime, endTime, MemberName, purpose, detail, approve(status),
    // extraInfo, extraInfoAns, headCount,
    private Long reserveId;
    private Long spaceId;
    private String spaceName;
    private Long memberId;
    private Long regularReserveId;
    private String reserveDate;
    private String startTime;
    private String endTime;
    private int headCount;
    private String purpose;
    private String status;
    private String extraInfoAns;
    private String invitedMemberEmail;
}
