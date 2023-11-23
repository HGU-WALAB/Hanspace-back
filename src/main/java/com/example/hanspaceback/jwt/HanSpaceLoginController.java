package com.example.hanspaceback.jwt;

import com.example.hanspaceback.domain.Department;
import com.example.hanspaceback.domain.DeptMember;
import com.example.hanspaceback.domain.DeptRole;
import com.example.hanspaceback.domain.Member;
import com.example.hanspaceback.dto.request.MemberRequest;
import com.example.hanspaceback.dto.response.DepartmentResponse;
import com.example.hanspaceback.dto.response.DeptMemberResponse;
import com.example.hanspaceback.service.DeptMemberService;
import com.example.hanspaceback.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hanSpace")
@CrossOrigin("*")
public class HanSpaceLoginController {

    private final MemberService memberService;
    private final DeptMemberService deptMemberService;

    @Value("${jwt.secret}")
    private String secretKey;
    @PostMapping("/join")
    public String join(@RequestBody MemberRequest memberRequest) {
        memberService.signup(memberRequest);
        return "회원가입 성공";
    }

    @PostMapping("/login")
    public ResponseEntity<HanSpaceTokenResponse> login(@RequestBody HanSpaceLoginRequest hanSpaceLoginRequest) {

        Member member = memberService.login(hanSpaceLoginRequest);

        // 로그인 아이디나 비밀번호가 틀린 경우 global error return
        if(member == null) {
            return ResponseEntity.badRequest().body(new HanSpaceTokenResponse("로그인 아이디 또는 비밀번호가 틀렸습니다."));
        }

        long expireTimeMs = 1000 * 60 * 60;     // Token 유효 시간 = 60분
        List<DepartmentResponse> departmentResponses = deptMemberService.findDeptMembersByMemberId(member.getMemberId());

        String jwtToken = HanSpaceTokenUtil.createToken(member.getEmail(), member.getMemberId(), secretKey, expireTimeMs, departmentResponses);

        return ResponseEntity.ok(new HanSpaceTokenResponse(jwtToken));
    }

    @GetMapping("/info")
    public ResponseEntity<HanSpaceMemberInfoResponse> memberInfo(@AuthenticationPrincipal CustomUserDetails currentUser) {
        Long memberId = currentUser.getMemberId(); // CustomUserDetails에서 memberId를 가져옴
        Member member = memberService.findById(memberId);
        List<DepartmentResponse> department = deptMemberService.findDeptMembersByMemberId(member.getMemberId());

        HanSpaceMemberInfoResponse response = new HanSpaceMemberInfoResponse(
                member.getEmail(),
                member.getName(),
                member.getHanRole(),
                department
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "관리자 페이지 접근 성공";
    }
}