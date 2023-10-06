package com.example.hanspaceback.controller;

import com.example.hanspaceback.domain.Member;
import com.example.hanspaceback.domain.Space;
import com.example.hanspaceback.dto.request.MemberRequest;
import com.example.hanspaceback.dto.request.SpaceRequest;
import com.example.hanspaceback.service.SpaceService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class SpaceController {
    private final SpaceService spaceService;

    @PostMapping("/space")
    public void create(@RequestBody SpaceRequest request){
        spaceService.create(request);
    }
    @GetMapping("/space/list")
    public ResponseEntity<List<Space>> findSpaceFetchJoin(){
        return ResponseEntity.ok(spaceService.findSpaceFetchJoin());
    }
    @PatchMapping("/space/{id}")
    public ResponseEntity<Space> update(@PathVariable Long id, @RequestBody SpaceRequest request){
        return ResponseEntity.ok(spaceService.update(id, request));
    }
    @DeleteMapping("/space/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id){
        spaceService.delete(id);
        return ResponseEntity.ok(id);
    }
}
