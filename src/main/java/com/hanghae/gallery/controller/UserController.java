package com.hanghae.gallery.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hanghae.gallery.dto.LoginRequestDto;
import com.hanghae.gallery.dto.SignupRequestDto;
import com.hanghae.gallery.exception.UserSignException;
import com.hanghae.gallery.model.Artist;
import com.hanghae.gallery.model.User;
import com.hanghae.gallery.repository.ArtistRepository;
import com.hanghae.gallery.repository.UserRepository;
import com.hanghae.gallery.security.JwtTokenProvider;
import com.hanghae.gallery.service.UserService;
import com.hanghae.gallery.service.KakaoUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final ArtistRepository artistRepository;
    private final KakaoUserService kakaoUserService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;


    // 회원 가입 요청 처리
    @PostMapping("/user/signup")
    // registerUser의 SignupRequestDto 와 에러를 실행하는 메소드
    public void registerUser(@Valid @RequestBody SignupRequestDto signupRequestDto, Errors errors) {
        String errorMessage;
        // 에러 빈에 필드를 찾아서 에러메시지에 담음
        for (FieldError error : errors.getFieldErrors()) {
            errorMessage = error.getField();
            // 받은 에러메시지를 UserSignException에 던짐
            throw new UserSignException(errorMessage);
        }
        // 유저서비스에서 회원가입 처리
        userService.registerUser(signupRequestDto);
    }

    // 로그인 중복 처리
    @GetMapping("/user/redunancy")
    // redunancy 메소드에서 url의 username과 isArtist를 받음
    public void redunancy(@RequestParam String username, @RequestParam String isArtist) {
        // 받은 isArtist가 레포지토리에 존재하는 유저네임, artist이면 중복처리
        if (isArtist.equals("artist")) {
            artistRepository.findByUsername(username).orElseThrow(() ->
                    new UserSignException("중복된 username 입니다"));
        } else {
            // 받은 유저네임이 레포지토리에 존재하면 중복처리
            userRepository.findByUsername(username).orElseThrow(() ->
                    new UserSignException("중복된 username 입니다"));
        }
    }

    // 닉네임 중복 처리
    @GetMapping("/user/nickname")
    public void nickname(@RequestParam String nickname,  @RequestParam String isArtist) {
        if (isArtist.equals("artist")) {
            artistRepository.findByNickname(nickname).orElseThrow(() ->
                    new UserSignException("중복된 nickname 입니다"));
        } else {
            userRepository.findByNickname(nickname).orElseThrow(() ->
                    new UserSignException("중복된 nickname 입니다"));
        }
    }


    // 로그인  -  가독성 및 유지보수를 위해 서비스를 사용하지 않았다.
    // 이유는 컨트롤러에서 클라이언트로 JWT 와 함께 유저, 아티스트 비교 값을 넘겨야 하기 때문에 유저의 로그인 정보를 바로 레포지토리에서
    // 유저인지 아닌지 확인하고 맞으면 가져온 유저 정보를 이용해서 유저의 role을 가져와 역할을 비교해서 클라이언트로 함께 보내줌
    @PostMapping("/user/login")  // 유저와 아티스트 구분을 위해서 비교할 수 있는 값을 보내주고 JWT는 쿠키에 비교값은 로컬스토리지에 보관
    public List<Map<String, String>> login(@RequestBody LoginRequestDto loginRequestDto) { // Key, Value 형식 Map사용하기

        Map<String, String> token = new HashMap<>();
        Map<String, String> role = new HashMap<>();

        List<Map<String, String>> all = new ArrayList<>(); // 리스트에 map을 담아서 한번에 보낸다
        // ["token" : {token}, "role" : {role}] - res.data[0].token , res.data[1].role  리액트에서 값 받는 방법


        // 유저 인지 아티스트 인지 비교
        if (loginRequestDto.getIsArtist().equals("artist")) {
            // 아티스트 레포지토리에서
            Artist artist = artistRepository.findByUsername(loginRequestDto.getUsername())
                    .orElseThrow(() -> new UserSignException(("해당 작가는 없습니다.")));
            if (!passwordEncoder.matches(loginRequestDto.getPassword(), artist.getPassword())) {
                throw new UserSignException("잘못된 비밀번호입니다.");
            }

           token.put("token", jwtTokenProvider.createToken(artist.getUsername(),artist.getRole())); // 토큰에 이름, 역할 부여, 역할로 누군지 구분 가능

            role.put("role", "artist");
            all.add(token);
            all.add(role);
            return all;

        } else {
            User user = userRepository.findByUsername(loginRequestDto.getUsername())
                    .orElseThrow(() -> new UserSignException("해당 유저는 없는 유저입니다."));

            if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
                throw new UserSignException("잘못된 비밀번호입니다.");
            }

            token.put("token", jwtTokenProvider.createToken(user.getUsername(),user.getRole())); // 토큰에 이름, 역할 부여, 역할로 누군지 구분 가능

            role.put("role", "user");
            all.add(token);
            all.add(role);
            return all;

        }
    }
    // 카카오 유저 정보 받기
    @GetMapping("/user/kakao/callback")
    public String kakaoLogin(@RequestParam String code) throws IOException {
        kakaoUserService.kakaoLogin(code);
        return "redirect:/";
    }
}

