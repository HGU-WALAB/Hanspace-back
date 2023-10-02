package com.example.hanspaceback.domain;

import com.example.hanspaceback.dto.request.UnavailableReserveRequest;
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
public class UnavailableReserve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spaceWeekTimeId")
    private Long id;
    private String week;
    private String unavailableDate;
    private String unavailableStart;
    private String unavailableEnd;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "spaceId")
    private Space space;

    public void update(UnavailableReserveRequest request){
        this.week = request.getWeek();
        this.unavailableDate = request.getUnavailableDate();
        this.unavailableStart = request.getUnavailableStart();
        this.unavailableEnd = request.getUnavailableEnd();
    }
}
