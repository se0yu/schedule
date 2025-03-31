package com.example.schedule.service;

import com.example.schedule.dto.SignUpResponseDto;
import com.example.schedule.dto.UserResponseDto;

public interface UserService {

    SignUpResponseDto signUp(String username, String email, String password);

    UserResponseDto findUserById(Long id);

    void signOut(Long id, String password);
}
