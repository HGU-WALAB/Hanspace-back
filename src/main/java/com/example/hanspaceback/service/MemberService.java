package com.example.hanspaceback.service;

import com.example.hanspaceback.domain.Department;
import com.example.hanspaceback.domain.Member;
import com.example.hanspaceback.dto.request.MemberRequest;
import com.example.hanspaceback.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    public void create(MemberRequest request){
        Member member = Member.builder()
                .name(request.getName())
                .email(request.getEmail())
                .build();
        memberRepository.save(member);
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
