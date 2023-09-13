package com.example.hanspaceback.controller;

import com.example.hanspaceback.dto.request.DepartmentRequest;
import com.example.hanspaceback.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;
    @PostMapping("/dept")
    public void create(@RequestBody DepartmentRequest request){
        departmentService.create(request);
    }
}
