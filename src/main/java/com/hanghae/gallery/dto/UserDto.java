package com.hanghae.gallery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {

    private String username;
    private String nickname;
    private String password;
    private String kakaoId;

}
