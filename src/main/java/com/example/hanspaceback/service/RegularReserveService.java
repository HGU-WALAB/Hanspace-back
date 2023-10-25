package com.example.hanspaceback.service;

import com.example.hanspaceback.domain.Member;
import com.example.hanspaceback.domain.RegularReserve;
import com.example.hanspaceback.domain.Reserve;
import com.example.hanspaceback.domain.Space;
import com.example.hanspaceback.dto.request.RegularReserveRequest;
import com.example.hanspaceback.dto.request.ReserveRequest;
import com.example.hanspaceback.dto.response.RegularReserveResponse;
import com.example.hanspaceback.repository.MemberRepository;
import com.example.hanspaceback.repository.RegularReserveRepository;
import com.example.hanspaceback.repository.ReserveRepository;
import com.example.hanspaceback.repository.SpaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RegularReserveService {
    private final RegularReserveRepository regularReserveRepository;
    private final ReserveRepository reserveRepository;
    private final SpaceRepository spaceRepository;
    private final MemberRepository memberRepository;

    public RegularReserveResponse create(RegularReserveRequest request){
            RegularReserve regularReserve = RegularReserve.builder()
                .week(request.getWeek())
                .startDate(request .getStartDate())
                .endDate(request.getEndDate())
                .build();
        regularReserveRepository.save(regularReserve);

        for(int i = 0; i < request.getReserveCount(); i++) {
            Space space = spaceRepository.findById(request.getSpaceId()).orElseThrow();
            Member member = memberRepository.findById(request.getMemberId()).orElseThrow();

            Reserve reserve = Reserve.builder()
                    .reserveDate(request.getReserveDate()[i])
                    .startTime(request.getStartTime())
                    .endTime(request.getEndTime())
                    .headCount(request.getHeadCount())
//                    .groupName(request.getGroupName())
                    .purpose(request.getPurpose())
                    .detail(request.getDetail())
//                    .phoneNumber(request.getPhoneNumber())
                    .status(request.getStatus())
                    .extraInfoAns(request.getExtraInfoAns())
                    .regularReserve(regularReserve)
                    .space(space)
                    .member(member)
                    .build();
            reserveRepository.save(reserve);
        }

        RegularReserveResponse response = new RegularReserveResponse();
        response.setLongReserveId(regularReserve.getId());
        return response;
    }
    public List<RegularReserve> findAll(){
        return regularReserveRepository.findAll();
    }
    public RegularReserve update(Long id, RegularReserveRequest request){
//        RegularReserve regularReserve = regularReserveRepository.findById(id).orElseThrow();
//        regularReserve.update(request);
        RegularReserve regularReserve = regularReserveRepository.findById(id).orElseThrow();
        regularReserve.update(request);

        List<Reserve> reserves = reserveRepository.findByRegularReserve_Id(id);
        for (int i = 0; i < request.getReserveCount(); i++) {
            if (i < reserves.size()) {
                Reserve reserve = reserves.get(i);
                // existingReserve의 값을 request로부터 업데이트하세요.
                // 예: existingReserve.setReserveDate(request.getReserveDate()[i]);
                reserve.setReserveDate(request.getReserveDate()[i]);
                reserve.setStartTime(request.getStartTime());
                reserve.setEndTime(request.getEndTime());
                reserve.setHeadCount(request.getHeadCount());
//                reserve.setGroupName(request.getGroupName());
                reserve.setPurpose(request.getPurpose());
                reserve.setDetail(reserve.getDetail());
                reserve.setStatus(request.getStatus());
                reserve.setExtraInfoAns(request.getExtraInfoAns());
            }
        }

        return regularReserve;
    }
    public void delete(Long id){
        regularReserveRepository.deleteById(id);
    }
}
