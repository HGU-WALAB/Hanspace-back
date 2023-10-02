package com.example.hanspaceback.controller;

import com.example.hanspaceback.domain.UnavailableReserve;
import com.example.hanspaceback.dto.request.UnavailableReserveRequest;
import com.example.hanspaceback.service.UnavailableReserveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UnavailableReserveController {
    private final UnavailableReserveService unavailableReserveService;
    @PostMapping("/unavailableReserve")
    public void create(@RequestBody UnavailableReserveRequest request){
        unavailableReserveService.create(request);
    }

    @GetMapping("/unavailableReserve/list")
    public ResponseEntity<List<UnavailableReserve>> findAll(){
        return ResponseEntity.ok(unavailableReserveService.findAll());
    }

    @PatchMapping("/unavailableReserve/{id}")
    public ResponseEntity<UnavailableReserve> update(@PathVariable Long id, @RequestBody UnavailableReserveRequest request){
        return ResponseEntity.ok(unavailableReserveService.update(id, request));
    }
    @DeleteMapping("/unavailableReserve/{id}")
    public void delete(@PathVariable Long id){
        unavailableReserveService.delete(id);
    }
}
