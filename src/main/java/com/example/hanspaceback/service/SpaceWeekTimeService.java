package com.example.hanspaceback.service;

import com.example.hanspaceback.domain.Department;
import com.example.hanspaceback.domain.Member;
import com.example.hanspaceback.domain.Space;
import com.example.hanspaceback.domain.SpaceWeekTime;
import com.example.hanspaceback.dto.request.MemberRequest;
import com.example.hanspaceback.dto.request.SpaceWeekTimeRequest;
import com.example.hanspaceback.dto.response.SpaceWeekTimeResponse;
import com.example.hanspaceback.repository.SpaceRepository;
import com.example.hanspaceback.repository.SpaceWeekTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SpaceWeekTimeService {
    private final SpaceWeekTimeRepository spaceWeekTimeRepository;
    private final SpaceRepository spaceRepository;
    public void create(SpaceWeekTimeRequest request){
        Space space = spaceRepository.findById(request.getSpaceId()).orElseThrow();
        SpaceWeekTime spaceWeekTime = SpaceWeekTime.builder()
                .space(space)
                .monStartTime(request.getMonStartTime())
                .tueStartTime(request.getTueStartTime())
                .wedStartTime(request.getWedStartTime())
                .thuStartTime(request.getThuStartTime())
                .friStartTime(request.getFriStartTime())
                .staStartTime(request.getSatStartTime())
                .sunStartTime(request.getSunStartTime())
                .monEndTime(request.getMonEndTime())
                .tueEndTime(request.getTueEndTime())
                .wedEndTime(request.getWedEndTime())
                .thuEndTime(request.getThuEndTime())
                .friEndTime(request.getFriEndTime())
                .staEndTime(request.getSatEndTime())
                .sunEndTime(request.getSunEndTime())
                .build();
        spaceWeekTimeRepository.save(spaceWeekTime);
    }
    public List<SpaceWeekTime> findAll(){
        return spaceWeekTimeRepository.findAll();
    }

    public SpaceWeekTime update(Long id, SpaceWeekTimeRequest request){
        SpaceWeekTime spaceWeekTime = spaceWeekTimeRepository.findById(id).get();
        spaceWeekTime.update(request);
        spaceWeekTimeRepository.save(spaceWeekTime);
        return spaceWeekTime;
    }

    public void delete(Long id){
        spaceWeekTimeRepository.deleteById(id);
    }
}
