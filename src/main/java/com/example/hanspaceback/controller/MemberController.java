package com.example.hanspaceback.controller;

import com.example.hanspaceback.domain.Member;
import com.example.hanspaceback.dto.request.MemberRequest;
import com.example.hanspaceback.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @PostMapping("/member")
    public void create(@RequestBody MemberRequest request){
        memberService.create(request);
    }
    @GetMapping("/member/list")
    public ResponseEntity<List<Member>> findAll(){
        return ResponseEntity.ok(memberService.findAll());
    }
    @PatchMapping("/member/{id}")
    public ResponseEntity<Member> update(@PathVariable Long id, @RequestBody MemberRequest request){
        return ResponseEntity.ok(memberService.update(id, request));
    }
    @DeleteMapping("/member/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id){
        memberService.delete(id);
        return ResponseEntity.ok(id);
    }
}
