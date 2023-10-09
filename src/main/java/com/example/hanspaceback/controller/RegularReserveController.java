package com.example.hanspaceback.controller;

import com.example.hanspaceback.domain.LongReserve;
import com.example.hanspaceback.dto.request.LongReserveRequest;
import com.example.hanspaceback.dto.response.LongReserveResponse;
import com.example.hanspaceback.service.LongReserveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class LongReserveController {
    private final LongReserveService longReserveService;

    @PostMapping("/longReserve")
    public LongReserveResponse create(@RequestBody LongReserveRequest request){
        LongReserveResponse response = longReserveService.create(request);
        return response;
    }

    @GetMapping("/longReserve/list")
    public ResponseEntity<List<LongReserve>> findAll(){
        return ResponseEntity.ok(longReserveService.findAll());
    }
    @PatchMapping("longReserve/{id}")
    public ResponseEntity<LongReserve> update(@PathVariable Long id, @RequestBody LongReserveRequest request){
        return ResponseEntity.ok(longReserveService.update(id, request));
    }
    @DeleteMapping("longReserve/{id}")
    public void delete(@PathVariable Long id){
        longReserveService.delete(id);
    }
}
