package com.hanghae.gallery.dto;

import com.hanghae.gallery.model.Work;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

//서버 -> 클라이언트 보낼 때
@Getter
@NoArgsConstructor
public class WorkResponseDto {

    private Long id;
    private String image;
    private String workTitle;
    private String workDesc;
    private String workSize;
    private String workMaterial;
    private String workMade;
    private Long artistId;


    public WorkResponseDto(Work work){
        this.id = work.getId();
        this.artistId = work.getArtistId();
        this.workDesc = work.getWorkDesc();
        this.workMade = work.getWorkMade();
        this.workSize = work.getWorkSize();
        this.image = work.getImage();
        this.workMaterial = work.getWorkMaterial();
        this.workTitle = work.getWorkTitle();
    }

}
