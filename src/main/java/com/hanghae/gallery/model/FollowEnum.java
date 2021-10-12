package com.hanghae.gallery.model;
import lombok.Getter;

@Getter
public enum FollowEnum {

    USER_FOLLOW("Y",true),
    USER_UNFOLLOW("N",true),
    NON_USER_UNFOLLOW("N",false);

    private final String code;
    private final boolean isUser;

    FollowEnum(String code,boolean isUser){
        this.code = code;
        this.isUser = isUser;

    }

}
