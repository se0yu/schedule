package com.example.schedule.service;

import com.example.schedule.dto.SignUpResponseDto;

public interface UserService {

    SignUpResponseDto signUp(String username, String email, String password);
}
