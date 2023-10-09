package com.example.hanspaceback.controller;

import com.example.hanspaceback.domain.RegularReserve;
import com.example.hanspaceback.dto.request.RegularReserveRequest;
import com.example.hanspaceback.dto.request.ReserveRequest;
import com.example.hanspaceback.dto.response.RegularReserveResponse;
import com.example.hanspaceback.service.RegularReserveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class RegularReserveController {
    private final RegularReserveService regularReserveService;

    @PostMapping("/regularReserve")
//    public RegularReserveResponse create(@RequestBody RegularReserveRequest request, @RequestBody ReserveRequest reserveRequest){
    public RegularReserveResponse create(@RequestBody RegularReserveRequest request){
//        RegularReserveResponse response = regularReserveService.create(request, reserveRequest);
        RegularReserveResponse response = regularReserveService.create(request);
        return response;
    }

    @GetMapping("/regularReserve/list")
    public ResponseEntity<List<RegularReserve>> findAll(){
        return ResponseEntity.ok(regularReserveService.findAll());
    }
    @PatchMapping("regularReserve/{id}")
    public ResponseEntity<RegularReserve> update(@PathVariable Long id, @RequestBody RegularReserveRequest request){
        return ResponseEntity.ok(regularReserveService.update(id, request));
    }
    @DeleteMapping("regularReserve/{id}")
    public void delete(@PathVariable Long id){
        regularReserveService.delete(id);
    }
}
