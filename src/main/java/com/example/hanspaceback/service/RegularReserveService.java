package com.example.hanspaceback.service;

import com.example.hanspaceback.domain.RegularReserve;
import com.example.hanspaceback.dto.request.RegularReserveRequest;
import com.example.hanspaceback.dto.response.LongReserveResponse;
import com.example.hanspaceback.repository.LongReserveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LongReserveService {
    private final LongReserveRepository longReserveRepository;

    public LongReserveResponse create(RegularReserveRequest request){
        RegularReserve regularReserve = RegularReserve.builder()
                .week(request.getWeek())
                .startDate(request .getStartDate())
                .endDate(request.getEndDate())
                .build();
        longReserveRepository.save(regularReserve);

        LongReserveResponse response = new LongReserveResponse();
        response.setLongReserveId(regularReserve.getId());
        return response;
    }
    public List<RegularReserve> findAll(){
        return longReserveRepository.findAll();
    }
    public RegularReserve update(Long id, RegularReserveRequest request){
        RegularReserve regularReserve = longReserveRepository.findById(id).orElseThrow();
        regularReserve.update(request);
        return regularReserve;
    }
    public void delete(Long id){
        longReserveRepository.deleteById(id);
    }
}
