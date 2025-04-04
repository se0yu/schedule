package com.example.schedule.service;

import com.example.schedule.dto.*;
import com.example.schedule.entity.User;

import java.util.List;

public interface UserService {

    SignUpResponseDto signUp(SignUpRequestDto requestDto);

    LoginResponseDto login(LoginRequestDto requestDto);

    List<UserResponseDto> findAllUsers();

    UserResponseDto findUserById(Long id);

    void signOut(Long id, String password);

    User updateUser(Long id, UpdateUserRequestDto requestDto);
}
