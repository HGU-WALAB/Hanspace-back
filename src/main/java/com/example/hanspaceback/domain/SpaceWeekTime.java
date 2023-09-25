package com.example.hanspaceback.domain;

import com.example.hanspaceback.dto.request.SpaceWeekTimeRequest;
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
public class SpaceWeekTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spaceWeekTimeId")
    private Long id;
    private String monStartTime;
    private String monEndTime;
    private String tueStartTime;
    private String tueEndTime;
    private String wedStartTime;
    private String wedEndTime;
    private String thuStartTime;
    private String thuEndTime;
    private String friStartTime;
    private String friEndTime;
    private String staStartTime;
    private String staEndTime;
    private String sunStartTime;
    private String sunEndTime;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "spaceId") // spaceId를 외래 키(FK)로 사용
    private Space space; // Space 엔티티와의 일대일 관계

    public void update(SpaceWeekTimeRequest request){
        this.monStartTime = request.getMonStartTime();
        this.monEndTime = request.getMonEndTime();
        this.tueStartTime = request.getTueStartTime();
        this.tueEndTime = request.getThuEndTime();
        this.wedStartTime = request.getWedStartTime();
        this.wedEndTime = request.getWedEndTime();
        this.thuStartTime = request.getThuStartTime();
        this.thuEndTime = request.getThuEndTime();
        this.friStartTime = request.getFriStartTime();
        this.friEndTime = request.getFriEndTime();
        this.staStartTime = request.getSatStartTime();
        this.staEndTime = request.getSatEndTime();
        this.sunStartTime = request.getSunStartTime();
        this.sunEndTime = request.getSunEndTime();
    }
}
