package com.example.hanspaceback.controller;

import com.example.hanspaceback.dto.request.ReserveMemberRequest;
import com.example.hanspaceback.dto.response.ReserveMemberResponse;
import com.example.hanspaceback.service.ReserveMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class ReserveMemberController {
    private final ReserveMemberService reserveMemberService;

//    @PostMapping("/reserveMember/create")
//    public void create(@RequestBody ReserveMemberRequest request){
//        reserveMemberService.create(request);
//    }

//    @GetMapping("/reserve/list/{memberId}")
//    public ResponseEntity<List<ReserveMemberResponse>> findAll(@PathVariable Long memberId){
//        return ResponseEntity.ok(reserveMemberService.findByMemberId(memberId));
//    }
}
