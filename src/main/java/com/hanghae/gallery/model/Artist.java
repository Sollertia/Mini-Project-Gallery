package com.hanghae.gallery.model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;


@NoArgsConstructor
@Getter
@Entity
public class Artist {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String artistDesc;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private RoleEnum role;

    // 산하님이 만들어주신 Follow와 매핑  Follow 만든거는 아주 훌륭합니다.
//    @OneToMany(mappedBy = "artist")
//    private List<Follow> followList = new ArrayList<>();


    //작가 회원가입 시 사용합니다
    public Artist(String username, String password, String nickname, RoleEnum role){
        this.artistDesc = "";
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
    }

    // 작가 설명 수정시 사용합니다
    public void updateArtistDesc(String artistDesc){

        this.artistDesc=artistDesc;
    }

}
