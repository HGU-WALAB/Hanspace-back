package com.example.hanspaceback.domain;

import com.example.hanspaceback.dto.request.ReserveRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
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
    private String purpose;
    private String status;
    private String extraInfoAns;
    private String invitedMemberEmail;
//    private Long createMemberId;
    private String createMemberName;

    @ManyToOne
    @JoinColumn(name = "spaceId")
    Space space;

//    @ManyToOne
//    @JoinColumn(name = "memberId")
//    Member member;
    @JsonIgnore
    @OneToMany(mappedBy = "reserve", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReserveMember> reserveMember = new ArrayList<>();
    @ManyToOne(optional = true)
    @JoinColumn(name = "regularReserveId", nullable = true)
    RegularReserve regularReserve;

    public void update(ReserveRequest request){
        this.reserveDate = request.getReserveDate();
        this.startTime = request.getStartTime();
        this.endTime = request.getEndTime();
        this.headCount = request.getHeadCount();
        this.purpose = request.getPurpose();
        this.status = request.getStatus();
        this.extraInfoAns = request.getExtraInfoAns();
        this.invitedMemberEmail = request.getInvitedMemberEmail();
    }
}
