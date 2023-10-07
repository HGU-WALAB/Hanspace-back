package com.example.hanspaceback.service;

import com.example.hanspaceback.domain.Department;
import com.example.hanspaceback.domain.DeptMember;
import com.example.hanspaceback.domain.Member;
import com.example.hanspaceback.domain.Space;
import com.example.hanspaceback.dto.request.DepartmentRequest;
import com.example.hanspaceback.dto.request.DeptMemberRequest;
import com.example.hanspaceback.dto.response.DepartmentResponse;
import com.example.hanspaceback.dto.response.DeptMemberResponse;
import com.example.hanspaceback.dto.response.SpaceResponse;
import com.example.hanspaceback.exception.DuplicateDeptMemberException;
import com.example.hanspaceback.repository.DepartmentRepository;
import com.example.hanspaceback.repository.DeptMemberRepository;
import com.example.hanspaceback.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        int count = deptMemberRepository.countByDeptIdAndMemberId(request.getDeptId(), request.getMemberId());
        if (count > 0) {
            throw new DuplicateDeptMemberException("이미 존재하는 DeptMember입니다.");
        }
        Department department = departmentRepository.findById(request.getDeptId()).orElseThrow();
        Member member = memberRepository.findById(request.getMemberId()).orElseThrow();

        DeptMember deptMember = DeptMember.builder()
                .department(department)
                .member(member)
//                .approve(request.getApprove()) // deptMemberCreate 때 굳이 request에서 입력받을 필요 없다고 생각함
                .approve("승인 대기") // default : 승인대기
//                .permission(request.getPermission()) // deptMemberCreate 때 굳이 request에서 입력받을 필요 없다고 생각함
                .permission("user") // default : user
                .build();
        deptMemberRepository.save(deptMember);
    }

//    public List<DeptMember> findDeptMemberFetchJoin() {
//        return deptMemberRepository.findDeptMemberFetchJoin();
//    }
    public List<DeptMemberResponse> findDeptMemberFetchJoin() {
        List<DeptMember> deptMembers = deptMemberRepository.findAll();
        List<DeptMemberResponse> responses = new ArrayList<>();

        for (DeptMember deptMember : deptMembers) {
            DeptMemberResponse response = new DeptMemberResponse();
            response.setDeptMemberId(deptMember.getId());
            response.setDeptId(deptMember.getDepartment().getDeptId());
            response.setMemberId(deptMember.getMember().getMemberId());
            response.setApprove(deptMember.getApprove());
            response.setPermission(deptMember.getPermission());
            responses.add(response);
        }
        return responses;
    }
    public List<DeptMemberResponse> findByDeptMemberFetchJoin(Long deptId) {
        List<DeptMember> deptMembers = deptMemberRepository.findByDepartment_DeptId(deptId);
        List<DeptMemberResponse> responses = new ArrayList<>();

        for (DeptMember deptMember : deptMembers) {
            DeptMemberResponse response = new DeptMemberResponse();
            response.setDeptMemberId(deptMember.getId());
            response.setDeptId(deptMember.getDepartment().getDeptId());
            response.setMemberId(deptMember.getMember().getMemberId());
            response.setApprove(deptMember.getApprove());
            response.setPermission(deptMember.getPermission());
            responses.add(response);
        }
        return responses;
    }
    public DeptMember findById(Long id){
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
