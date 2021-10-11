package com.hanghae.gallery.model;

import com.hanghae.gallery.dto.WorkDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Work {


    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(unique = true)
    private String workTitle="무제";

    @Column(nullable = false)
    private String workSize;

    @Column(nullable = false)
    private String workMaterial;

    @Column(nullable = false)
    private String workMade;

    @Column(nullable = false)
    private String workDesc;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private Long artistId;



    public Work(WorkDto workDto){
        this.artistId = workDto.getArtistId();
        this.workDesc = workDto.getWorkDesc();
        this.workMade = workDto.getWorkMade();
        this.workSize = workDto.getWorkSize();
        this.image = workDto.getImage();
        this.workMaterial = workDto.getWorkMaterial();
        this.workTitle = workDto.getWorkTitle();
    }

}
