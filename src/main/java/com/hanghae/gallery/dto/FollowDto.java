package com.hanghae.gallery.dto;

import com.hanghae.gallery.model.Artist;

import com.hanghae.gallery.model.FollowEnum;
import com.hanghae.gallery.model.Work;

public class FollowDto {
    private String workTitle;
    private String workSize;
    private String workMaterial;
    private String workMade;
    private String workDesc;
    private String image;

    private String username;
    private String nickname;
    private String artistDesc;
    private String code;
    private  boolean isUser;



    public FollowDto(Artist artist, Work work, FollowEnum responseCodeSet) {
        this.workDesc = work.getWorkDesc();
        this.workMade = work.getWorkMade();
        this.workSize = work.getWorkSize();
        this.image = work.getImage();
        this.workMaterial = work.getWorkMaterial();
        this.workTitle = work.getWorkTitle();

        this.username = artist.getUsername();
        this.nickname = artist.getNickname();

        //만약 상세페이지에 설명 안들어가면 이부분 삭제
        this.artistDesc = artist.getArtistDesc();
        this.code = responseCodeSet.getCode();
        this.isUser = responseCodeSet.isUser();

    }

}
