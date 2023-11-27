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
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.region.static}")
    private String region;

    public void create(Long memberId, DeptMemberRequest request){
        int count = deptMemberRepository.countByDeptIdAndMemberId(request.getDeptId(), request.getMemberId());
        if (count > 0) {
            throw new DuplicateDeptMemberException("이미 존재하는 DeptMember입니다.");
        }
        Department department = departmentRepository.findById(request.getDeptId()).orElseThrow();
        Member member = memberRepository.findById(memberId).orElseThrow();

        DeptMember deptMember = DeptMember.builder()
                .department(department)
                .member(member)
                .approve("승인 대기") // default : 승인대기
                .deptRole(DeptRole.USER) // default : user
                .build();
        deptMemberRepository.save(deptMember);
    }

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
        String url = "https://" + bucket + ".s3." + region + ".amazonaws.com/deptImage/";

        for (Department department : departments) {
            List<DeptMember> deptMembers = deptMemberRepository.findByMember_MemberId(memberId);

            DepartmentResponse response = new DepartmentResponse();

            response.setDeptId(department.getDeptId());
            response.setSiteName(department.getSiteName());
            response.setDeptName(department.getDeptName());
            response.setLogoImage(department.getLogoImage());
            response.setUserAccept(department.isUserAccept());
            response.setMaxRserveCount(department.getMaxReserveCount());
            response.setLink(department.getLink());
            response.setExtraInfo(department.getExtraInfo());
            if(department.getDeptImage() != null)
                response.setDeptImage(url + department.getDeptImage());

            response.setMemberCount(deptMembers.size());
            response.setSpaceCount(department.getSpace().size());

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
        String url = "https://" + bucket + ".s3." + region + ".amazonaws.com/deptImage/";

        for (DeptMember deptMember : deptMembers) {
            if(deptMember.getDepartment().getDeptId() != null) {
                DepartmentResponse response = new DepartmentResponse();
                Department department =  deptMember.getDepartment();
                response.setDeptId(department.getDeptId());
                response.setSiteName(department.getSiteName());
                response.setDeptName(department.getDeptName());
                response.setLogoImage(department.getLogoImage());
                response.setUserAccept(department.isUserAccept());
                response.setMaxRserveCount(department.getMaxReserveCount());
                response.setLink(department.getLink());
                response.setExtraInfo(department.getExtraInfo());
                if(department.getDeptImage() != null)
                    response.setDeptImage(url + department.getDeptImage());
                response.setMemberCount(deptMembers.size());
                response.setSpaceCount(department.getSpace().size());
                responses.add(response);
            }
        }
        return responses;
    }
    public List<DepartmentResponse> findNAddDeptMembersByMemberId(Long memberId) {
        List<DeptMember> deptMembers = deptMemberRepository.findByMember_MemberId(memberId);
        List<Department> allDepts = departmentRepository.findAll();
        String url = "https://" + bucket + ".s3." + region + ".amazonaws.com/deptImage/";

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
                response.setLogoImage(dept.getLogoImage());
                response.setUserAccept(dept.isUserAccept());
                response.setMaxRserveCount(dept.getMaxReserveCount());
                response.setLink(dept.getLink());
                response.setExtraInfo(dept.getExtraInfo());
                if(dept.getDeptImage() != null)
                    response.setDeptImage(url + dept.getDeptImage());
                response.setMemberCount(deptMembers.size());
                response.setSpaceCount(dept.getSpace().size());
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
