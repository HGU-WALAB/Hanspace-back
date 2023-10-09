package com.example.hanspaceback.domain;

import com.example.hanspaceback.dto.request.ReserveRequest;
import jakarta.persistence.*;
import lombok.*;

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
    private String extraInfoAns;
//    private String deleteMember;

    @ManyToOne
    @JoinColumn(name = "spaceId")
    Space space;

    @ManyToOne
    @JoinColumn(name = "memberId")
    Member member;

    @ManyToOne(optional = true)
    @JoinColumn(name = "reserveReserveId", nullable = true)
    RegularReserve regularReserve;

    public void update(ReserveRequest request){
        this.startTime = request.getStartTime();
        this.endTime = request.getEndTime();
        this.headCount = request.getHeadCount();;
        this.groupName = request.getGroupName();
        this.purpose = request.getPurpose();
        this.phoneNumber = request.getPhoneNumber();
        this.approve = request.getApprove();
        this.extraInfoAns = request.getExtraInfoAns();
    }
}
