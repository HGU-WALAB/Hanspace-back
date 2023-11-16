package com.example.hanspaceback.controller;

import com.example.hanspaceback.domain.Department;
import com.example.hanspaceback.domain.DeptMember;
import com.example.hanspaceback.dto.request.DeptMemberRequest;
import com.example.hanspaceback.dto.response.DepartmentResponse;
import com.example.hanspaceback.dto.response.DeptMemberResponse;
import com.example.hanspaceback.dto.response.MemberResponse;
import com.example.hanspaceback.jwt.CustomUserDetails;
import com.example.hanspaceback.service.DeptMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/hanSpace/deptMember")
public class DeptMemberController {
    private final DeptMemberService deptMemberService;
    @PostMapping
    public void create(@RequestBody DeptMemberRequest request){
        deptMemberService.create(request);
    }
//    @GetMapping("/deptMember/list")
//    public ResponseEntity<List<DeptMember>> findDeptMemberFetchJoin(){
//        return ResponseEntity.ok(deptMemberService.findDeptMemberFetchJoin());
//    }
    @GetMapping("/list")
    public ResponseEntity<List<DeptMemberResponse>> findDeptMemberFetchJoin(){
        return ResponseEntity.ok(deptMemberService.findDeptMemberFetchJoin());
    }

    @GetMapping("/list/{deptId}")
    public ResponseEntity<List<DeptMemberResponse>> findByDeptMemberFetchJoin(@PathVariable Long deptId){
        return ResponseEntity.ok(deptMemberService.findByDeptMemberFetchJoin(deptId));
    }
    @GetMapping("/list/approve/{deptId}")
    public ResponseEntity<List<DeptMemberResponse>> findDeptMembersApprove(@PathVariable Long deptId){
        return ResponseEntity.ok(deptMemberService.findDeptMembersByApprovalStatus(deptId, "승인"));
    }
    @GetMapping("/list/approveNan/{deptId}")
    public ResponseEntity<List<DeptMemberResponse>> findDeptMembersNApprove(@PathVariable Long deptId){
        return ResponseEntity.ok(deptMemberService.findDeptMembersByApprovalStatus(deptId, "승인 대기"));
    }
    @GetMapping("/list/member/add")
    public ResponseEntity<List<DepartmentResponse>> findMemberIdAdd(@AuthenticationPrincipal CustomUserDetails currentUserDetails){
        Long memberId = currentUserDetails.getMemberId();
        return ResponseEntity.ok(deptMemberService.findAddDeptMembersByMemberId(memberId));
    }
    @GetMapping("/list/member/nadd")
    public ResponseEntity<List<DepartmentResponse>> findMemberIdNAdd(@AuthenticationPrincipal CustomUserDetails currentUserDetails){
        Long memberId = currentUserDetails.getMemberId();
        return ResponseEntity.ok(deptMemberService.findNAddDeptMembersByMemberId(memberId));
    }
    @GetMapping("/memberId")
    public ResponseEntity<DeptMember> findbyId(@AuthenticationPrincipal CustomUserDetails currentUserDetails){
        Long memberId = currentUserDetails.getMemberId();
        return ResponseEntity.ok(deptMemberService.findById(memberId));
    }
    @PatchMapping("/update")
    public ResponseEntity<DeptMember> update(@AuthenticationPrincipal CustomUserDetails currentUserDetails, @RequestBody DeptMemberRequest request){
        Long memberId = currentUserDetails.getMemberId();
        return ResponseEntity.ok(deptMemberService.update(memberId, request));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Long> delete(@AuthenticationPrincipal CustomUserDetails currentUserDetails, @RequestBody DeptMemberRequest request){
        Long memberId = currentUserDetails.getMemberId();
        deptMemberService.delete(memberId, request);
        return ResponseEntity.ok(memberId);
    }
}
