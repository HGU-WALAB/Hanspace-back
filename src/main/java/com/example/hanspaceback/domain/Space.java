package com.example.hanspaceback.domain;

import com.example.hanspaceback.dto.request.SpaceRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Space extends BaseEntity{
    @Id
    @Column(name = "spaceId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long spaceId;
    private String name;
    private int headCount;
    private String startTime;
    private String endTime;
    private String detail;
    private String lableColor;
    private boolean availability;
    private String image;
    private String unusableDate;

    @ManyToOne
    @JoinColumn(name = "deptId")
    private Department department;

    public void update(SpaceRequest request){
        this.name = request.getName();
        this.headCount = request.getHeadCount();
        this.startTime = request.getStartTime();
        this.endTime = request.getEndTime();
        this.detail = request.getDetail();
        this.lableColor = request.getLableColor();
        this.availability = request.isAvailability();
        this.image = request.getImage();
        this.unusableDate = request.getUnusableDate();
    }
    @JsonIgnore
    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserve> reserve = new ArrayList<>();

//    @OneToOne(mappedBy = "space", fetch = FetchType.LAZY)
//    private SpaceWeekTime spaceWeekTime;
    @JsonIgnore
    @OneToOne(mappedBy = "space", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private SpaceWeekTime spaceWeekTime;
}
