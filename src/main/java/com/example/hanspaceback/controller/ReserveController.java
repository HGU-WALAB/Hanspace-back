package com.example.hanspaceback.controller;

import com.example.hanspaceback.domain.Reserve;
import com.example.hanspaceback.dto.request.RegularReserveRequest;
import com.example.hanspaceback.dto.request.ReserveRequest;
import com.example.hanspaceback.dto.response.ReserveResponse;
import com.example.hanspaceback.dto.response.SpaceWithReservesResponse;
import com.example.hanspaceback.service.RegularReserveService;
import com.example.hanspaceback.service.ReserveMemberService;
import com.example.hanspaceback.service.ReserveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("hanSpace/reserve")
public class ReserveController {
    private final ReserveService reserveService;
    private final ReserveMemberService reserveMemberService;
    private final RegularReserveService regularReserveService;
    @PostMapping
    public void create(@RequestBody ReserveRequest request){
        reserveService.create(request);
    }
    @PostMapping("/regularReserve")
    public ReserveResponse create(@RequestBody RegularReserveRequest request){
//        RegularReserveResponse response = regularReserveService.create(request, reserveRequest);
        ReserveResponse response = regularReserveService.create(request);
        return response;
    }
    @GetMapping("/count")
    public Long countReserve(@RequestBody ReserveRequest request){
        return reserveService.countReserve(request.getSpaceId());
    }
    @GetMapping("/list")
    public ResponseEntity<List<Reserve>> findReserveFetchJoin(){
        return ResponseEntity.ok(reserveService.findReserveFetchJoin());
    }
    // reserveMember findByMemberId
    @GetMapping("/list/member/{memberId}")
    public ResponseEntity<List<ReserveResponse>> findAll(@PathVariable Long memberId){
        return ResponseEntity.ok(reserveMemberService.findByMemberId(memberId));
    }
    @GetMapping("/list/{spaceId}")
    public ResponseEntity<List<Reserve>> findBySpaceId(@PathVariable Long spaceId){
        return ResponseEntity.ok(reserveService.findBySpaceId(spaceId));
    }
    @GetMapping("/{deptId}")
    public ResponseEntity<List<Reserve>> findByDeptId(@PathVariable Long deptId){
        return ResponseEntity.ok(reserveService.findByDeptId(deptId));
    }

    @PostMapping("/{deptId}/list")
    public ResponseEntity<List<SpaceWithReservesResponse>> findByDate(@PathVariable Long deptId, @RequestBody ReserveRequest request){
        return ResponseEntity.ok(reserveService.findByReserveDateFetchJoin(deptId, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Reserve> update(@PathVariable Long id, @RequestBody ReserveRequest request){
        return ResponseEntity.ok(reserveService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        reserveService.delete(id);
    }
}
