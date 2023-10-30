package com.example.hanspaceback.dto.request;

import lombok.Data;

@Data
public class ReserveMemberRequest {
    private Long reserveId;
    private Long memberId;
}
