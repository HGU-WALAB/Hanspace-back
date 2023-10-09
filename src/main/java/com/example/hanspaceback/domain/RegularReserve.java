package com.example.hanspaceback.domain;

import com.example.hanspaceback.dto.request.LongReserveRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LongReserve extends BaseEntity{
    @Id
    @Column(name = "longReserveId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String week;
    private String startDate;
    private String endDate;

    public void update(LongReserveRequest request){
        this.week = request.getWeek();
        this.startDate = request.getStartDate();
        this.endDate = request.getEndDate();
    }
    @JsonIgnore
    @OneToMany(mappedBy = "longReserve", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserve> reserve = new ArrayList<>();
}
