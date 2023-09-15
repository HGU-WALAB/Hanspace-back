package com.example.hanspaceback.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reserve extends BaseEntity{
    @Id
    @Column(name = "reserveId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private String startTime;
    private String endTime;
    private int headCount;
    private String groupName;
    private String purpose;
    private String phoneNumber;
//    @ColumnDefault("pending approval")
    private String approve;
    private String firstInfoAns;
    private String secondInfoAns;
    @ManyToOne
    @JoinColumn(name = "spaceId")
    Space space;

    @ManyToOne
    @JoinColumn(name = "memberId")
    Member member;

    @ManyToOne
    @JoinColumn(name = "longReserveId")
    LongReserve longReserve;
}
