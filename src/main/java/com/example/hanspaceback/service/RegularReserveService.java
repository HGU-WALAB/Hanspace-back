package com.example.hanspaceback.service;

import com.example.hanspaceback.domain.*;
import com.example.hanspaceback.dto.request.RegularReserveRequest;
import com.example.hanspaceback.dto.request.ReserveRequest;
import com.example.hanspaceback.dto.response.RegularReserveResponse;
import com.example.hanspaceback.dto.response.ReserveResponse;
import com.example.hanspaceback.repository.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RegularReserveService {
    private final RegularReserveRepository regularReserveRepository;
    private final ReserveRepository reserveRepository;
    private final SpaceRepository spaceRepository;
    private final MemberRepository memberRepository;
    private final ReserveMemberRepository reserveMemberRepository;

    public ReserveResponse create(Long memberId, RegularReserveRequest request){
            RegularReserve regularReserve = RegularReserve.builder()
                .week(request.getWeek())
                .startDate(request .getStartDate())
                .endDate(request.getEndDate())
                .build();
        regularReserveRepository.save(regularReserve);

        Reserve lastReserve = null;
        List<String> reserveDates = new ArrayList<>(); // 예약 날짜를 저장할 리스트

        for(int i = 0; i < request.getReserveCount(); i++) {
            Space space = spaceRepository.findById(request.getSpaceId()).orElseThrow();
            Member member = memberRepository.findById(memberId).orElseThrow();

            Reserve reserve = Reserve.builder()
                    .reserveDate(request.getReserveDate()[i])
                    .startTime(request.getStartTime())
                    .endTime(request.getEndTime())
                    .headCount(request.getHeadCount())
                    .purpose(request.getPurpose())
                    .status(request.getStatus())
                    .extraInfoAns(request.getExtraInfoAns())
                    .regularReserve(regularReserve)
                    .space(space)
//                    .member(member)
                    .build();
            reserveRepository.save(reserve);
            lastReserve = reserve;
            reserveDates.add(reserve.getReserveDate());

            ReserveMember reserveMember = ReserveMember.builder()
                    .reserve(reserve)
                    .member(member)
                    .build();
            reserveMemberRepository.save(reserveMember);
        }

        ReserveResponse response = new ReserveResponse();
        response.setReserveId(lastReserve.getId());
        response.setSpaceId(lastReserve.getSpace().getSpaceId());
        response.setRegularReserveId(regularReserve.getId());
        response.setStartTime(lastReserve.getStartTime());
        response.setEndTime(lastReserve.getEndTime());
        response.setHeadCount(lastReserve.getHeadCount());
        response.setPurpose(lastReserve.getPurpose());
        response.setStatus(lastReserve.getStatus());
        response.setExtraInfoAns(lastReserve.getExtraInfoAns());
        response.setReserveDate(String.join(", ", reserveDates));
        return response;
    }
    public List<RegularReserve> findAll(){
        return regularReserveRepository.findAll();
    }
    public RegularReserve update(Long id, RegularReserveRequest request){
        RegularReserve regularReserve = regularReserveRepository.findById(id).orElseThrow();
        regularReserve.update(request);

        List<Reserve> reserves = reserveRepository.findByRegularReserve_Id(id);
        for (int i = 0; i < request.getReserveCount(); i++) {
            if (i < reserves.size()) {
                Reserve reserve = reserves.get(i);
                reserve.setReserveDate(request.getReserveDate()[i]);
                reserve.setStartTime(request.getStartTime());
                reserve.setEndTime(request.getEndTime());
                reserve.setHeadCount(request.getHeadCount());
                reserve.setPurpose(request.getPurpose());
                reserve.setStatus(request.getStatus());
                reserve.setExtraInfoAns(request.getExtraInfoAns());
            }
        }

        return regularReserve;
    }
    public void delete(Long id){
        regularReserveRepository.deleteById(id);
    }

    public void processCsvFile(MultipartFile file, Long memberId) throws IOException {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                RegularReserve regularReserve = RegularReserve.builder()
                        .week(csvRecord.get("사용할 요일"))
                        .startDate(csvRecord.get("사용 시작일"))
                        .endDate(csvRecord.get("사용 종료일"))
                        .build();
                regularReserveRepository.save(regularReserve);
//                Reserve reserve = Reserve.builder()
//                        .reserveDate(csvRecord.get("reserveDate"))
//                        .startTime(csvRecord.get("시작 시간"))
//                        .endTime(csvRecord.get("종료 시간"))
////                        .headCount(Integer.parseInt(csvRecord.get("headCount")))
//                        .purpose(csvRecord.get("목적"))
//                        .status("미승인")
//                        .extraInfoAns(csvRecord.get("기타"))
////                        .invitedMemberEmail(csvRecord.get("invitedMemberEmail"))
//                        .build();
//                reserveRepository.save(reserve);
            }
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
