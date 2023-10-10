package com.example.hanspaceback.service;

import com.example.hanspaceback.domain.*;
import com.example.hanspaceback.dto.request.ReserveRequest;
import com.example.hanspaceback.repository.RegularReserveRepository;
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

    public void create(ReserveRequest request){
        Space space = spaceRepository.findById(request.getSpaceId()).orElseThrow();
        Member member = memberRepository.findById(request.getMemberId()).orElseThrow();
        Reserve reserve = Reserve.builder()
                .reserveDate(request.getReserveDate()[0])
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
                .build();
        reserveRepository.save(reserve);
    }

    public List<Reserve> findReserveFetchJoin(){
        return reserveRepository.findReserveFetchJoin();
    }
    public List<Reserve> findBySpaceId(Long spaceId){
        List<Reserve> reserves = reserveRepository.findBySpace_SpaceId(spaceId);
        return reserves;
    }
    public List<Reserve> findByDeptId(Long deptId){
        List<Reserve> reserves = reserveRepository.findBySpace_Department_deptId(deptId);
        return reserves;
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
