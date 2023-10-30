package com.example.hanspaceback.domain;

import com.example.hanspaceback.dto.request.DeptMemberRequest;
import com.example.hanspaceback.dto.request.ReserveMemberRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReserveMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserveMemberId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reserveId")
    private Reserve reserve;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

//    public void update(ReserveMemberRequest request){
//        this.reserve.getId() = request.getReserveId();
//        this.member.getMemberId() = request.getMemberId();
//    }
}
