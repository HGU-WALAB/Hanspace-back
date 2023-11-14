package com.example.hanspaceback.controller;

import com.example.hanspaceback.domain.Member;
import com.example.hanspaceback.dto.request.MemberRequest;
import com.example.hanspaceback.dto.response.MemberResponse;
import com.example.hanspaceback.jwt.CustomUserDetails;
import com.example.hanspaceback.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/hanSpace/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/add")
    public void add(@RequestBody MemberRequest request){
        memberService.add(request);
    }
    @PostMapping("/signup")
    public void signup(@RequestBody MemberRequest request){
        memberService.signup(request);
    }
//    @GetMapping("/member/list")
//    public ResponseEntity<List<Member>> findAll(){
//        return ResponseEntity.ok(memberService.findAll());
//    }

    @GetMapping("/list")
    public ResponseEntity<List<MemberResponse>> findAll(){
        return ResponseEntity.ok(memberService.findAll());
    }
//    @PatchMapping("/{id}")
//    public ResponseEntity<Member> update(@PathVariable Long id, @RequestBody MemberRequest request){
//        return ResponseEntity.ok(memberService.update(id, request));
//    }
    @PatchMapping("/update")
    public ResponseEntity<Member> update(@AuthenticationPrincipal CustomUserDetails currentUserDetails, @RequestBody MemberRequest request){
        Long memberId = currentUserDetails.getMemberId();
        return ResponseEntity.ok(memberService.update(memberId, request));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Long> delete(@AuthenticationPrincipal CustomUserDetails currentUserDetails){
        Long memberId = currentUserDetails.getMemberId();
        memberService.delete(memberId);
        return ResponseEntity.ok(memberId);
    }
}
