package com.example.schedule.service;

import com.example.schedule.dto.*;

import java.util.List;

public interface UserService {

    SignUpResponseDto signUp(SignUpRequestDto requestDto);

    LoginResponseDto login(LoginRequestDto requestDto);

    List<UserResponseDto> findAllUsers();

    UserResponseDto findUserById(Long id);

    void signOut(Long id, String password);

    UserResponseDto updateUser(Long id, UpdateUserRequestDto requestDto);
}
