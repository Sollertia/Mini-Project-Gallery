package com.hanghae.gallery.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class ArtistInfoDto {

    @NotNull
    private String nickname;

    @NotNull
    private String profileImg;

    @NotNull
    private String artistDesc;


}
