package org.example.login.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class UserLoginDto {

    @NotEmpty(message = "아이디를 입력하세요.")
    private String loginId;

    @NotEmpty(message = "비밀번호를 입력하세요.")
    private String password;
}
