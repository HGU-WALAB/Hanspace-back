//package com.example.hanspaceback.jwt.dept;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//
//import java.util.Date;
//
//public class DeptTokenUtil {
//
//    // JWT Token 발급
//    public static String createToken(Long deptId, String key, long expireTimeMs) {
//        // Claim = Jwt Token에 들어갈 정보
//        Claims claims = Jwts.claims();
////        claims.put("role", role);
//        claims.put("deptId", deptId);
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
//                .signWith(SignatureAlgorithm.HS256, key)
//                .compact();
//    }
//
//    public static String getDeptId(String token, String secretKey) {
//        return extractClaims(token, secretKey).get("deptId").toString();
//    }
//
//    // 밝급된 Token이 만료 시간이 지났는지 체크
//    public static boolean isExpired(String token, String secretKey) {
//        Date expiredDate = extractClaims(token, secretKey).getExpiration();
//        // Token의 만료 날짜가 지금보다 이전인지 check
//        return expiredDate.before(new Date());
//    }
//
//    // SecretKey를 사용해 Token Parsing
//    private static Claims extractClaims(String token, String secretKey) {
//        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
//    }
//}