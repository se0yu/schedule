package com.example.schedule.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateUserRequestDto {

    @Size(max = 6,message = "사용자명은 6글자를 초과할 수 없습니다.")
    private final String username;

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private final String email;

    @Size(min = 8,message ="비밀번호는 최소 8자 이상입니다.")
    private final String password;



}
