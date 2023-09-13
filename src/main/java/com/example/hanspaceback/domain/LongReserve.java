package com.example.hanspaceback.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    private Date startDate;
    private Date endDate;



}
