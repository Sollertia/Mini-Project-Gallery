package com.hanghae.gallery.model;

import lombok.Getter;

@Getter
public enum StatusEnum {

    STATUS_SUCCESS("SUCCESS"),
    STATUS_FAILE("FAILE");

    private final String status;

    StatusEnum(String status){
        this.status=status;
    }


}
