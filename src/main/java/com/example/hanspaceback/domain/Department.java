package com.example.hanspaceback.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Department extends BaseEntity{
    // NonNull이 필요할까?
    @Id
    @Column(name = "deptId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String siteName;
    private String deptName;
    private String logo;
    private String color;
    private boolean userAccept;
    private int maxRserveCount;
    private String link;
    private String firstInfo;
    private String secondInfo;
    private String siteInfoTitle;
    private String siteInfoDetail;
}
