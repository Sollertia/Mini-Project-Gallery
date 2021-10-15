package com.hanghae.gallery.controller;

import com.hanghae.gallery.dto.LoginRequestDto;
import com.hanghae.gallery.dto.SignupRequestDto;
import com.hanghae.gallery.dto.StatusMsgDto;
import com.hanghae.gallery.exception.UserSignException;
import com.hanghae.gallery.model.Artist;
import com.hanghae.gallery.model.StatusEnum;
import com.hanghae.gallery.model.RoleEnum;
import com.hanghae.gallery.model.User;
import com.hanghae.gallery.repository.ArtistRepository;
import com.hanghae.gallery.repository.UserRepository;
import com.hanghae.gallery.security.JwtTokenProvider;
import com.hanghae.gallery.service.KakaoUserService;
import com.hanghae.gallery.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public StatusMsgDto registerUser(@Valid @RequestBody SignupRequestDto signupRequestDto, Errors errors) {
        List<String> errorMessage=new ArrayList<>();

        if (errors.hasErrors()){
            for (FieldError error : errors.getFieldErrors()) {
                errorMessage.add(error.getField());
            }
        }
        // 패스워드 속에 아이디 값 중복 확인
        if(signupRequestDto.getPassword().contains(signupRequestDto.getUsername())) {
            errorMessage.add("password 안에 username이 있어서는 안됩니다.");
        }
        StatusMsgDto statusMsgDto;
        //회원가입 성공
        if (errorMessage.isEmpty()){
            Object obj = userService.registerUser(signupRequestDto);
            statusMsgDto = new StatusMsgDto(StatusEnum.STATUS_SUCCESS,obj);
        }else {
            statusMsgDto =  new StatusMsgDto(StatusEnum.STATUS_FAILE,signupRequestDto);
        }
        return statusMsgDto;

    }

    // 로그인 중복 처리
    @GetMapping("/user/redunancy")
    public void redunancy(@RequestParam String username, @RequestParam String isArtist) {
        if (isArtist.equals("artist")) {
            artistRepository.findByUsername(username).orElseThrow(() ->
                    new UserSignException("중복된 username 입니다"));
        } else {
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
        if (loginRequestDto.getIsArtist().equals("user")) {
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

        } else {
            Artist artist = artistRepository.findByUsername(loginRequestDto.getUsername())
                    .orElseThrow(() -> new UserSignException(("해당 작가는 없습니다.")));
            if (!passwordEncoder.matches(loginRequestDto.getPassword(), artist.getPassword())) {
                throw new UserSignException("잘못된 비밀번호입니다.");
            }

            token.put("token", jwtTokenProvider.createToken(artist.getUsername(),artist.getRole())); // 토큰에 이름, 역할 부여, 역할로 누군지 구분 가능

            role.put("role", String.valueOf(artist.getId())); // 메인페이지에서 작가본인이 본인 작가페이지에 접근할 때 사용
            all.add(token);
            all.add(role);
            return all;

        }
    }
    @GetMapping("/user/kakao/callback") // 카카오 로그인 JWT 처리
    public List<Map<String, String>> kakaoLogin(@RequestParam String code) throws IOException {
        User kakaoUser = kakaoUserService.kakaoLogin(code); // 카카오에서 보내주는 코드로 로그인계정 정보 가져오기
        // 카카오로그인 한 유저 로그인 및 회원가입 완료되면 토큰 발급
        Map<String, String> token = new HashMap<>();
        Map<String, String> role = new HashMap<>();

        List<Map<String, String>> all = new ArrayList<>();

        token.put("token", jwtTokenProvider.createToken(kakaoUser.getUsername(),kakaoUser.getRole())); // 토큰에 이름, 역할 부여, 역할로 누군지 구분 가능
        role.put("role", "user");

        all.add(token);
        all.add(role);

        return all;
    }
}

