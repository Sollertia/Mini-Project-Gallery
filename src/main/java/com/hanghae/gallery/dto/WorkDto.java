package com.hanghae.gallery.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WorkDto {

    private String workTitle;
    private String workSize;
    private String workMaterial;
    private String workMade;
    private String workDesc;
    private Long artistId;
    private String image;

}
