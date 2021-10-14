package com.hanghae.gallery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


//클라이언트 -> 서버 받을 때
@Getter
@AllArgsConstructor
public class WorkRequestDto {

    private Long id;
    @NotBlank
    private String image;

    private String workTitle;

    @NotBlank
    private String workDesc;

    @NotBlank
    private String workSize;

    @NotBlank
    private String workMaterial;

    @NotBlank
    private String workMade;


}
