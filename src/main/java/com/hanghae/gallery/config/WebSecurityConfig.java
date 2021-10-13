package com.hanghae.gallery.config;

import com.hanghae.gallery.security.JwtAuthenticationFilter;
import com.hanghae.gallery.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;


    @Bean // 비밀번호 암호화해서 등록할 때 사용
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    @Override // authenticationManager를 Bean 등록
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable(); // API 집중을 위해 CSRF 무시
        http.httpBasic().disable(); //Http basic Auth 가 생성하는 로그인 창 막기
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);  // jwt token 사용
        http
                .authorizeRequests() // 권한 요청 설정
                .antMatchers("/user").anonymous() // 로그인, 회원가입 관련은 비인증 유저만 접근 가능
                .anyRequest().permitAll() // 나머지 요청은 누구나 접근 가능
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);
    }


    @Override
    public void configure(WebSecurity web) {
        // h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
        web
                .ignoring()
                .antMatchers("/h2-console/**");
    }


}


