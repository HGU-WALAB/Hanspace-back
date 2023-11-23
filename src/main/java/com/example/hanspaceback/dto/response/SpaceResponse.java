package com.example.hanspaceback.dto.response;

import com.example.hanspaceback.domain.Department;
import com.example.hanspaceback.domain.SpaceTimeExtra;
import lombok.Data;

@Data
public class SpaceResponse {
    private Long spaceId;
    private String name;
    private int headCount;
    private String availableStart;
    private String availableEnd;
    private String detail;
    private boolean availability;
    private String image;
}
