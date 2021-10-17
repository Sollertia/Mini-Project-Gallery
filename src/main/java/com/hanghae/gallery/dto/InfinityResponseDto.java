package com.hanghae.gallery.dto;

import com.hanghae.gallery.model.Work;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InfinityResponseDto {
    private Long id;
    private String image;
    private String workTitle;
    private String workDesc;
    private String workSize;
    private String workMaterial;
    private String workMade;
    private Long artistId;


    public InfinityResponseDto(Work work){
        this.id = work.getId();
        this.workDesc = work.getWorkDesc();
        this.workMade = work.getWorkMade();
        this.workSize = work.getWorkSize();
        this.image = work.getImage();
        this.workMaterial = work.getWorkMaterial();
        this.workTitle = work.getWorkTitle();
        this.artistId = work.getArtistId();
    }

}
