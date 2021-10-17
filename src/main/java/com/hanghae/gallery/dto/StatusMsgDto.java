package com.hanghae.gallery.dto;

import com.hanghae.gallery.model.StatusEnum;
import lombok.Getter;


@Getter
public class StatusMsgDto {

    String status;
    Object object;

    public StatusMsgDto(StatusEnum s, Object o){
        this.status = s.getStatus();
        this.object = o;
    }
}
