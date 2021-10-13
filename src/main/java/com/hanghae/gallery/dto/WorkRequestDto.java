package com.hanghae.gallery.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;


//클라이언트 -> 서버 받을 때
@Getter
@NoArgsConstructor
public class WorkRequestDto {

    private String image;
    private String workTitle;
    private String workDesc;
    private String workSize;
    private String workMaterial;
    private String workMade;

}
