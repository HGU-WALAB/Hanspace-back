package com.example.hanspaceback.dto.response;

import com.example.hanspaceback.domain.Department;
import com.example.hanspaceback.domain.Reserve;
import com.example.hanspaceback.domain.Space;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class SpaceWithReservesResponse {
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private Long spaceId;
    private String name;
    private int headCount;
    private String availableStart;
    private String availableEnd;
    private String detail;
    private boolean availability;
    private String image;
    private Department department;

    private List<ReserveResponse> reserves;

    public SpaceWithReservesResponse(Space space, List<ReserveResponse> reserves) {
        this.regDate = space.getRegDate();
        this.modDate = space.getModDate();
        this.spaceId = space.getSpaceId();
        this.name = space.getName();
        this.headCount = space.getHeadCount();
        this.availableStart = space.getAvailableStart();
        this.availableEnd = space.getAvailableEnd();
        this.detail = space.getDetail();
        this.availability = space.isAvailability();
        this.image = space.getImage();
        this.department = space.getDepartment();

        this.reserves = reserves;
    }
}
