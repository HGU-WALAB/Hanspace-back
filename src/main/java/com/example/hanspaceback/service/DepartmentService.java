package com.example.hanspaceback.service;

import com.example.hanspaceback.domain.Department;
import com.example.hanspaceback.domain.DeptMember;
import com.example.hanspaceback.domain.Member;
import com.example.hanspaceback.dto.request.DepartmentRequest;
import com.example.hanspaceback.dto.response.DepartmentResponse;
import com.example.hanspaceback.repository.DepartmentRepository;
import com.example.hanspaceback.repository.DeptMemberRepository;
import com.example.hanspaceback.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

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
    public void create(DepartmentRequest request){
        Department department = Department.builder()
                .siteName(request.getSiteName())
                .deptName(request.getDeptName())
                .logo(request.getLogo())
                .color(request.getColor())
                .userAccept(request.isUserAccept())
                .maxRserveCount(request.getMaxRserveCount())
                .link(request.getLink())
                .extraInfo(request.getExtraInfo())
                .siteInfoTitle(request.getSiteInfoTitle())
                .siteInfoDetail(request.getSiteInfoDetail())
                .build();
        departmentRepository.save(department);

        Member member = memberRepository.findById(request.getMemberId()).get();

        DeptMember deptMember = DeptMember.builder()
                .department(department)
                .member(member)
                .approve("승인 허가")
                .permission("admin")
                .build();
        deptMemberRepository.save(deptMember);
    }

//    public List<Department> findAll(){
//        return departmentRepository.findAll();
//    }
public List<DepartmentResponse> findAll() {
    List<Department> departments = departmentRepository.findAll();
    List<DepartmentResponse> responses = new ArrayList<>();

    for (Department department : departments) {
        DepartmentResponse response = new DepartmentResponse();
        response.setDeptId(department.getDeptId());
        response.setSiteName(department.getSiteName());
        response.setDeptName(department.getDeptName());
        response.setLogo(department.getLogo());
        response.setColor(department.getColor());
        response.setUserAccept(department.isUserAccept());
        response.setMaxRserveCount(department.getMaxRserveCount());
        response.setLink(department.getLink());
        response.setExtraInfo(department.getExtraInfo());
        response.setSiteInfoTitle(department.getSiteInfoTitle());
        response.setSiteInfoDetail(department.getSiteInfoDetail());

        responses.add(response);
    }
    return responses;
}


    public Department update(Long id, DepartmentRequest request){
        Department department = departmentRepository.findById(id).get();
        department.update(request);
        departmentRepository.save(department);
        return department;
    }

    public void delete(Long id){
        departmentRepository.deleteById(id);
    }

}
