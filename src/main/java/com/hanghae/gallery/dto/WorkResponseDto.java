package com.hanghae.gallery.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//서버 -> 클라이언트 보낼 때
@Getter
@Setter
@NoArgsConstructor
public class WorkResponseDto {

    private Long id;
    private String workTitle;
    private String workSize;
    private String workMaterial;
    private String workMade;
    private String workDesc;
    private Long artistId;
    private String image;
}
