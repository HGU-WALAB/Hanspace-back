package com.example.hanspaceback.dto.request;

import lombok.Data;

@Data
public class UnavailableReserveRequest {
    private Long spaceId;
    private String week;
    private String unavailableDate;
    private String unavailableStart;
    private String unavailableEnd;
}
