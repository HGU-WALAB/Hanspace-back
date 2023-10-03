package com.example.hanspaceback.domain;

import com.example.hanspaceback.dto.request.SpaceRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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
    private String availableStart;
    private String availableEnd;
    private String detail;
    private String lableColor;
    private boolean availability;
    private String image;

    @ManyToOne
    @JoinColumn(name = "deptId")
    private Department department;

    public void update(SpaceRequest request){
        this.name = request.getName();
        this.headCount = request.getHeadCount();
        this.availableStart = request.getAvailableStart();
        this.availableEnd = request.getAvailableEnd();
        this.detail = request.getDetail();
//        this.lableColor = request.getLableColor();
        this.availability = request.isAvailability();
        this.image = request.getImage();
    }
    @JsonIgnore
    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserve> reserve = new ArrayList<>();

    @JsonIgnore
    @OneToOne(mappedBy = "space", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UnavailableReserve unavailableReserve;
}
