package com.hanghae.gallery.model;
import com.hanghae.gallery.dto.ArtistDto;
import com.hanghae.gallery.dto.ArtistInfoDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


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
    private String artistImg;

    // 산하님이 만들어주신 Follow와 매핑  Follow 만든거는 아주 훌륭합니다.
//    @OneToMany(mappedBy = "artist")
//    private List<Follow> follws = new ArrayList<>();


    //작가 회원가입 시 사용합니다
    public Artist(ArtistDto artistDto){
        this.username = artistDto.getUsername();
        this.artistDesc = "";
        this.nickname = artistDto.getNickname();
        this.password = artistDto.getPassword();
    }

    // 작가 설명 수정시 사용합니다
    public void updateArtistDesc(ArtistInfoDto artistInfoDto){
        this.nickname = artistInfoDto.getNickname();
        this.artistDesc=artistInfoDto.getArtistDesc();
        this.artistImg = artistInfoDto.getProfileImg();

    }

}
