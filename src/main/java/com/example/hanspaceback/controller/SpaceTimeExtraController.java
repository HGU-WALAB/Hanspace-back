package com.example.hanspaceback.controller;

import com.example.hanspaceback.domain.SpaceTimeExtra;
import com.example.hanspaceback.dto.request.SpaceTimeExtraRequest;
import com.example.hanspaceback.dto.response.SpaceTimeExtraResponse;
import com.example.hanspaceback.service.SpaceTimeExtraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class SpaceTimeExtraController {
    private final SpaceTimeExtraService spaceTimeExtraService;
    @PostMapping("/spaceTimeExtra")
    public void create(@RequestBody SpaceTimeExtraRequest request){
        spaceTimeExtraService.create(request);
    }

    @GetMapping("/spaceTimeExtra/list")
    public ResponseEntity<List<SpaceTimeExtraResponse>> findAll(){
        return ResponseEntity.ok(spaceTimeExtraService.findAll());
    }

    @PatchMapping("/spaceTimeExtra/{id}")
    public ResponseEntity<SpaceTimeExtra> update(@PathVariable Long id, @RequestBody SpaceTimeExtraRequest request){
        return ResponseEntity.ok(spaceTimeExtraService.update(id, request));
    }
    @DeleteMapping("/spaceTimeExtra/{id}")
    public void delete(@PathVariable Long id){
        spaceTimeExtraService.delete(id);
    }
}
