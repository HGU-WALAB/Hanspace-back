package com.example.hanspaceback.domain;

import com.example.hanspaceback.dto.request.ReserveRequest;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reserve extends BaseEntity{
    // groupName -> purpose
    // purpose -> detail
    // approve -> status
    // phoneNumber 삭제
    @Id
    @Column(name = "reserveId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reserveDate;
    private String startTime;
    private String endTime;
    private int headCount;
    private String purpose;
//    private String detail;
//    private String phoneNumber;
    private String status;
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
        this.reserveDate = request.getReserveDate();
        this.startTime = request.getStartTime();
        this.endTime = request.getEndTime();
        this.headCount = request.getHeadCount();
        this.purpose = request.getPurpose();
//        this.detail = request.getDetail();
//        this.phoneNumber = request.getPhoneNumber();
        this.status = request.getStatus();
        this.extraInfoAns = request.getExtraInfoAns();
    }
}
