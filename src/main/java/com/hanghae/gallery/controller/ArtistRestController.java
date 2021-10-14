package com.hanghae.gallery.controller;

import com.hanghae.gallery.dto.ArtistInfoDto;
import com.hanghae.gallery.dto.StatusMsgDto;
import com.hanghae.gallery.exception.NoFoundException;
import com.hanghae.gallery.model.Artist;
import com.hanghae.gallery.model.StatusEnum;
import com.hanghae.gallery.model.User;
import com.hanghae.gallery.repository.ArtistRepository;
import com.hanghae.gallery.service.ArtistService;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
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
    public StatusMsgDto updateArtistInfo(@Validated @RequestBody ArtistInfoDto artistInfoDto,
                                         User user, Errors errors){

        String newNickname = artistInfoDto.getNickname();
        Artist artistId = artistRepository.findById(user.getId()).orElseThrow(()
        -> new NoFoundException("해당 작가를 찾을 수 없습니다."));
        if (artistId.getNickname().equals(newNickname) || errors.hasErrors()){
            return new StatusMsgDto(StatusEnum.STATUS_FAILE,artistInfoDto);
        }

        Artist artist = artistService.updateInfo(artistInfoDto, user);

        return new StatusMsgDto(StatusEnum.STATUS_SUCCESS,artist);

    }
}
