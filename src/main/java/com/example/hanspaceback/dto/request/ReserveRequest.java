package com.example.hanspaceback.dto.request;

import jakarta.annotation.Nullable;
import lombok.Data;

import java.util.Date;

@Data
public class ReserveRequest {
    private Long spaceId;
    private Long memberId;
    private Long regularReserveId;
    private String reserveDate;
    private String startTime;
    private String endTime;
    private int headCount;
    private String purpose;
    private String detail;
//    private String phoneNumber;
    private String status;
    private String extraInfoAns;
}
