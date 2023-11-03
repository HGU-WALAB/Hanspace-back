package com.example.hanspaceback.controller;

import com.example.hanspaceback.domain.Member;
import com.example.hanspaceback.domain.Space;
import com.example.hanspaceback.dto.request.MemberRequest;
import com.example.hanspaceback.dto.request.SpaceRequest;
import com.example.hanspaceback.dto.response.SpaceResponse;
import com.example.hanspaceback.service.SpaceService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/hanSpace/space")
public class SpaceController {
    private final SpaceService spaceService;

    @PostMapping
    public void create(@RequestBody SpaceRequest request){
        spaceService.create(request);
    }
    @GetMapping("/list")
    public ResponseEntity<List<Space>> findSpaceFetchJoin(){
        return ResponseEntity.ok(spaceService.findSpaceFetchJoin());
    }
    @GetMapping("/{deptId}")
    public ResponseEntity<List<SpaceResponse>> findByDeptId(@PathVariable Long deptId){
        return ResponseEntity.ok(spaceService.findByDeptId(deptId));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Space> update(@PathVariable Long id, @RequestBody SpaceRequest request){
        return ResponseEntity.ok(spaceService.update(id, request));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id){
        spaceService.delete(id);
        return ResponseEntity.ok(id);
    }
}
