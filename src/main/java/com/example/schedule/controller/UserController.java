package com.example.schedule.controller;

import com.example.schedule.dto.SignOutRequestDto;
import com.example.schedule.dto.SignUpRequestDto;
import com.example.schedule.dto.SignUpResponseDto;
import com.example.schedule.dto.UserResponseDto;
import com.example.schedule.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    //회원가입 기능
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto requestDto) {

        SignUpResponseDto signUpResponseDto =
                userService.signUp(
                        requestDto.getUsername(),
                        requestDto.getEmail(),
                        requestDto.getPassword()
                );

        return new ResponseEntity<>(signUpResponseDto, HttpStatus.CREATED);
    }

    //특정 유저 조회(이름, 이메일)
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable Long id){

        UserResponseDto userResponseDto = userService.findUserById(id);

        return new ResponseEntity<>(userResponseDto,HttpStatus.OK);
    }


    //특정 유저 정보 수정


    //회원탈퇴
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id,@RequestBody SignOutRequestDto requestDto){

        userService.signOut(id,requestDto.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}