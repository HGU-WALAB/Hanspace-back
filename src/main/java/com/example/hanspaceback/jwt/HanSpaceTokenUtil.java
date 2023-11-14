package com.example.hanspaceback.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class HanSpaceTokenUtil {

    // JWT Token 발급
    public static String createToken(String email, Long memberId, String key, long expireTimeMs) {
        // Claim = Jwt Token에 들어갈 정보
        Claims claims = Jwts.claims();
        claims.put("email", email);
        claims.put("memberId", memberId);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public static String getEmail(String token, String secretKey) {
        return extractClaims(token, secretKey).get("email").toString();
    }

    public static Long getMemberId(String token, String secretKey) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return Long.valueOf(claims.get("memberId").toString());
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