package com.example.hanspaceback.domain;

import com.example.hanspaceback.dto.request.ReserveRequest;
import com.example.hanspaceback.repository.ReserveRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String reserveDate;
    private String startTime;
    private String endTime;
    private int headCount;
    private String groupName;
    private String purpose;
    private String phoneNumber;
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

    public void update(ReserveRequest request){
        this.reserveDate = request.getReserveDate();
        this.startTime = request.getStartTime();
        this.endTime = request.getEndTime();
        this.headCount = request.getHeadCount();;
        this.groupName = request.getGroupName();
        this.purpose = request.getPurpose();
        this.phoneNumber = request.getReserveDate();
        this.approve = request.getApprove();
        this.firstInfoAns = request.getFirstInfoAns();
        this.secondInfoAns = request.getSecondInfoAns();
    }
}
