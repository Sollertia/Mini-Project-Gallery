package com.hanghae.gallery.dto;

import com.hanghae.gallery.model.Work;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

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

    @Value("${file.dir}")
    private String fileDir;

    public InfinityResponseDto(Work work){
        this.id = work.getId();
        this.workDesc = work.getWorkDesc();
        this.workMade = work.getWorkMade();
        this.workSize = work.getWorkSize();
        this.image = fileDir+work.getImage();
        this.workMaterial = work.getWorkMaterial();
        this.workTitle = work.getWorkTitle();
        this.artistId = work.getArtistId();
    }

}
