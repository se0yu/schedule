package com.example.schedule.service;

import com.example.schedule.dto.*;
import com.example.schedule.entity.User;
import com.example.schedule.exception.CustomException;
import com.example.schedule.exception.ErrorCode;
import com.example.schedule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

//custom exception 만들것


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    //회원가입
    @Override
    @Transactional
    public SignUpResponseDto signUp(String username, String email, String password){

        User user = new User(username,email, password);
        User savedUser = userRepository.save(user);

        return new SignUpResponseDto(savedUser.getId(),
                                    savedUser.getUsername(),
                                    savedUser.getEmail());
    }


    //로그인
    @Override
    public LoginResponseDto login(LoginRequestDto requestDto) {

        //일치하는 아이디, 비밀번호 없을 경우 오류
        User user = userRepository.findByEmailAndPassword(requestDto.getEmail(), requestDto.getPassword())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"아이디와 비밀번호를 다시 확인해주세요."));

        return new LoginResponseDto(user.getId(),user.getUsername());
    }


    //유저 단일 조회
    @Override
    public UserResponseDto findUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));

        return new UserResponseDto(user.getId(),user.getUsername(), user.getEmail(),user.getCreatedAt(),user.getUpdatedAt());
    }

    //유저 정보 수정
    @Override
    @Transactional
    public UserResponseDto updateUser(Long id, UpdateUserRequestDto requestDto) {
        //해당 유저 데이터 존재 여부 확인&불러오기
        User savedUser = userRepository.findById(id)
                .orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));

        savedUser.updateUser(requestDto.getUsername(),requestDto.getEmail(),requestDto.getPassword());

        User updatedUser = userRepository.findById(savedUser.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return new UserResponseDto(updatedUser.getId(),
                updatedUser.getUsername(),
                updatedUser.getEmail(),
                updatedUser.getCreatedAt(),
                updatedUser.getUpdatedAt());
    }

    //회원탈퇴
    @Override
    @Transactional
    public void signOut(Long id, String password) {

        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()){
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        User user = optionalUser.get();
        if(!password.equals(user.getPassword())){
            throw new CustomException(ErrorCode.WRONG_PASSWORD);
        }

        userRepository.delete(user);
    }
}
