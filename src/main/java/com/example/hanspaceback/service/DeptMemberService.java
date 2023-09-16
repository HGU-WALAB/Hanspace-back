package com.example.hanspaceback.service;

import com.example.hanspaceback.domain.Department;
import com.example.hanspaceback.domain.DeptMember;
import com.example.hanspaceback.domain.Member;
import com.example.hanspaceback.dto.request.DepartmentRequest;
import com.example.hanspaceback.dto.request.DeptMemberRequest;
import com.example.hanspaceback.repository.DepartmentRepository;
import com.example.hanspaceback.repository.DeptMemberRepository;
import com.example.hanspaceback.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeptMemberService {
    private final DepartmentRepository departmentRepository;
    private final DeptMemberRepository deptMemberRepository;
    private final MemberRepository memberRepository;

    public void create(DeptMemberRequest request){
        Department department = departmentRepository.findById(request.getDeptId()).orElseThrow();
        Member member = memberRepository.findById(request.getMemberId()).orElseThrow();

        DeptMember deptMember = DeptMember.builder()
                .department(department)
                .member(member)
                .approve(request.getApprove())
                .permission(request.getPermission())
                .build();
        deptMemberRepository.save(deptMember);
    }

    public List<DeptMember> findDeptMemberFetchJoin() {
        return deptMemberRepository.findDeptMemberFetchJoin();
    }
    public DeptMember findbyId(Long id){
        DeptMember deptMember = deptMemberRepository.findById(id).get();
        return deptMember;
    }

    public DeptMember update(Long id, DeptMemberRequest request){
        DeptMember deptMember = deptMemberRepository.findById(id).orElseThrow();
        deptMember.update(request);
        deptMemberRepository.save(deptMember);
        return deptMember;
    }

    public void delete(Long id){
        deptMemberRepository.deleteById(id);
    }
}
