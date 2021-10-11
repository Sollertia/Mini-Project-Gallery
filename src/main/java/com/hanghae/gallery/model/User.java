package com.hanghae.gallery.model;

import com.hanghae.gallery.dto.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
@Entity
public class User {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private Long kakaoId;

    //likeArtist 칼럼 구현

    //일반 회원가입 user
    public User(UserDto userDto){
        this.nickname = userDto.getNickname();
        this.username = userDto.getUsername();
        this.password = userDto.getPassword();
        this.kakaoId = null;

    }
    //카카오 회원가입 user
}
