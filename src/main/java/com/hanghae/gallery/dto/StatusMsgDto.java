package com.hanghae.gallery.dto;

import com.hanghae.gallery.model.StatusEnum;

public class StatusMsgDto {

    String status;
//    String[] errorMsg;
    Object object;


//    public StatusMsgDto(StatusEnum s,String[] e, Object o){
//        this.status = s.getStatus();
//        this.errorMsg=e;
//        this.object = o;
//
//    }
    public StatusMsgDto(StatusEnum s, Object o){
        this.status = s.getStatus();
        this.object = o;

    }
}
