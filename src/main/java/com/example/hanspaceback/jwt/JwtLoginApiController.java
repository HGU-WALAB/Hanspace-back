package com.example.hanspaceback.jwt;

import com.example.hanspaceback.domain.Member;
import com.example.hanspaceback.dto.request.MemberRequest;
import com.example.hanspaceback.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jwt-login")
public class JwtLoginApiController {

    private final MemberService memberService;

    @PostMapping("/join")
    public String join(@RequestBody MemberRequest memberRequest) {

//        // loginId 중복 체크
//        if(memberService.checkLoginIdDuplicate(joinRequest.getLoginId())) {
//            return "로그인 아이디가 중복됩니다.";
//        }
//        // 닉네임 중복 체크
//        if(memberService.checkNicknameDuplicate(joinRequest.getNickname())) {
//            return "닉네임이 중복됩니다.";
//        }
//        // password와 passwordCheck가 같은지 체크
//        if(!joinRequest.getPassword().equals(joinRequest.getPasswordCheck())) {
//            return"바밀번호가 일치하지 않습니다.";
//        }

        memberService.signup(memberRequest);
        return "회원가입 성공";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {

        Member member = memberService.login(loginRequest);

        // 로그인 아이디나 비밀번호가 틀린 경우 global error return
        if(member == null) {
            return"로그인 아이디 또는 비밀번호가 틀렸습니다.";
        }

        // 로그인 성공 => Jwt Token 발급

        String secretKey = "d2FsYWItaGFuc3BhY2UteXVqaW4taHllcmltLWhhbm5hLXdhbGFiLXNwcmluZy1ib290LXNlY3VyaXR5LWp3dC1oYW5zcGFjZSEK";
        long expireTimeMs = 1000 * 60 * 60;     // Token 유효 시간 = 60분

        String jwtToken = JwtTokenUtil.createToken(member.getEmail(), secretKey, expireTimeMs);

        return jwtToken;
    }

    @GetMapping("/info")
    public String userInfo(String email) {
        Member member = memberService.getLoginMemerByEmail(email);

        return String.format("loginId : %s\nnickname : %s",
                member.getEmail(), member.getName());
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "관리자 페이지 접근 성공";
    }
}