package com.hanghae.gallery.service;

import com.hanghae.gallery.dto.SignupRequestDto;
import com.hanghae.gallery.exception.UserSignException;
import com.hanghae.gallery.model.Artist;
import com.hanghae.gallery.model.RoleEnum;
import com.hanghae.gallery.model.StatusEnum;
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
    public Object registerUser(SignupRequestDto signupRequestDto) {
        System.out.println("확인");
        RoleEnum role;
        String username = signupRequestDto.getUsername();
        // 패스워드 인코딩
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        // nickname
        String nickname = signupRequestDto.getNickname();

        if(signupRequestDto.getIsArtist().equals("artist")){
            role = RoleEnum.ARTIST;
            Artist artist = new Artist(username, password, nickname, role);
            return artistRepository.save(artist);
        }else{
            role = RoleEnum.USER;
            User user = new User(username, password, nickname, role);
            return userRepository.save(user);
        }

    }

}
