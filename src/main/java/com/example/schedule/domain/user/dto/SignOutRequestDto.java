package com.example.schedule.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignOutRequestDto {

    private final String password;
}
