package com.example.schedule.domain.user.service;

import com.example.schedule.common.config.PasswordEncoder;
import com.example.schedule.domain.user.dto.LoginRequestDto;
import com.example.schedule.domain.user.dto.LoginResponseDto;
import com.example.schedule.domain.user.dto.SignUpRequestDto;
import com.example.schedule.domain.user.dto.SignUpResponseDto;
import com.example.schedule.domain.user.dto.UpdateUserRequestDto;
import com.example.schedule.domain.user.dto.UserResponseDto;
import com.example.schedule.domain.user.entity.User;
import com.example.schedule.common.exception.CustomException;
import com.example.schedule.common.exception.ErrorCode;
import com.example.schedule.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//custom exception 만들것


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    @Override
    @Transactional
    public SignUpResponseDto signUp(SignUpRequestDto requestDto){

        String hashedPassword = passwordEncoder.encode(requestDto.getPassword());
        User user = new User(requestDto.getUsername(), requestDto.getEmail(), hashedPassword);
        User savedUser = userRepository.save(user);

        return new SignUpResponseDto(savedUser.getId(),
                                    savedUser.getUsername(),
                                    savedUser.getEmail());
    }


    //로그인
    @Override
    public LoginResponseDto login(LoginRequestDto requestDto) {

        //일치하는 아이디가 없을 경우 오류
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(()->new CustomException(ErrorCode.USER_LOGIN_FAIL));

        if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())){
            throw new CustomException(ErrorCode.WRONG_PASSWORD);
        }

        return new LoginResponseDto(user.getId(),user.getUsername());
    }

    //유저 전체 조회
    @Override
    public List<UserResponseDto> findAllUsers() {

        return userRepository.findAll().stream().map(UserResponseDto::toDto)
                .toList();
    }


    //유저 단일 조회
    @Override
    public UserResponseDto findUserById(Long id) {

        User user = userRepository.findByIdOrElseThrow(id);

        return new UserResponseDto(user.getId(),
                                    user.getUsername(),
                                    user.getEmail(),
                                    user.getCreatedAt(),
                                    user.getUpdatedAt());
    }

    //유저 정보 수정
    @Override
    @Transactional
    public User updateUser(Long id, UpdateUserRequestDto requestDto) {
        //해당 유저 데이터 존재 여부 확인&불러오기
        User savedUser = userRepository.findByIdOrElseThrow(id);

        String hashedPassword = passwordEncoder.encode(requestDto.getPassword());
        savedUser.updateUser(requestDto.getUsername(),requestDto.getEmail(),hashedPassword);

        return savedUser;
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
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new CustomException(ErrorCode.WRONG_PASSWORD);
        }

        userRepository.delete(user);
    }

}
