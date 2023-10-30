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
@RequestMapping("/regularReserve")
public class RegularReserveController {
    private final RegularReserveService regularReserveService;
    @GetMapping("/list")
    public ResponseEntity<List<RegularReserve>> findAll(){
        return ResponseEntity.ok(regularReserveService.findAll());
    }
    @PatchMapping("/{id}")
    public ResponseEntity<RegularReserve> update(@PathVariable Long id, @RequestBody RegularReserveRequest request){
        return ResponseEntity.ok(regularReserveService.update(id, request));
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        regularReserveService.delete(id);
    }
}
