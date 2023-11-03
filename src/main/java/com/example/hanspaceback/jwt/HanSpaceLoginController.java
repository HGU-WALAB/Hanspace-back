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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hanSpace")
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
        String secretKey = "my-secret-key-123123";
        long expireTimeMs = 1000 * 60 * 60;     // Token 유효 시간 = 60분

        String jwtToken = HanSpaceTokenUtil.createToken(member.getEmail(), secretKey, expireTimeMs);

        return ResponseEntity.ok(new HanSpaceTokenResponse(jwtToken));
    }

    @GetMapping("/info")
    public ResponseEntity<HanSpaceMemberInfoResponse> memberInfo(String email) {
        Member member = memberService.findByEmail(email);
//        List<DepartmentResponse> department = deptMemberService.findDeptMembersByMemberId(member.getMemberId());
        List<DeptMemberResponse> deptMembers = deptMemberService.findDeptMembersByMemberId(member.getMemberId());

//        List<DeptRole> deptRoles = new ArrayList<>();

//        for (DepartmentResponse dept : department) {
//            List<DeptMemberResponse> deptMemberResponses = deptMemberService.findByDeptMemberFetchJoin(dept.getDeptId());
//
//            for(DeptMemberResponse deptMemberResponse : deptMemberResponses){
//                DeptRole deptRole = deptMemberResponse.getDeptRole();
//                if (deptRole != null) { // deptRole이 null이 아닐 경우에만 리스트에 추가합니다.
//                    deptRoles.add(deptRole);
//                }
//            }
//        }
        HanSpaceMemberInfoResponse response = new HanSpaceMemberInfoResponse(
                member.getEmail(),
                member.getName(),
                member.getHanRole(),
                deptMembers
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "관리자 페이지 접근 성공";
    }
}