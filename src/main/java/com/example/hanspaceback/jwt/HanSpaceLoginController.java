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

        // 로그인 성공 => Jwt Token 발급
        String secretKey = "hanspace2023AAAHanNaLee_HyelimChio_YujinJang";
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
//@GetMapping("/info")
//public ResponseEntity<HanSpaceMemberInfoResponse> memberInfo(@AuthenticationPrincipal CustomUserDetails currentUser) {
//    Long memberId = currentUser.getMemberId(); // CustomUserDetails에서 memberId를 가져옴
//    Member member = memberService.findById(memberId); // memberId를 사용하여 사용자 정보를 조회합니다.
//    if (member == null) {
//        // 적절한 예외 처리를 수행합니다.
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//    }
//    // 토큰에서 deptId와 deptRole 정보를 추출합니다.
//    List<DeptMemberResponse> deptMemberResponses = deptMemberService.findByDeptMemberFetchJoin(memberId).stream()
//            .map(deptMember -> {
//                DeptMemberResponse response = new DeptMemberResponse();
//                response.setDeptId(deptMember.getDeptId());
//                response.setDeptRole(deptMember.getDeptRole());
//                return response;
//            }).collect(Collectors.toList());
//
//    // 응답 객체를 생성하여 반환합니다.
//    HanSpaceMemberInfoResponse response = new HanSpaceMemberInfoResponse(
//            member.getEmail(),
//            member.getName(),
//            member.getHanRole(),
//            null, // departmentResponses는 여기서 사용하지 않습니다.
//            deptMemberResponses
//    );
//
//    return ResponseEntity.ok(response);
//}


    @GetMapping("/admin")
    public String adminPage() {
        return "관리자 페이지 접근 성공";
    }
}