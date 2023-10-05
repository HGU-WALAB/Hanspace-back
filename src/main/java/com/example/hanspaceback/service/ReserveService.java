package com.example.hanspaceback.service;

import com.example.hanspaceback.domain.*;
import com.example.hanspaceback.dto.request.ReserveRequest;
import com.example.hanspaceback.dto.request.SpaceRequest;
import com.example.hanspaceback.repository.LongReserveRepository;
import com.example.hanspaceback.repository.MemberRepository;
import com.example.hanspaceback.repository.ReserveRepository;
import com.example.hanspaceback.repository.SpaceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ReserveService {
    private final ReserveRepository reserveRepository;
    private final MemberRepository memberRepository;
    private final SpaceRepository spaceRepository;
    private final LongReserveRepository longReserveRepository;

    public void create(ReserveRequest request, int i){
        Space space = spaceRepository.findById(request.getSpaceId()).orElseThrow();
        Member member = memberRepository.findById(request.getMemberId()).orElseThrow();
        LongReserve longReserve = null;
        if (request.getLongReserveId() != null) {
            longReserve = longReserveRepository.findById(request.getLongReserveId()).orElseThrow();
        }
        Reserve reserve = Reserve.builder()
                .reserveDate(request.getReserveDate()[i])
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .headCount(request.getHeadCount())
                .groupName(request.getGroupName())
                .purpose(request.getPurpose())
                .phoneNumber(request.getPhoneNumber())
                .approve(request.getApprove())
                .extraInfoAns(request.getExtraInfoAns())
                .member(member)
                .space(space)
                .longReserve(longReserve)
                .build();
        reserveRepository.save(reserve);
    }

    public List<Reserve> findReserveFetchJoin(){
        return reserveRepository.findReserveFetchJoin();
    }

    public Reserve update(Long id, ReserveRequest request){
        Reserve reserve = reserveRepository.findById(id).orElseThrow();
        reserve.update(request);
        reserveRepository.save(reserve);
        return reserve;
    }

    public void delete(Long id){
        reserveRepository.deleteById(id);
//        Reserve reserve = reserveRepository.findById(id).orElseThrow();
//        Member member = memberRepository.findById(id).get();
//        Reserve reserve = Reserve.builder()
//                .deleteMember(String.valueOf(member))
//                .build();
//        reserveRepository.update(reserve);
    }
}
