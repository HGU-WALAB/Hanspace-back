package com.example.hanspaceback.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Space extends BaseEntity{
    @Id
    @Column(name = "spaceId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int headCount;
    private String startTime;
    private String endTime;
    private String detail;
    private String lableColor;

    @ColumnDefault("true")
    private boolean availability;
    private String image;
    private Date unusableDate;

    @ManyToOne
    @JoinColumn(name = "deptId")
    private Department department;
}
