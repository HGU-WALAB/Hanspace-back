package com.example.hanspaceback.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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
//    @ColumnDefault("black")
    private String color;
//    @ColumnDefault("true")
    private boolean userAccept;
//    @ColumnDefault("30")
    private int maxRserveCount;
    private String link;
    private String firstInfo;
    private String secondInfo;
    private String siteInfoTitle;
    private String siteInfoDetail;
}
