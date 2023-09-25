package com.example.hanspaceback.controller;

import com.example.hanspaceback.domain.SpaceWeekTime;
import com.example.hanspaceback.dto.request.SpaceWeekTimeRequest;
import com.example.hanspaceback.service.SpaceWeekTimeService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SpaceWeekTimeController {
    private final SpaceWeekTimeService spaceWeekTimeService;
    @PostMapping("/spaceWeekTime")
    public void create(@RequestBody SpaceWeekTimeRequest request){
//        System.out.println("requset.getspaceID: " + request.getSpaceId());
        spaceWeekTimeService.create(request);
    }

    @GetMapping("/spaceWeekTime/list")
    public ResponseEntity<List<SpaceWeekTime>> findAll(){
        return ResponseEntity.ok(spaceWeekTimeService.findAll());
    }

    @PatchMapping("/spaceWeekTime/{id}")
    public ResponseEntity<SpaceWeekTime> update(@PathVariable Long id, @RequestBody SpaceWeekTimeRequest request){
        return ResponseEntity.ok(spaceWeekTimeService.update(id, request));
    }
    @DeleteMapping("/spaceWeekTime/{id}")
    public void delete(@PathVariable Long id){
        spaceWeekTimeService.delete(id);
    }
}
