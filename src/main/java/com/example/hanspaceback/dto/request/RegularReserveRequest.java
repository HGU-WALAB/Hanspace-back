package com.example.hanspaceback.dto.request;

import lombok.Data;

import java.util.Date;

@Data
public class LongReserveRequest {
    private String week;
    private String startDate;
    private String endDate;
}
