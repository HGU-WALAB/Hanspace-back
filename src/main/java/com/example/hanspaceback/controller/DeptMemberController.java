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
    // 기관 멤버 create (add)
    @PostMapping
    public void create(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody DeptMemberRequest request){
        Long memberId = customUserDetails.getMemberId();
        deptMemberService.create(memberId, request);
    }
    // 전체 기관 멤버 리스트
    @GetMapping("/list")
    public ResponseEntity<List<DeptMemberResponse>> findDeptMemberFetchJoin(){
        return ResponseEntity.ok(deptMemberService.findDeptMemberFetchJoin());
    }

    // 기관 아이디로 홰당 기관의 유저 리스트
    @GetMapping("/list/{deptId}")
    public ResponseEntity<List<DeptMemberResponse>> findByDeptMemberFetchJoin(@PathVariable Long deptId){
        return ResponseEntity.ok(deptMemberService.findByDeptMemberFetchJoin(deptId));
    }

    // 기관 아이디로 홰당 기관에 승인된 유저 리스트
    @GetMapping("/list/approve/{deptId}")
    public ResponseEntity<List<DeptMemberResponse>> findDeptMembersApprove(@PathVariable Long deptId){
        return ResponseEntity.ok(deptMemberService.findDeptMembersByApprovalStatus(deptId, "승인"));
    }
    // 기관 아이디로 홰당 기관에 미승인된 유저 리스트 (승인 대기)
    @GetMapping("/list/approveNan/{deptId}")
    public ResponseEntity<List<DeptMemberResponse>> findDeptMembersNApprove(@PathVariable Long deptId){
        return ResponseEntity.ok(deptMemberService.findDeptMembersByApprovalStatus(deptId, "승인 대기"));
    }
    // 해당 멤버가 추가한 기관 리스트 (승인 + 승인 대기 + 거절)
    @GetMapping("/list/member/add")
    public ResponseEntity<List<DepartmentResponse>> findMemberIdAdd(@AuthenticationPrincipal CustomUserDetails currentUserDetails){
        Long memberId = currentUserDetails.getMemberId();
        return ResponseEntity.ok(deptMemberService.findAddDeptMembersByMemberId(memberId));
    }
    // 해당 멤버가 추가하지 않은 기관 리스트 (승인 + 승인 대기 + 거절)
    @GetMapping("/list/member/nadd")
    public ResponseEntity<List<DepartmentResponse>> findMemberIdNAdd(@AuthenticationPrincipal CustomUserDetails currentUserDetails){
        Long memberId = currentUserDetails.getMemberId();
        return ResponseEntity.ok(deptMemberService.findNAddDeptMembersByMemberId(memberId));
    }
    // 해당 멤버가 추가하고 승인된 기관 리스트 (승인)
    @GetMapping("/list/member/add/approve")
    public ResponseEntity<List<DepartmentResponse>> findMemberIdAddApprove(@AuthenticationPrincipal CustomUserDetails currentUserDetails){
        Long memberId = currentUserDetails.getMemberId();
        return ResponseEntity.ok(deptMemberService.findAddApproveDeptMembersByMemberId(memberId));
    }
    // 멤버 아이디로 찾기
    @GetMapping("/memberId")
    public ResponseEntity<DeptMember> findbyId(@AuthenticationPrincipal CustomUserDetails currentUserDetails){
        Long memberId = currentUserDetails.getMemberId();
        return ResponseEntity.ok(deptMemberService.findById(memberId));
    }
    // deptMember 업데이트
    @PatchMapping("/update")
    public ResponseEntity<DeptMember> update(@AuthenticationPrincipal CustomUserDetails currentUserDetails, @RequestBody DeptMemberRequest request){
        Long memberId = currentUserDetails.getMemberId();
        return ResponseEntity.ok(deptMemberService.update(memberId, request));
    }
    // deptMember 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<Long> delete(@AuthenticationPrincipal CustomUserDetails currentUserDetails, @RequestBody DeptMemberRequest request){
        Long memberId = currentUserDetails.getMemberId();
        deptMemberService.delete(memberId, request);
        return ResponseEntity.ok(memberId);
    }
}
