package com.example.hanspaceback.service;

import com.example.hanspaceback.domain.DeptMember;
import com.example.hanspaceback.domain.Member;
import com.example.hanspaceback.domain.Reserve;
import com.example.hanspaceback.domain.ReserveMember;
import com.example.hanspaceback.dto.request.ReserveMemberRequest;
import com.example.hanspaceback.dto.response.DeptMemberResponse;
import com.example.hanspaceback.dto.response.ReserveMemberResponse;
import com.example.hanspaceback.repository.MemberRepository;
import com.example.hanspaceback.repository.ReserveMemberRepository;
import com.example.hanspaceback.repository.ReserveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReserveMemberService {
    private final ReserveRepository reserveRepository;
    private final MemberRepository memberRepository;
    private final ReserveMemberRepository reserveMemberRepository;

    public void create(ReserveMemberRequest request){
        Reserve reserve = reserveRepository.findById(request.getReserveId()).orElseThrow();
        Member member = memberRepository.findById(request.getMemberId()).orElseThrow();

//        Reserve reserve = Reserve.builder()
//
//                .build();
//        reserveRepository.save(reserve);

        ReserveMember reserveMember = ReserveMember.builder()
                .reserve(reserve)
                .member(member)
                .build();
        reserveMemberRepository.save(reserveMember);
    }

    public List<ReserveMemberResponse> findByMemberId(Long memberId) {
//        return reserveMemberRepository.findByMember_MemberId(memberId);
        List<ReserveMember> reserveMembers = reserveMemberRepository.findByMember_MemberId(memberId);
        List<ReserveMemberResponse> responses = new ArrayList<>();

        for (ReserveMember reserveMember : reserveMembers) {
            if (reserveMember.getReserve() == null || reserveMember.getMember() == null) {
                continue;
            }
            ReserveMemberResponse response = new ReserveMemberResponse();
            response.setReserveId(reserveMember.getReserve().getId());
            response.setMemberId(reserveMember.getMember().getMemberId());
            responses.add(response);
        }
        return responses;
    }
}
