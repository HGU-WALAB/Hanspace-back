package com.example.hanspaceback.service;

import com.example.hanspaceback.domain.*;
import com.example.hanspaceback.dto.request.DepartmentRequest;
import com.example.hanspaceback.dto.request.DeptMemberRequest;
import com.example.hanspaceback.dto.response.DepartmentResponse;
import com.example.hanspaceback.dto.response.DeptMemberResponse;
import com.example.hanspaceback.dto.response.MemberResponse;
import com.example.hanspaceback.dto.response.SpaceResponse;
import com.example.hanspaceback.exception.DuplicateDeptMemberException;
import com.example.hanspaceback.exception.MemberNotFoundException;
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
                .deptRole(DeptRole.USER) // default : user
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
            response.setDeptRole(deptMember.getDeptRole());
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
            response.setDeptRole(deptMember.getDeptRole());
            responses.add(response);
        }
        return responses;
    }
    public List<DeptMemberResponse> findDeptMembersByApprovalStatus(Long deptId, String approvalStatus) {
        List<DeptMember> deptMembers = deptMemberRepository.findByDepartment_DeptId(deptId);
        List<DeptMemberResponse> responses = new ArrayList<>();

        for (DeptMember deptMember : deptMembers) {
            if (!approvalStatus.equals(deptMember.getApprove())) {
                continue;
            }
            DeptMemberResponse response = new DeptMemberResponse();
            response.setDeptMemberId(deptMember.getId());
            response.setDeptId(deptMember.getDepartment().getDeptId());
            response.setMemberId(deptMember.getMember().getMemberId());
            response.setApprove(deptMember.getApprove());
            response.setDeptRole(deptMember.getDeptRole());
            responses.add(response);
        }
        return responses;
    }
    public List<DepartmentResponse> findDeptMembersByMemberId(Long memberId) {
        List<DepartmentResponse> responses = new ArrayList<>();
        List<Department> departments = departmentRepository.findAll();
        for (Department department : departments) {
            List<DeptMember> deptMembers = deptMemberRepository.findByMember_MemberId(memberId);

            DepartmentResponse response = new DepartmentResponse();

            response.setDeptId(department.getDeptId());
            response.setSiteName(department.getSiteName());
            response.setDeptName(department.getDeptName());
            response.setLogo(department.getLogo());
            response.setColor(department.getColor());
            response.setUserAccept(department.isUserAccept());
            response.setMaxRserveCount(department.getMaxReserveCount());
            response.setLink(department.getLink());
            response.setExtraInfo(department.getExtraInfo());
            response.setSiteInfoTitle(department.getSiteInfoTitle());
            response.setSiteInfoDetail(department.getSiteInfoDetail());

            List<DeptMemberResponse> deptMemberResponses = new ArrayList<>();
            for (DeptMember deptMember : deptMembers) {
                if(deptMember.getDepartment().getDeptId() == department.getDeptId()) {
                    DeptMemberResponse deptMemberResponse = new DeptMemberResponse();
                    deptMemberResponse.setDeptMemberId(deptMember.getId());
                    deptMemberResponse.setDeptRole(deptMember.getDeptRole());
                    deptMemberResponse.setApprove(deptMember.getApprove());
                    deptMemberResponses.add(deptMemberResponse);
                }
            }

            response.setDeptMemberResponse(deptMemberResponses);
            responses.add(response);
        }
        return responses;
    }
    public List<DepartmentResponse> findAddDeptMembersByMemberId(Long memberId) {
        List<DeptMember> deptMembers = deptMemberRepository.findByMember_MemberId(memberId);
        List<DepartmentResponse> responses = new ArrayList<>();

        for (DeptMember deptMember : deptMembers) {
            if(deptMember.getDepartment().getDeptId() != null) {
                DepartmentResponse response = new DepartmentResponse();
                Department department =  deptMember.getDepartment();
                response.setDeptId(department.getDeptId());
                response.setSiteName(department.getSiteName());
                response.setDeptName(department.getDeptName());
                response.setLogo(department.getLogo());
                response.setColor(department.getColor());
                response.setUserAccept(department.isUserAccept());
                response.setMaxRserveCount(department.getMaxReserveCount());
                response.setLink(department.getLink());
                response.setExtraInfo(department.getExtraInfo());
                response.setSiteInfoTitle(department.getSiteInfoTitle());
                response.setSiteInfoDetail(department.getSiteInfoDetail());
                responses.add(response);
            }
        }
        return responses;
    }
    public List<DepartmentResponse> findNAddDeptMembersByMemberId(Long memberId) {
        List<DeptMember> deptMembers = deptMemberRepository.findByMember_MemberId(memberId);
        List<Department> allDepts = departmentRepository.findAll();

        List<Long> addedDeptIds = new ArrayList<>();
        for (DeptMember deptMember : deptMembers) {
            addedDeptIds.add(deptMember.getDepartment().getDeptId());
        }

        List<DepartmentResponse> notAddedDepts = new ArrayList<>();
        for (Department dept : allDepts) {
            if (!addedDeptIds.contains(dept.getDeptId())) {
                DepartmentResponse response = new DepartmentResponse();
                response.setDeptId(dept.getDeptId());
                response.setSiteName(dept.getSiteName());
                response.setDeptName(dept.getDeptName());
                response.setLogo(dept.getLogo());
                response.setColor(dept.getColor());
                response.setUserAccept(dept.isUserAccept());
                response.setMaxRserveCount(dept.getMaxReserveCount());
                response.setLink(dept.getLink());
                response.setExtraInfo(dept.getExtraInfo());
                response.setSiteInfoTitle(dept.getSiteInfoTitle());
                response.setSiteInfoDetail(dept.getSiteInfoDetail());
                notAddedDepts.add(response);
            }
        }

        return notAddedDepts;
    }
    public DeptMember findById(Long id){
        DeptMember deptMember = deptMemberRepository.findById(id).get();
        return deptMember;
    }

    public DeptMember update(Long memberId, DeptMemberRequest request){
        DeptMember deptMember = deptMemberRepository.findByMember_MemberIdAndDepartment_DeptId(memberId, request.getDeptId());

        if(deptMember == null){
            throw new MemberNotFoundException("Member with ID " + memberId + " not found in department " + request.getDeptId());
        }
        deptMember.update(request);
        deptMemberRepository.save(deptMember);

        return deptMember;
    }

    public void delete(Long memberId, DeptMemberRequest request){
        deptMemberRepository.deleteByMember_MemberIdAndDepartment_DeptId(memberId, request.getDeptId());
    }
}
