package com.hanghae.gallery.service;

import com.hanghae.gallery.dto.SignupRequestDto;
import com.hanghae.gallery.exception.UserSignException;
import com.hanghae.gallery.model.Artist;
import com.hanghae.gallery.model.RoleEnum;
import com.hanghae.gallery.model.User;
import com.hanghae.gallery.repository.ArtistRepository;
import com.hanghae.gallery.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ArtistRepository artistRepository;


    // 회원가입 - 아티스트와 유저 비교해서 회원 가입
    public void registerUser(SignupRequestDto signupRequestDto) {
        RoleEnum role;
        String errorMessage;
        // signupRequestDto에서 유저네임 가져옴
        String username = signupRequestDto.getUsername();

        // 패스워드 속에 아이디 값 중복 확인
        if(signupRequestDto.getPassword().contains(username)) {
            errorMessage = "password 안에 username이 있어서는 안됩니다.";
            throw new UserSignException(errorMessage);
        }

        // 패스워드 인코딩
        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        // nickname
        String nickname = signupRequestDto.getNickname();
        // 회원가입 시에 IsArtist가 artist이면
        if(signupRequestDto.getIsArtist().equals("artist")){
            // 아티스트 권한으로
            role = RoleEnum.ARTIST;
            // 아티스트 레포지토리에 정보를 저장
            Artist artist = new Artist(username, password, nickname, role);
            artistRepository.save(artist);
        }else{
            // 일반 유저면 유저레포지토리에 정보를 저장
            role = RoleEnum.USER;
            User user = new User(username, password, nickname, role);
            userRepository.save(user);
        }
      
    }

}
