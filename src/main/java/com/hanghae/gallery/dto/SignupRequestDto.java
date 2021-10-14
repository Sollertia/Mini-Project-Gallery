package com.hanghae.gallery.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
public class SignupRequestDto {
    @NotBlank(message = "username 값이 비어있지 않아야 합니다.")
    @Size(min = 3, message = "Username은 최소 3자 이상입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]*$",message = "Username은 알파벳대소문자와 숫자만 가능합니다.")
    private String username;

    @NotBlank(message = "password 값이 비어있지 않아야 합니다.")
    @Size(min = 4, message = "Password는 최소 4자 이상입니다.")
    private String password;

    @NotBlank(message = "nickname 값이 비어있지 않아야 합니다.")
    @Size(min = 4, message = "nickname 최소 4자 이상입니다.")
    private String nickname;

    @NotBlank(message = " 값이 비어있지 않아야 합니다.")
    private String isArtist;
}
