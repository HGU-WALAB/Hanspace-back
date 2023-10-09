package com.example.hanspaceback.domain;

import com.example.hanspaceback.dto.request.RegularReserveRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RegularReserve extends BaseEntity{
    @Id
    @Column(name = "regularReserveId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String week;
    private String startDate;
    private String endDate;

    public void update(RegularReserveRequest request){
        this.week = request.getWeek();
        this.startDate = request.getStartDate();
        this.endDate = request.getEndDate();
    }
    @JsonIgnore
    @OneToMany(mappedBy = "regularReserve", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserve> reserve = new ArrayList<>();
}
