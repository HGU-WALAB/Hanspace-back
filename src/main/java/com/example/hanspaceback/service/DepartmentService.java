package com.example.hanspaceback.service;

import com.example.hanspaceback.domain.Department;
import com.example.hanspaceback.dto.request.DepartmentRequest;
import com.example.hanspaceback.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    public void create(DepartmentRequest request){
        Department department = Department.builder()
                .siteName(request.getSiteName())
                .deptName(request.getDeptName())
                .logo(request.getLogo())
                .color(request.getColor())
                .userAccept(request.isUserAccept())
                .maxRserveCount(request.getMaxRserveCount())
                .link(request.getLink())
                .firstInfo(request.getFirstInfo())
                .secondInfo(request.getSecondInfo())
                .siteInfoTitle(request.getSiteInfoTitle())
                .siteInfoDetail(request.getSiteInfoDetail())
                .build();
        departmentRepository.save(department);
    }
}
