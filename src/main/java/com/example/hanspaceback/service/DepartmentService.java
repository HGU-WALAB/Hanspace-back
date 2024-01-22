package com.example.hanspaceback.service;

import com.example.hanspaceback.domain.Department;
import com.example.hanspaceback.domain.DeptMember;
import com.example.hanspaceback.domain.DeptRole;
import com.example.hanspaceback.domain.Member;
import com.example.hanspaceback.dto.request.DepartmentRequest;
import com.example.hanspaceback.dto.response.DepartmentResponse;
import com.example.hanspaceback.repository.DepartmentRepository;
import com.example.hanspaceback.repository.DeptMemberRepository;
import com.example.hanspaceback.repository.MemberRepository;
import com.example.hanspaceback.s3.S3Uploader;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentService {
    private final MemberRepository memberRepository;
    private final DepartmentRepository departmentRepository;
    private final DeptMemberRepository deptMemberRepository;
    private final S3Uploader s3Uploader;

    public void create(Long memberId, DepartmentRequest request, MultipartFile deptImage, MultipartFile logoImage) throws IOException {
        String deptImg = null;
        String logoImg = null;
        if(deptImage != null && !deptImage.isEmpty()) {
            deptImg = s3Uploader.upload(deptImage,"deptImage");
        }
        if(logoImage != null && !logoImage.isEmpty()) {
            logoImg = s3Uploader.upload(logoImage,"logoImage");
        }
        Department department = Department.builder()
                .siteName(request.getSiteName())
                .deptName(request.getDeptName())
                .logoImage(logoImg)
                .userAccept(request.isUserAccept())
                .maxReserveCount(request.getMaxRserveCount())
                .link(request.getLink())
                .extraInfo(request.getExtraInfo())
                .deptImage(deptImg)
                .build();
        departmentRepository.save(department);

        Member member = memberRepository.findById(memberId).get();

        DeptMember deptMember = DeptMember.builder()
                .department(department)
                .member(member)
                .approve("승인")
                .deptRole(DeptRole.관리자)
                .build();
        deptMemberRepository.save(deptMember);
    }

    public List<DepartmentResponse> findAll(MultipartFile deptImage, MultipartFile logoImage) throws IOException {
        String deptImg = null;
        String logoImg = null;
        if(deptImage != null && !deptImage.isEmpty()) {
            deptImg = s3Uploader.upload(deptImage,"deptImage");
        }
        if(logoImage != null && !logoImage.isEmpty()) {
            logoImg = s3Uploader.upload(logoImage,"logoImage");
        }
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentResponse> responses = new ArrayList<>();

        for (Department department : departments) {
            DepartmentResponse response = new DepartmentResponse();
            response.setDeptId(department.getDeptId());
            response.setSiteName(department.getSiteName());
            response.setDeptName(department.getDeptName());
            response.setLogoImage(logoImg);
            response.setUserAccept(department.isUserAccept());
            response.setMaxRserveCount(department.getMaxReserveCount());
            response.setLink(department.getLink());
            response.setExtraInfo(department.getExtraInfo());
            response.setDeptImage(deptImg);

            responses.add(response);
        }
        return responses;
    }

    public DepartmentResponse findByDeptId(Long id, MultipartFile deptImage, MultipartFile logoImage) throws IOException {
        String deptImg = null;
        String logoImg = null;
        if(deptImage != null && !deptImage.isEmpty()) {
            deptImg = s3Uploader.upload(deptImage,"deptImage");
        }
        if(logoImage != null && !logoImage.isEmpty()) {
            logoImg = s3Uploader.upload(logoImage,"logoImage");
        }
        Department department = departmentRepository.findById(id).get();
        DepartmentResponse response = new DepartmentResponse();

        response.setDeptId(department.getDeptId());
        response.setSiteName(department.getSiteName());
        response.setDeptName(department.getDeptName());
        response.setLogoImage(logoImg);
        response.setUserAccept(department.isUserAccept());
        response.setMaxRserveCount(department.getMaxReserveCount());
        response.setLink(department.getLink());
        response.setExtraInfo(department.getExtraInfo());
        response.setDeptImage(deptImg);

        return response;
    }

    public Department update(Long id, DepartmentRequest request, MultipartFile deptImage, MultipartFile logoImage) throws IOException {
        Department department = departmentRepository.findById(id).get();
        String logoImg = null;
        String deptImg = null;

        if (logoImage != null && !logoImage.isEmpty()) {
            logoImg = s3Uploader.upload(logoImage, "logoImage");
        }
        if (deptImage != null && !deptImage.isEmpty()) {
            deptImg = s3Uploader.upload(deptImage, "deptImage");
        }
        department.update(request, deptImg, logoImg);
        departmentRepository.save(department);
        return department;
    }

    public void delete(Long id){
        departmentRepository.deleteById(id);
    }

    public Long findDeptIdByDeptName(String deptName) {
        Department department = departmentRepository.findByDeptName(deptName).orElseThrow();
        Long deptId = department.getDeptId();
        return deptId;
    }

    public List<DepartmentResponse> findByDeptRole(Long memberId, DeptRole deptRole, MultipartFile deptImage, MultipartFile logoImage) throws IOException {
        List<DeptMember> deptMembers = deptMemberRepository.findByMember_MemberIdAndDeptRole(memberId, deptRole);
        List<DepartmentResponse> responses = new ArrayList<>();
        String deptImg = null;
        String logoImg = null;
        if(deptImage != null && !deptImage.isEmpty()) {
            deptImg = s3Uploader.upload(deptImage,"deptImage");
        }
        if(logoImage != null && !logoImage.isEmpty()) {
            logoImg = s3Uploader.upload(logoImage,"logoImage");
        }

        for (DeptMember deptMember : deptMembers) {
            DepartmentResponse response = new DepartmentResponse();
            response.setDeptId(deptMember.getDepartment().getDeptId());
            response.setSiteName(deptMember.getDepartment().getSiteName());
            response.setDeptName(deptMember.getDepartment().getDeptName());
            response.setLogoImage(logoImg);
            response.setUserAccept(deptMember.getDepartment().isUserAccept());
            response.setLink(deptMember.getDepartment().getLink());
            response.setExtraInfo(deptMember.getDepartment().getExtraInfo());
            response.setDeptImage(deptImg);

            responses.add(response);
        }
        return responses;
    }

}
