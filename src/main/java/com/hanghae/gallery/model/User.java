package com.hanghae.gallery.model;

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

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private RoleEnum role;

    @Column()
    private Long kakaoId;

    //테이블에 따로 저장 안됨
    //연관관계 명시
    @OneToMany(mappedBy = "user")
    private List<Follow> followList = new ArrayList<>();

    //일반 회원가입 user
    public User(String username, String password, String nickname, RoleEnum role){
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
    }
    // 카카오 로그인
    public User(String nickname, String password, String username, RoleEnum role, Long kakaoId){
        this.nickname = nickname;
        this.username = username;
        this.password = password;
        this.kakaoId = kakaoId;
        this.role = role;
    }


}
