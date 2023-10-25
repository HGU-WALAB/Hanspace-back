package com.example.hanspaceback.controller;

import com.example.hanspaceback.domain.Department;
import com.example.hanspaceback.domain.DeptMember;
import com.example.hanspaceback.dto.request.DeptMemberRequest;
import com.example.hanspaceback.dto.response.DeptMemberResponse;
import com.example.hanspaceback.service.DeptMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class DeptMemberController {
    private final DeptMemberService deptMemberService;
    @PostMapping("/deptMember")
    public void create(@RequestBody DeptMemberRequest request){
        deptMemberService.create(request);
    }
//    @GetMapping("/deptMember/list")
//    public ResponseEntity<List<DeptMember>> findDeptMemberFetchJoin(){
//        return ResponseEntity.ok(deptMemberService.findDeptMemberFetchJoin());
//    }
    @GetMapping("/deptMember/list")
    public ResponseEntity<List<DeptMemberResponse>> findDeptMemberFetchJoin(){
        return ResponseEntity.ok(deptMemberService.findDeptMemberFetchJoin());
    }

    @GetMapping("/deptMember/list/{deptId}")
    public ResponseEntity<List<DeptMemberResponse>> findByDeptMemberFetchJoin(@PathVariable Long deptId){
        return ResponseEntity.ok(deptMemberService.findByDeptMemberFetchJoin(deptId));
    }
    @GetMapping("/deptMember/list/approve/{deptId}")
    public ResponseEntity<List<DeptMemberResponse>> findDeptMembersApprove(@PathVariable Long deptId){
        return ResponseEntity.ok(deptMemberService.findDeptMembersByApprovalStatus(deptId, "승인"));
    }
    @GetMapping("/deptMember/list/approveNan/{deptId}")
    public ResponseEntity<List<DeptMemberResponse>> findDeptMembersNApprove(@PathVariable Long deptId){
        return ResponseEntity.ok(deptMemberService.findDeptMembersByApprovalStatus(deptId, "승인 대기"));
    }
    @GetMapping("/deptMember/{id}")
    public ResponseEntity<DeptMember> findbyId(@PathVariable Long id){
        return ResponseEntity.ok(deptMemberService.findById(id));
    }
    @PatchMapping("/deptMember/{id}")
    public ResponseEntity<DeptMember> update(@PathVariable Long id, @RequestBody DeptMemberRequest request){
        return ResponseEntity.ok(deptMemberService.update(id, request));
    }
    @DeleteMapping("/deptMember/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id){
        deptMemberService.delete(id);
        return ResponseEntity.ok(id);
    }
}
