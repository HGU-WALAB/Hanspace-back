package com.example.hanspaceback.domain;

import com.example.hanspaceback.dto.request.SpaceTimeExtraRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SpaceTimeExtra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spaceWeekTimeId")
    private Long id;
    private String week;
    private String extraUnavailableStart;
    private String extraUnavailableEnd;
    private String extraStartDate;
    private String extraEndDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "spaceId")
    private Space space;

    public void update(SpaceTimeExtraRequest request){
        this.week = request.getWeek();
        this.extraUnavailableStart = request.getExtraUnavailableStart();
        this.extraUnavailableEnd = request.getExtraUnavailableEnd();
        this.extraStartDate = request.getExtraStartDate();
        this.extraEndDate = request.getExtraEndDate();
    }
}
