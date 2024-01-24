package com.example.hanspaceback.controller;

import com.example.hanspaceback.domain.Department;
import com.example.hanspaceback.domain.DeptRole;
import com.example.hanspaceback.dto.request.DepartmentRequest;
import com.example.hanspaceback.dto.response.DepartmentResponse;
import com.example.hanspaceback.jwt.CustomUserDetails;
import com.example.hanspaceback.service.DepartmentService;
import com.example.hanspaceback.service.ReserveService;
import com.example.hanspaceback.service.SpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/hanSpace/dept")
public class DepartmentController {
    private final DepartmentService departmentService;
    private final ReserveService reserveService;
    private final SpaceService spaceService;
    @PostMapping
    public void create(@AuthenticationPrincipal CustomUserDetails currentUserDetails, @ModelAttribute DepartmentRequest request, @RequestParam(value="deptImage", required = false) MultipartFile deptImage, @RequestParam(value="logoImage", required = false) MultipartFile logoImage) throws IOException {
        Long memberId = currentUserDetails.getMemberId();
        departmentService.create(memberId, request, deptImage, logoImage);
    }
    @GetMapping("/list")
    public ResponseEntity<List<DepartmentResponse>> findAll(@RequestParam(value="deptImage", required = false) MultipartFile deptImage, @RequestParam(value="logoImage", required = false) MultipartFile logoImage) throws IOException {
        return ResponseEntity.ok(departmentService.findAll(deptImage, logoImage));
    }
    @GetMapping("/{deptName}")
    public Long findDeptIdByDeptName(@PathVariable String deptName){
//        return ResponseEntity.ok(departmentService.findDeptIdByDeptName());
        return departmentService.findDeptIdByDeptName(deptName);
    }
    @GetMapping("/deptId/{deptId}")
    public ResponseEntity<Map<String, String>> findByDeptId(@PathVariable Long deptId, @RequestParam(value="deptImage", required = false) MultipartFile deptImage, @RequestParam(value="logoImage", required = false) MultipartFile logoImage) throws IOException {
        String extraInfo = departmentService.findByDeptId(deptId, deptImage, logoImage).getExtraInfo();
        Map<String, String> response = new HashMap<>();
        response.put("extraInfo", extraInfo);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list/user")
    public ResponseEntity<List<DepartmentResponse>> findByUser(@AuthenticationPrincipal CustomUserDetails currentUserDetails, @RequestParam(value="deptImage", required = false) MultipartFile deptImage, @RequestParam(value="logoImage", required = false) MultipartFile logoImage) throws IOException {
        Long memberId = currentUserDetails.getMemberId();
        departmentService.findByDeptRole(memberId, DeptRole.사용자, deptImage, logoImage);
        return ResponseEntity.ok(departmentService.findByDeptRole(memberId, DeptRole.사용자, deptImage, logoImage));
    }

    @GetMapping("/list/admin")
    public ResponseEntity<List<DepartmentResponse>> findByADMIN(@AuthenticationPrincipal CustomUserDetails currentUserDetails, @RequestParam(value="deptImage", required = false) MultipartFile deptImage, @RequestParam(value="logoImage", required = false) MultipartFile logoImage) throws IOException {
        Long memberId = currentUserDetails.getMemberId();
        departmentService.findByDeptRole(memberId, DeptRole.관리자, deptImage, logoImage);
        return ResponseEntity.ok(departmentService.findByDeptRole(memberId, DeptRole.관리자, deptImage, logoImage));
    }

    @GetMapping("/list/admin/count/{deptId}")
    public List<String> findByADMINCount(@AuthenticationPrincipal CustomUserDetails currentUserDetails, @PathVariable Long deptId) throws IOException {
        Long memberId = currentUserDetails.getMemberId();
        Long spaceId = spaceService.findByDeptId(deptId).get(0).getSpaceId();
        List<String> count = null;
//        reserveService.countReserve(memberId, spaceId);
//        reserveService.countAllReserve(memberId, deptId);

        // 미승인 예약 수, 미승인 사용자 수,
//        departmentService.findByDeptRole(memberId, DeptRole.관리자, deptImage, logoImage);
        return count;
    }
//    @GetMapping("/{id}")
//    public List<String> count(@PathVariable Long id){
//        Long spaceId = spaceService.findByDeptId(id).get(0).getSpaceId();
//        reserveService.countReserve(spaceId);
//        return ;
//    }
    @PatchMapping("/{id}")
    public ResponseEntity<Department> update(@PathVariable Long id, @ModelAttribute DepartmentRequest request, @RequestParam(value="deptImage", required = false) MultipartFile deptImage, @RequestParam(value="logoImage", required = false) MultipartFile logoImage) throws IOException {
        return ResponseEntity.ok(departmentService.update(id, request, deptImage, logoImage));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id){
        departmentService.delete(id);
        return ResponseEntity.ok(id);
    }
}
