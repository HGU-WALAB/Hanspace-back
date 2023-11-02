package com.example.hanspaceback.jwt;

import com.example.hanspaceback.domain.HanRole;
import com.example.hanspaceback.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MemberService memberService;
    private static String secretKey = "my-secret-key-123123";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return httpSecurity
//                .httpBasic().disable()
//                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .addFilterBefore(new JwtTokenFilter(memberService, secretKey), UsernamePasswordAuthenticationFilter.class)
//                .authorizeRequests()
////                .antMatchers("/jwt-login/info").authenticated()
////                .antMatchers("/jwt-login/admin/**").hasAuthority(UserRole.ADMIN.name())
//                .and().build();
        http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JwtTokenFilter(memberService, secretKey), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(request -> request
                        .requestMatchers(
                                "/jwt-login/login", "/member/signup"
                        )
                        .permitAll()
                )
                .authorizeHttpRequests(request -> request
//                        .anyRequest().authenticated()
                        .requestMatchers(
                                "/jwt-login/info"
                        ).authenticated()
                )
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/jwt-login/admin/**")
                        .hasAuthority(HanRole.ADMIN.name())
                );
        return http.build();
    }
}
