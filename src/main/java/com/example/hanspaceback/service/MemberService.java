package com.example.hanspaceback.service;

import com.example.hanspaceback.domain.Department;
import com.example.hanspaceback.domain.DeptMember;
import com.example.hanspaceback.domain.Member;
import com.example.hanspaceback.domain.ReserveMember;
import com.example.hanspaceback.dto.request.MemberRequest;
import com.example.hanspaceback.dto.response.MemberResponse;
import com.example.hanspaceback.exception.DuplicateDeptMemberException;
import com.example.hanspaceback.exception.DuplicateMemberException;
import com.example.hanspaceback.jwt.LoginRequest;
import com.example.hanspaceback.repository.DepartmentRepository;
import com.example.hanspaceback.repository.DeptMemberRepository;
import com.example.hanspaceback.repository.MemberRepository;
import com.example.hanspaceback.repository.ReserveMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final DeptMemberRepository deptMemberRepository;
    private final DepartmentRepository departmentRepository;
    private final ReserveMemberRepository reserveMemberRepository;

    public void signup(MemberRequest request){
        Member member = memberRepository.findByEmail(request.getEmail());

        if (member == null) {
            member = Member.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .build();
            memberRepository.save(member);
        }else{
            throw new DuplicateMemberException("이미 member에 있는 사람입니당.");
        }
    }
    public void add(MemberRequest request){
        Member member = memberRepository.findByEmail(request.getEmail());
        Department department = departmentRepository.findById(request.getDeptId()).get();
        if(member == null){
            throw new RuntimeException("해당 멤버를 찾을 수 없습니다.");
        }
        if(department == null){
            throw new RuntimeException("해당 기관을 찾을 수 없습니다.");
        }
        DeptMember existingDeptMember = deptMemberRepository.findByMemberAndDepartment(member, department);
        if (existingDeptMember != null) {
            throw new DuplicateDeptMemberException("이미 해당 부서에 소속된 멤버입니다.");
        }

        DeptMember deptMember = DeptMember.builder()
                .department(department)
                .member(member)
                .approve("승인 대기")
                .permission("user")
                .build();
        deptMemberRepository.save(deptMember);

        ReserveMember reserveMember = ReserveMember.builder()
                .member(member)
                .build();
        reserveMemberRepository.save(reserveMember);
    }
    public Member getLoginMemerByEmail(String email) {
        Member member = memberRepository.findByEmail(email);
        return member;
    }

    public List<MemberResponse> findAll(){
        List<Member> members = memberRepository.findAll();
        List<MemberResponse> responses = new ArrayList<>();

        for (Member member : members) {
            MemberResponse response = new MemberResponse();
            response.setMemberId(member.getMemberId());
            response.setName(member.getName());
            response.setEmail(member.getEmail());
            response.setDeptId(member.getMemberId());

            responses.add(response);
        }
        return responses;
    }
    public Member update(Long id, MemberRequest request){
        Member member = memberRepository.findById(id).get();
        member.update(request);
        memberRepository.save(member);
        return member;
    }
    public void delete(Long id){
        memberRepository.deleteById(id);
    }

    public Member login(LoginRequest loginRequest) {
        Member member = memberRepository.findByEmail(loginRequest.getEmail());

        return member;
    }
}
