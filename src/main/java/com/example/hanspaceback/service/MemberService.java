package com.example.hanspaceback.service;

import com.example.hanspaceback.domain.Member;
import com.example.hanspaceback.dto.request.MemberRequest;
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
