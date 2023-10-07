package com.example.hanspaceback.service;

import com.example.hanspaceback.domain.Department;
import com.example.hanspaceback.domain.DeptMember;
import com.example.hanspaceback.domain.Member;
import com.example.hanspaceback.dto.request.MemberRequest;
import com.example.hanspaceback.exception.DuplicateDeptMemberException;
import com.example.hanspaceback.exception.DuplicateMemberException;
import com.example.hanspaceback.repository.DepartmentRepository;
import com.example.hanspaceback.repository.DeptMemberRepository;
import com.example.hanspaceback.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final DeptMemberRepository deptMemberRepository;
    private final DepartmentRepository departmentRepository;
    public void create(MemberRequest request){
//        Member member = Member.builder()
//                .name(request.getName())
//                .email(request.getEmail())
//                .build();
//        memberRepository.save(member);
        Member member = memberRepository.findByNameAndEmail(request.getName(), request.getEmail());

        if (member == null) {
            // 존재하지 않는 멤버인 경우에만 member를 생성
            member = Member.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .build();
            memberRepository.save(member);
        }else{
            throw new DuplicateMemberException("이미 member에 있는 사람입니당.");
        }

        Department department = departmentRepository.findById(request.getDeptId()).get();
        DeptMember deptMember = DeptMember.builder()
                .department(department)
                .member(member)
                .approve("승인 대기")
                .permission("user")
                .build();
        deptMemberRepository.save(deptMember);
    }
    public List<Member> findAll(){
        return memberRepository.findAll();
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
}
