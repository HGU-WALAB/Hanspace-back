package com.example.hanspaceback.service;

import com.example.hanspaceback.domain.Space;
import com.example.hanspaceback.domain.UnavailableReserve;
import com.example.hanspaceback.dto.request.UnavailableReserveRequest;
import com.example.hanspaceback.repository.SpaceRepository;
import com.example.hanspaceback.repository.UnavailableReserveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UnavailableReserveService {
    private final UnavailableReserveRepository unavailableReserveRepository;
    private final SpaceRepository spaceRepository;
    public void create(UnavailableReserveRequest request){
        Space space = spaceRepository.findById(request.getSpaceId()).orElseThrow();
        UnavailableReserve unavailableReserve = UnavailableReserve.builder()
                .space(space)
                .week(request.getWeek())
                .unavailableDate(request.getUnavailableDate())
                .unavailableStart(request.getUnavailableStart())
                .unavailableEnd(request.getUnavailableEnd())
                .build();
        unavailableReserveRepository.save(unavailableReserve);
    }
    public List<UnavailableReserve> findAll(){
        return unavailableReserveRepository.findAll();
    }
    public UnavailableReserve update(Long id, UnavailableReserveRequest request){
        UnavailableReserve unavailableReserve = unavailableReserveRepository.findById(id).get();
        unavailableReserve.update(request);
        unavailableReserveRepository.save(unavailableReserve);
        return unavailableReserve;
    }
    public void delete(Long id){
        unavailableReserveRepository.deleteById(id);
    }
}
