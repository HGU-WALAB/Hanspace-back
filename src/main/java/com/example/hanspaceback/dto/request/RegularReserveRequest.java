package com.example.hanspaceback.dto.request;

import lombok.Data;

@Data
public class RegularReserveRequest {
    private Long spaceId;
    private Long memberId;
    private Long reserveReserveId;
    private String[] reserveDate;
    private String startTime;
    private String endTime;
    private int headCount;
    private String groupName;
    private String purpose;
    private String phoneNumber;
    private String approve;
    private String extraInfoAns;

    private String week;
    private String startDate;
    private String endDate;
    private int reserveCount;
//    RegularReserveRequest(){
//        ReserveRequest request = new ReserveRequest();
//        this.spaceId = request.getSpaceId();
//        this.memberId = request.getMemberId();
//        this.reserveReserveId = request.getReserveReserveId();
//        this.reserveDate = request.getReserveDate();
//        this.startTime = request.getStartTime();
//        this.endTime = request.getEndTime();
//        this.headCount = request.getHeadCount();
//        this.groupName = request.getGroupName();
//        this.purpose = request.getPurpose();
//        this.phoneNumber = request.getPhoneNumber();
//        this.approve = request.getApprove();
//        this.extraInfoAns = request.getExtraInfoAns();
//    }
}
