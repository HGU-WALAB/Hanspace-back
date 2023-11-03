package com.example.hanspaceback.controller;

import com.example.hanspaceback.domain.Department;
import com.example.hanspaceback.dto.request.DepartmentRequest;
import com.example.hanspaceback.dto.response.DepartmentResponse;
import com.example.hanspaceback.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/hanSpace/dept")
public class DepartmentController {
    private final DepartmentService departmentService;
    @PostMapping
    public void create(@RequestBody DepartmentRequest request){
        departmentService.create(request);
    }
//    @GetMapping("/dept/list")
//    public ResponseEntity<List<Department>> findAll(){
//        return ResponseEntity.ok(departmentService.findAll());
//    }
    @GetMapping("/list")
    public ResponseEntity<List<DepartmentResponse>> findAll(){
        return ResponseEntity.ok(departmentService.findAll());
    }
    @GetMapping("/{deptName}")
    public Long findDeptIdByDeptName(@PathVariable String deptName){
//        return ResponseEntity.ok(departmentService.findDeptIdByDeptName());
        return departmentService.findDeptIdByDeptName(deptName);
    }
    @GetMapping("/deptId/{deptId}")
    public ResponseEntity<Map<String, String>> findByDeptId(@PathVariable Long deptId) {
        String extraInfo = departmentService.findByDeptId(deptId).getExtraInfo();
        Map<String, String> response = new HashMap<>();
        response.put("extraInfo", extraInfo);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Department> update(@PathVariable Long id, @RequestBody DepartmentRequest request){
        return ResponseEntity.ok(departmentService.update(id, request));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id){
        departmentService.delete(id);
        return ResponseEntity.ok(id);
    }
}
