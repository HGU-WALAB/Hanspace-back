package com.example.hanspaceback.dto.request;

import jakarta.annotation.Nullable;
import lombok.Data;

import java.util.Date;

@Data
public class ReserveRequest {
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
    private int reserveCount;
    private String week;
    private String startDate;
    private String endDate;
}
