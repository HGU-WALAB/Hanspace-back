package com.example.hanspaceback.service;

import com.example.hanspaceback.domain.*;
import com.example.hanspaceback.dto.request.ReserveRequest;
import com.example.hanspaceback.dto.response.DepartmentResponse;
import com.example.hanspaceback.dto.response.ReserveResponse;
import com.example.hanspaceback.dto.response.SpaceWithReservesResponse;
import com.example.hanspaceback.jwt.CustomUserDetails;
import com.example.hanspaceback.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class ReserveService {
    private final ReserveRepository reserveRepository;
    private final MemberRepository memberRepository;
    private final SpaceRepository spaceRepository;
    private final ReserveMemberRepository reserveMemberRepository;
    public void create(Long memberId, ReserveRequest request){
        Space space = spaceRepository.findById(request.getSpaceId()).orElseThrow();
        Member member = memberRepository.findById(memberId).orElseThrow();
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
//                .createMemberId(memberId)
                .createMemberName(member.getName())
                .build();
        reserveRepository.save(reserve);

        ReserveMember reservingReserveMember = ReserveMember.builder()
                .reserve(reserve)
                .member(member)
                .build();
        reserveMemberRepository.save(reservingReserveMember);

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

    public List<SpaceWithReservesResponse> findByReserveDateFetchJoin(Long deptId, ReserveRequest request){
        List<Reserve> reserves = reserveRepository.findByReserveDateFetchJoin(request.getReserveDate());
        List<Long> spaceIds = new ArrayList<>();
        List<Space> spaces = new ArrayList<>();
        List<SpaceWithReservesResponse> spaceWithReserveResponses = new ArrayList<>();
        List<ReserveResponse> reserveResponses = new ArrayList<>();

        for (Reserve reserve : reserves) {
            if (reserve.getSpace().getDepartment().getDeptId().equals(deptId)) {
                Long spaceId = reserve.getSpace().getSpaceId();
                if (!spaceIds.contains(spaceId)) {
                    spaceIds.add(spaceId);
                }
                ReserveResponse response = new ReserveResponse();
                response.setReserveId(reserve.getId());
                response.setReserveDate(reserve.getReserveDate());
                response.setPurpose(reserve.getPurpose());
                if (reserve.getRegularReserve() != null) {
                    response.setRegularReserveId(reserve.getRegularReserve().getId());
                }
                response.setHeadCount(reserve.getHeadCount());
                response.setEndTime(reserve.getEndTime());
//                response.setMemberId(reserve.getCreateMemberId());
                response.setMemberName(reserve.getCreateMemberName());
                response.setStatus(reserve.getStatus());
                response.setInvitedMemberEmail(reserve.getInvitedMemberEmail());
                response.setStartTime(reserve.getStartTime());
                response.setExtraInfoAns(reserve.getExtraInfoAns());
                reserveResponses.add(response);
            }
        }

        for (Long spaceId : spaceIds) {
            Space space = spaceRepository.findById(spaceId).orElseThrow();
            spaces.add(space);
        }

        for(Space space : spaces){
            SpaceWithReservesResponse sr = new SpaceWithReservesResponse(space,reserveResponses);

            spaceWithReserveResponses.add(sr);
        }
        return spaceWithReserveResponses;
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

//    public List<Reserve> findByDeptIdMemberId(Long memberId, Long deptId) {
//        List<ReserveMember> reserveMembers = reserveMemberRepository.findByMember_MemberId(memberId);
//        List<Reserve> reserves = reserveRepository.findBySpace_Department_deptId(deptId);
//        return reserves;
//    }

    public List<Reserve> findByDeptIdMemberId(Long memberId, Long deptId) {
        // memberId로 ReserveMember 리스트 찾기
        List<ReserveMember> reserveMembers = reserveMemberRepository.findByMember_MemberId(memberId);

        // memberId에 해당하는 Reserve 리스트 필터링
        List<Reserve> filteredReserves = reserveMembers.stream()
                .map(ReserveMember::getReserve) // ReserveMember에서 Reserve 객체로 변환
//                .filter(reserve -> reserve.getSpace().getDepartment().getDeptId().equals(deptId)) // deptId가 일치하는 Reserve만 필터링
                .filter(Objects::nonNull)
                .filter(reserve -> {
                    Space space = reserve.getSpace();
                    return space != null && space.getDepartment() != null && space.getDepartment().getDeptId() == deptId;
                })
//                .filter(reserve -> reserve.getSpace().getDepartment().getDeptId() == deptId)
                .collect(Collectors.toList()); // 결과를 List로 수집
        return filteredReserves;
    }
}
