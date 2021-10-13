package com.hanghae.gallery.service;


import com.hanghae.gallery.dto.ArtistInfoDto;
import com.hanghae.gallery.model.Artist;
import com.hanghae.gallery.model.User;
import com.hanghae.gallery.repository.ArtistRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository){
        this.artistRepository = artistRepository;
    }

    @Transactional
    public void updateInfo(ArtistInfoDto artistInfoDto, User user){
        Artist artist = artistRepository.findById(user.getId())
                .orElseThrow(()-> new IllegalArgumentException("해당 계정을 찾을 수 없습니다."));

        artist.updateArtistDesc(artistInfoDto);


    }


}