package com.hanghae.gallery.model;

import com.hanghae.gallery.dto.ArtistDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Artist {

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
    private String artistDesc;

    //작가 회원가입 시 사용합니다
    public Artist(ArtistDto artistDto){
        this.username = artistDto.getUsername();
        this.artistDesc = "";
        this.nickname = artistDto.getNickname();
        this.password = artistDto.getPassword();
    }

    // 작가 설명 수정시 사용합니다
    public void updateArtistDesc(String artistDesc){
        this.artistDesc=artistDesc;
    }

}
