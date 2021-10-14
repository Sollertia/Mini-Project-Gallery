package com.hanghae.gallery.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter // 이미지 이름 등록
//클라이언트 -> 서버 받을 때
@Getter
@NoArgsConstructor
public class WorkRequestDto {

    // 작품 등록시 이미지 이름 필요없음, 수정 시에도 무조건 필요하지는 않음.
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
