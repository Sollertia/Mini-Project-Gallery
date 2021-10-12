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

    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    // 산하님이 만들어주신 Follow와 매핑  Follow 만든거는 아주 훌륭합니다.
    @OneToMany(mappedBy = "user")
    private List<Follow> follws = new ArrayList<>();

    //테이블에 따로 저장 안됨 repository 이용할 수 있을듯.
//    @OneToMany(mappedBy = "user")
//    private List<Artist> followList = new ArrayList<>();

    //일반 회원가입 user
    public User(UserDto userDto){
        this.nickname = userDto.getNickname();
        this.username = userDto.getUsername();
        this.password = userDto.getPassword();
        this.kakaoId = null;
    }
    //카카오 회원가입 user
}
