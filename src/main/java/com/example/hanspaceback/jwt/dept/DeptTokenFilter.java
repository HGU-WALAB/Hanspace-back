//package com.example.hanspaceback.jwt.dept;
//
//import com.example.hanspaceback.domain.DeptMember;
//import com.example.hanspaceback.domain.Member;
//import com.example.hanspaceback.dto.response.DeptMemberResponse;
//import com.example.hanspaceback.jwt.HanSpaceTokenUtil;
//import com.example.hanspaceback.service.DeptMemberService;
//import com.example.hanspaceback.service.MemberService;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpHeaders;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.List;
//
//// OncePerRequestFilter : 매번 들어갈 때 마다 체크 해주는 필터
//@RequiredArgsConstructor
//public class DeptTokenFilter extends OncePerRequestFilter {
//
//    private final DeptMemberService deptMemberService;
//    private final String secretKey;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//
//        // Header의 Authorization 값이 비어있으면 => Jwt Token을 전송하지 않음 => 로그인 하지 않음
//        if(authorizationHeader == null) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        // Header의 Authorization 값이 'Bearer '로 시작하지 않으면 => 잘못된 토큰
//        if(!authorizationHeader.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        // 전송받은 값에서 'Bearer ' 뒷부분(Jwt Token) 추출
//        String token = authorizationHeader.split(" ")[1];
//
//        // 전송받은 Jwt Token이 만료되었으면 => 다음 필터 진행(인증 X)
//        if(HanSpaceTokenUtil.isExpired(token, secretKey)) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        // Jwt Token에서 memberId 추출
////        String role = DeptTokenUtil.getRole(token, secretKey);
//        Long deptId = Long.valueOf(DeptTokenUtil.getDeptId(token, secretKey));
//
//        // 추출한 email User 찾아오기
////        Member loginMember = memberService.findByEmail(email);
//        List<DeptMemberResponse> deptMember = deptMemberService.findByDeptMemberFetchJoin(deptId);
//
//        // email 정보로 UsernamePasswordAuthenticationToken 발급
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//                loginMember.getEmail(), null, List.of(new SimpleGrantedAuthority(loginMember.getHanRole().name())));
//        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//        // 권한 부여
//        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//        filterChain.doFilter(request, response);
//    }
//}
