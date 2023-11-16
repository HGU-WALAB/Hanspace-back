package com.example.hanspaceback.service;

import com.example.hanspaceback.domain.DeptMember;
import com.example.hanspaceback.domain.Member;
import com.example.hanspaceback.domain.Reserve;
import com.example.hanspaceback.domain.ReserveMember;
import com.example.hanspaceback.dto.request.ReserveMemberRequest;
import com.example.hanspaceback.dto.response.DeptMemberResponse;
import com.example.hanspaceback.dto.response.ReserveMemberResponse;
import com.example.hanspaceback.dto.response.ReserveResponse;
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

    public List<ReserveResponse> findByMemberId(Long memberId) {
//        return reserveMemberRepository.findByMember_MemberId(memberId);
        List<ReserveMember> reserveMembers = reserveMemberRepository.findByMember_MemberId(memberId);
        List<ReserveResponse> responses = new ArrayList<>();

        for (ReserveMember reserveMember : reserveMembers) {
            if (reserveMember.getReserve() == null || reserveMember.getMember() == null) {
                continue;
            }
            ReserveResponse response = new ReserveResponse();
            Reserve reserve = reserveMember.getReserve();

            response.setReserveId(reserve.getId());
            response.setMemberId(reserveMember.getMember().getMemberId());
            response.setReserveDate(reserve.getReserveDate());
            response.setStartTime(reserve.getStartTime());
            response.setEndTime(reserve.getEndTime());
            response.setHeadCount(reserve.getHeadCount());
            response.setPurpose(reserve.getPurpose());
            response.setStatus(reserve.getStatus());
            response.setExtraInfoAns(reserve.getExtraInfoAns());
            response.setInvitedMemberEmail(reserve.getInvitedMemberEmail());
            response.setSpaceId(reserve.getSpace().getSpaceId());
            response.setSpaceName(reserve.getSpace().getName());
            responses.add(response);
        }
        return responses;
    }
}
