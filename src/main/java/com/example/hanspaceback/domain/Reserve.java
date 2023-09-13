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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private Date startTime;
    private Date endTime;
    private int headCount;
    private String groupName;
    private String purpose;
    private String phoneNumber;

//    @ColumnDefault("pending approval")
    private String approve;

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
