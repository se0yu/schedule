package com.example.schedule.domain.user.service;

import com.example.schedule.domain.user.dto.LoginRequestDto;
import com.example.schedule.domain.user.dto.LoginResponseDto;
import com.example.schedule.domain.user.dto.SignUpRequestDto;
import com.example.schedule.domain.user.dto.SignUpResponseDto;
import com.example.schedule.domain.user.dto.UpdateUserRequestDto;
import com.example.schedule.domain.user.dto.UserResponseDto;
import com.example.schedule.domain.user.entity.User;

import java.util.List;

public interface UserService {

    SignUpResponseDto signUp(SignUpRequestDto requestDto);

    LoginResponseDto login(LoginRequestDto requestDto);

    List<UserResponseDto> findAllUsers();

    UserResponseDto findUserById(Long id);

    void signOut(Long id, String password);

    User updateUser(Long id, UpdateUserRequestDto requestDto);
}
