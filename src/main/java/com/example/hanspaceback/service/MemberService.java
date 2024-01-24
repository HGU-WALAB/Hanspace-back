package com.example.hanspaceback.service;

import com.example.hanspaceback.domain.*;
import com.example.hanspaceback.dto.request.MemberRequest;
import com.example.hanspaceback.dto.response.MemberResponse;
import com.example.hanspaceback.exception.DuplicateDeptMemberException;
import com.example.hanspaceback.exception.DuplicateMemberException;
import com.example.hanspaceback.jwt.HanSpaceLoginRequest;
import com.example.hanspaceback.repository.DepartmentRepository;
import com.example.hanspaceback.repository.DeptMemberRepository;
import com.example.hanspaceback.repository.MemberRepository;
import com.example.hanspaceback.repository.ReserveMemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
//    private final BCryptPasswordEncoder passwordEncoder;
    private final DeptMemberRepository deptMemberRepository;
    private final DepartmentRepository departmentRepository;
    private final ReserveMemberRepository reserveMemberRepository;


    // HanSpace 회원 가입
    public void signup(MemberRequest request){
        Member member = memberRepository.findByEmail(request.getEmail());
//        String encodedPassword = passwordEncoder.encode(request.getPassword());

        if (member == null) {
            member = Member.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .sId(request.getSId())
                    .password(request.getPassword())
                    .hanRole(HanRole.사용자)
                    .build();
            memberRepository.save(member);
        }else{
            throw new DuplicateMemberException("이미 member에 있는 사람입니당.");
        }
    }
    // 기관에 멤버 추가
    public void add(MemberRequest request){
        Member member = memberRepository.findByEmail(request.getEmail());
        Department department = departmentRepository.findById(request.getDeptId()).get();
        String status = "미승인";
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
        if(department.isUserAccept() == false){
            status = "승인";
        }
        else if(department.isUserAccept() == true){
            status = "미승인";
        }
        DeptMember deptMember = DeptMember.builder()
                .department(department)
                .member(member)
                .approve(status)
                .deptRole(DeptRole.사용자)
                .build();
        deptMemberRepository.save(deptMember);

        ReserveMember reserveMember = ReserveMember.builder()
                .member(member)
                .build();
        reserveMemberRepository.save(reserveMember);
    }
    public Member findByEmail(String email) {
        Member member = memberRepository.findByEmail(email);
        return member;
    }
    public Member findById(Long memberId) {
        Member member = memberRepository.findById(memberId).get();
        return member;
    }
    public List<MemberResponse> findAll(){
        List<Member> members = memberRepository.findAll();
        List<MemberResponse> responses = new ArrayList<>();

        for (Member member : members) {
            MemberResponse response = new MemberResponse();
            response.setMemberId(member.getMemberId());
            response.setName(member.getName());
            response.setPassword(member.getPassword());
            response.setSId(member.getSId());
            response.setEmail(member.getEmail());
            response.setDeptId(member.getMemberId());

            responses.add(response);
        }
        return responses;
    }
    public Member update(Long memberId, MemberRequest request){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberId));
        member.update(request);
        memberRepository.save(member);
        return member;
    }

    //    public Member update(String email, MemberRequest request){
//        Member member = memberRepository.findByEmail(email);
//        member.update(request);
//        memberRepository.save(member);
//        return member;
//    }
    public void delete(Long id){
        memberRepository.deleteById(id);
    }

    public Member login(HanSpaceLoginRequest hanSpaceLoginRequest) {
        Member member = memberRepository.findByEmail(hanSpaceLoginRequest.getEmail());
        System.out.println("member.getPassword() = " + member.getPassword());
        System.out.println("hanSpaceLoginRequest.getPassword() = " + hanSpaceLoginRequest.getPassword());
        if(!member.getPassword().equals(hanSpaceLoginRequest.getPassword())){
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }
        else return member;
    }
}
