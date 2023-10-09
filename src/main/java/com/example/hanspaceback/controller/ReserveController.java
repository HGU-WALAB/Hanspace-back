package com.example.hanspaceback.controller;

import com.example.hanspaceback.domain.Reserve;
import com.example.hanspaceback.dto.request.ReserveRequest;
import com.example.hanspaceback.dto.response.ReserveResponse;
import com.example.hanspaceback.repository.ReserveRepository;
import com.example.hanspaceback.service.ReserveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class ReserveController {
    private final ReserveService reserveService;

    @PostMapping("/reserve")
    public void create(@RequestBody ReserveRequest request){
        reserveService.create(request);
    }

    @GetMapping("/reserve/list")
    public ResponseEntity<List<Reserve>> findReserveFetchJoin(){
        return ResponseEntity.ok(reserveService.findReserveFetchJoin());
    }
//    @GetMapping("/reserve/list")
//    public ResponseEntity<List<ReserveResponse>> findReserveFetchJoin(){
//        return ResponseEntity.ok(reserveService.findReserveFetchJoin());
//    }

    @PatchMapping("/reserve/{id}")
    public ResponseEntity<Reserve> update(@PathVariable Long id, @RequestBody ReserveRequest request){
        return ResponseEntity.ok(reserveService.update(id, request));
    }

    @DeleteMapping("/reserve/{id}")
    public void delete(@PathVariable Long id){
        reserveService.delete(id);
    }
}
