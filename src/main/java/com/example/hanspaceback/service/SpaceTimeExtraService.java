package com.example.hanspaceback.service;

import com.example.hanspaceback.domain.Space;
import com.example.hanspaceback.domain.SpaceTimeExtra;
import com.example.hanspaceback.dto.request.SpaceTimeExtraRequest;
import com.example.hanspaceback.dto.response.SpaceResponse;
import com.example.hanspaceback.dto.response.SpaceTimeExtraResponse;
import com.example.hanspaceback.repository.SpaceRepository;
import com.example.hanspaceback.repository.SpaceTimeExtraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SpaceTimeExtraService {
    private final SpaceTimeExtraRepository spaceTimeExtraRepository;
    private final SpaceRepository spaceRepository;
    public void create(SpaceTimeExtraRequest request){
        Space space = spaceRepository.findById(request.getSpaceId()).orElseThrow();
        SpaceTimeExtra spaceTimeExtra = SpaceTimeExtra.builder()
                .space(space)
                .week(request.getWeek())
                .extraUnavailableStart(request.getExtraUnavailableStart())
                .extraUnavailableEnd(request.getExtraUnavailableEnd())
                .extraStartDate(request.getExtraStartDate())
                .extraEndDate(request.getExtraEndDate())
                .build();
        spaceTimeExtraRepository.save(spaceTimeExtra);
    }
//    public List<SpaceTimeExtra> findAll(){
//        return spaceTimeExtraRepository.findAll();
//    }
    public List<SpaceTimeExtraResponse> findAll(){
        List<SpaceTimeExtra> spaceTimeExtras = spaceTimeExtraRepository.findAll();
        List<SpaceTimeExtraResponse> responses = new ArrayList<>();

//        return spaceTimeExtraRepository.findAll();
        return responses;
    }
    public SpaceTimeExtra update(Long id, SpaceTimeExtraRequest request){
        SpaceTimeExtra spaceTimeExtra = spaceTimeExtraRepository.findById(id).get();
        spaceTimeExtra.update(request);
        spaceTimeExtraRepository.save(spaceTimeExtra);
        return spaceTimeExtra;
    }
    public void delete(Long id){
        spaceTimeExtraRepository.deleteById(id);
    }
}
