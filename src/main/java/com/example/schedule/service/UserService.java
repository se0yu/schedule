package com.example.schedule.service;

import com.example.schedule.dto.*;

public interface UserService {

    SignUpResponseDto signUp(String username, String email, String password);

    LoginResponseDto login(LoginRequestDto requestDto);

    UserResponseDto findUserById(Long id);

    void signOut(Long id, String password);

    UserResponseDto updateUser(Long id, UpdateUserRequestDto requestDto);
}
