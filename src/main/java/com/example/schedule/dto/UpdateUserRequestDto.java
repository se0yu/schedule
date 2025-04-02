package com.example.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateUserRequestDto {

    private final String username;
    private final String email;
    private final String password;


}
