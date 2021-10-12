package com.hanghae.gallery.controller;

import com.hanghae.gallery.dto.ArtistInfoDto;
import com.hanghae.gallery.model.User;
import com.hanghae.gallery.service.ArtistService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ArtistRestController {

    private final ArtistService artistService;

    public ArtistRestController(ArtistService artistService){
        this.artistService = artistService;
    }

    @PostMapping("/artist/update")// userdetails로 정보 가져와야합니다 (수정하려는 작가 == 현재 유저)
    public void updateArtistInfo(@RequestBody ArtistInfoDto artistInfoDto,
                                 User user){
        artistService.updateInfo(artistInfoDto, user);


    }
}
