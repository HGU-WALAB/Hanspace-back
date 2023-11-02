package com.example.hanspaceback.jwt;

import com.example.hanspaceback.domain.Member;
import com.example.hanspaceback.dto.request.MemberRequest;
import com.example.hanspaceback.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hanSpace")
public class JwtLoginApiController {

    private final MemberService memberService;

    @PostMapping("/join")
    public String join(@RequestBody MemberRequest memberRequest) {
        memberService.signup(memberRequest);
        return "회원가입 성공";
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {

        Member member = memberService.login(loginRequest);

        // 로그인 아이디나 비밀번호가 틀린 경우 global error return
        if(member == null) {
            return ResponseEntity.badRequest().body(new JwtResponse("로그인 아이디 또는 비밀번호가 틀렸습니다."));
        }

        // 로그인 성공 => Jwt Token 발급
        String secretKey = "my-secret-key-123123";
        long expireTimeMs = 1000 * 60 * 60;     // Token 유효 시간 = 60분

        String jwtToken = JwtTokenUtil.createToken(member.getEmail(), secretKey, expireTimeMs);

        return ResponseEntity.ok(new JwtResponse(jwtToken));
    }

    @GetMapping("/info")
    public ResponseEntity<MemberInfoResponse> memberInfo(String email) {
        Member member = memberService.findByEmail(email);

        MemberInfoResponse response = new MemberInfoResponse(
                member.getEmail(),
                member.getName(),
                member.getHanRole()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "관리자 페이지 접근 성공";
    }
}