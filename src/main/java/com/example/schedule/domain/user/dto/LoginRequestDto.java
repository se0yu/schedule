package com.example.schedule.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.antlr.v4.runtime.misc.NotNull;


@Getter
@AllArgsConstructor
public class LoginRequestDto {

    @NotBlank(message = "이메일 입력은 필수입니다.")
    private final String email;

    @NotNull
    private final String password;
}
