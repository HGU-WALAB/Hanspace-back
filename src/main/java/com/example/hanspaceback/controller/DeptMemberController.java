package com.example.hanspaceback.controller;

import com.example.hanspaceback.domain.Department;
import com.example.hanspaceback.domain.DeptMember;
import com.example.hanspaceback.dto.request.DeptMemberRequest;
import com.example.hanspaceback.dto.response.DepartmentResponse;
import com.example.hanspaceback.dto.response.DeptMemberResponse;
import com.example.hanspaceback.dto.response.MemberResponse;
import com.example.hanspaceback.service.DeptMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("/list/member/add/{memberId}")
    public ResponseEntity<List<DepartmentResponse>> findMemberIdAdd(@PathVariable Long memberId){
        return ResponseEntity.ok(deptMemberService.findAddDeptMembersByMemberId(memberId));
    }
    @GetMapping("/list/member/nadd/{memberId}")
    public ResponseEntity<List<DepartmentResponse>> findMemberIdNAdd(@PathVariable Long memberId){
        return ResponseEntity.ok(deptMemberService.findNAddDeptMembersByMemberId(memberId));
    }
    @GetMapping("/{id}")
    public ResponseEntity<DeptMember> findbyId(@PathVariable Long id){
        return ResponseEntity.ok(deptMemberService.findById(id));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<DeptMember> update(@PathVariable Long id, @RequestBody DeptMemberRequest request){
        return ResponseEntity.ok(deptMemberService.update(id, request));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id){
        deptMemberService.delete(id);
        return ResponseEntity.ok(id);
    }
}
