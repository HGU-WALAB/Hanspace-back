package com.example.hanspaceback.service;

import com.example.hanspaceback.domain.*;
import com.example.hanspaceback.dto.request.ReserveRequest;
import com.example.hanspaceback.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ReserveService {
    private final ReserveRepository reserveRepository;
    private final MemberRepository memberRepository;
    private final SpaceRepository spaceRepository;
    private final ReserveMemberRepository reserveMemberRepository;
    public void create(ReserveRequest request){
        Space space = spaceRepository.findById(request.getSpaceId()).orElseThrow();
//        Member member = memberRepository.findById(request.getMemberId()).orElseThrow();
        Reserve reserve = Reserve.builder()
                .reserveDate(request.getReserveDate())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .headCount(request.getHeadCount())
                .purpose(request.getPurpose())
                .status(request.getStatus())
                .extraInfoAns(request.getExtraInfoAns())
                .invitedMemberEmail(request.getInvitedMemberEmail())
                .space(space)
                .build();
        reserveRepository.save(reserve);

        String invitedMemberEmail = request.getInvitedMemberEmail();

        if (invitedMemberEmail != null && !invitedMemberEmail.trim().isEmpty()) {
            List<String> emailsToProcess = new ArrayList<>();

            if (invitedMemberEmail.contains(",")) {
                emailsToProcess.addAll(Arrays.asList(invitedMemberEmail.split(",")));
            } else {
                emailsToProcess.add(invitedMemberEmail.trim());
            }

            for (String email : emailsToProcess) {
                email = email.trim();
                Member invitedMember = memberRepository.findByEmail(email);

                if (invitedMember == null) {
                    throw new IllegalArgumentException("No member found with email: " + email);
                }

                ReserveMember reserveMember = ReserveMember.builder()
                        .reserve(reserve)
                        .member(invitedMember)
                        .build();

                System.out.println("member email : " + email);
                reserveMemberRepository.save(reserveMember);
            }
        }

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

    public Long countReserve(Long spaceId) {
        Space space = spaceRepository.findById(spaceId).get();
        if(space == null) {
            throw new IllegalArgumentException("Space not found with ID: " + spaceId);
        }
        Long deptId = space.getDepartment().getDeptId();
        return reserveRepository.countByStatusAndSpace_Department_DeptId("미승인", deptId);
    }

}
