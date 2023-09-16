package com.example.hanspaceback.service;

import com.example.hanspaceback.domain.LongReserve;
import com.example.hanspaceback.dto.request.LongReserveRequest;
import com.example.hanspaceback.repository.LongReserveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LongResrveService {
    private final LongReserveRepository longReserveRepository;

    public void create(LongReserveRequest request){
        LongReserve longReserve = LongReserve.builder()
                .week(request.getWeek())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();
        longReserveRepository.save(longReserve);
    }
    public List<LongReserve> findAll(){
        return longReserveRepository.findAll();
    }
    public LongReserve update(Long id, LongReserveRequest request){
        LongReserve longReserve = longReserveRepository.findById(id).orElseThrow();
        longReserve.update(request);
        return longReserve;
    }
    public void delete(Long id){
        longReserveRepository.deleteById(id);
    }
}
