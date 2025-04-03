package com.example.schedule.service;

import com.example.schedule.dto.*;

public interface UserService {

    SignUpResponseDto signUp(SignUpRequestDto requestDto);

    LoginResponseDto login(LoginRequestDto requestDto);

    UserResponseDto findUserById(Long id);

    void signOut(Long id, String password);

    UserResponseDto updateUser(Long id, UpdateUserRequestDto requestDto);
}
