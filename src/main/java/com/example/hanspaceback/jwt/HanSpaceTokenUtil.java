package com.example.hanspaceback.jwt;

import com.example.hanspaceback.dto.response.DepartmentResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.*;
import java.util.stream.Collectors;

public class HanSpaceTokenUtil {
    public static String createToken(String email, Long memberId, String secretKey, long expireTimeMs, List<DepartmentResponse> departmentResponses) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("memberId", memberId);

        // departmentResponses에서 deptId와 deptRole을 추출하여 클레임에 추가
        List<Map<String, Object>> deptRolesClaims = departmentResponses.stream()
                .map(department -> {
                    Map<String, Object> deptRoleClaim = new HashMap<>();
                    deptRoleClaim.put("deptId", department.getDeptId());
                    department.getDeptMemberResponse().forEach(deptMemberResponse -> {
                        deptRoleClaim.put("deptRole", deptMemberResponse.getDeptRole());
                    });
                    return deptRoleClaim;
                }).collect(Collectors.toList());

        claims.put("deptRoles", deptRolesClaims);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }


    public static String getEmail(String token, String secretKey) {
        return extractClaims(token, secretKey).get("email").toString();
    }

    public static Long getMemberId(String token, String secretKey) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return Long.valueOf(claims.get("memberId").toString());
    }

    public static List<CustomUserDetails.DeptRole> getDeptRoles(String token, String secretKey) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        // claims에서 deptRoles 정보를 추출
        List<Map<String, Object>> deptRolesMaps = (List<Map<String, Object>>) claims.get("deptRoles");
        List<CustomUserDetails.DeptRole> deptRoles = new ArrayList<>();

        for (Map<String, Object> deptRoleMap : deptRolesMaps) {
            CustomUserDetails.DeptRole deptRole = new CustomUserDetails.DeptRole();
            deptRole.setDeptId((Integer) deptRoleMap.get("deptId"));
            deptRole.setDeptRole((String) deptRoleMap.get("deptRole"));
            deptRoles.add(deptRole);
        }

        return deptRoles;
    }

    // 밝급된 Token이 만료 시간이 지났는지 체크
    public static boolean isExpired(String token, String secretKey) {
        Date expiredDate = extractClaims(token, secretKey).getExpiration();
        // Token의 만료 날짜가 지금보다 이전인지 check
        return expiredDate.before(new Date());
    }

    // SecretKey를 사용해 Token Parsing
    private static Claims extractClaims(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
}