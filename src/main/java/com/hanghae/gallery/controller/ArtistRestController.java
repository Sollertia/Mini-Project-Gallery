package com.hanghae.gallery.controller;

import com.hanghae.gallery.dto.ArtistInfoDto;
import com.hanghae.gallery.exception.UserSignException;
import com.hanghae.gallery.model.User;
import com.hanghae.gallery.repository.ArtistRepository;
import com.hanghae.gallery.service.ArtistService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ArtistRestController {

    private final ArtistService artistService;
    private final ArtistRepository artistRepository;


    public ArtistRestController(ArtistService artistService,ArtistRepository artistRepository){
        this.artistService = artistService;
        this.artistRepository = artistRepository;
    }

    // userdetails로 정보 가져와야합니다 (수정하려는 작가 == 현재 유저)
    //작가 프로필 수정
    @PostMapping("/artist/update")
    public void updateArtistInfo(@RequestBody ArtistInfoDto artistInfoDto, User user){

        String newNickname = artistInfoDto.getNickname();

        if (artistRepository.findById(user.getId()).equals(newNickname)){
            throw new UserSignException("중복되는 닉네임이 있습니다.");
        }

        artistService.updateInfo(artistInfoDto, user);

    }
}
