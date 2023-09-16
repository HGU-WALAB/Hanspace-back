package com.example.hanspaceback.controller;

import com.example.hanspaceback.domain.Department;
import com.example.hanspaceback.dto.request.DepartmentRequest;
import com.example.hanspaceback.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;
    @PostMapping("/dept")
    public void create(@RequestBody DepartmentRequest request){
        departmentService.create(request);
    }
    @GetMapping("/dept/list")
    public ResponseEntity<List<Department>> findAll(){
        return ResponseEntity.ok(departmentService.findAll());
    }
    @PatchMapping("/dept/{id}")
    public ResponseEntity<Department> update(@PathVariable Long id, @RequestBody DepartmentRequest request){
        return ResponseEntity.ok(departmentService.update(id, request));
    }
    @DeleteMapping("/dept/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id){
        departmentService.delete(id);
        return ResponseEntity.ok(id);
    }
}
