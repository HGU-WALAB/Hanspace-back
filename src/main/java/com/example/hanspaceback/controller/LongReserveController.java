package com.example.hanspaceback.controller;

import com.example.hanspaceback.domain.LongReserve;
import com.example.hanspaceback.dto.request.LongReserveRequest;
import com.example.hanspaceback.repository.LongReserveRepository;
import com.example.hanspaceback.service.LongResrveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LongReserveController {
    private final LongResrveService longResrveService;

    @PostMapping("/longReserve")
    public void create(@RequestBody LongReserveRequest request){
        longResrveService.create(request);
    }

    @GetMapping("/longReserve/list")
    public ResponseEntity<List<LongReserve>> findAll(){
        return ResponseEntity.ok(longResrveService.findAll());
    }
    @PatchMapping("longReserve/{id}")
    public ResponseEntity<LongReserve> update(@PathVariable Long id, @RequestBody LongReserveRequest request){
        return ResponseEntity.ok(longResrveService.update(id, request));
    }
    @DeleteMapping("longReserve/{id}")
    public void delete(@PathVariable Long id){
        longResrveService.delete(id);
    }
}
