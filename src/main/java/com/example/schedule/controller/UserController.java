package com.example.schedule.controller;

import com.example.schedule.common.Const;
import com.example.schedule.dto.*;
import com.example.schedule.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //로그인 기능
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @Valid @RequestBody LoginRequestDto requestDto,
                                    HttpServletRequest request
            ){
        LoginResponseDto loginResponseDto = userService.login(requestDto.getUsername(),requestDto.getPassword());

        HttpSession session = request.getSession();
        session.setAttribute(Const.LOGIN_USER,loginResponseDto);


        return new ResponseEntity<>(loginResponseDto,HttpStatus.OK);
    }

    //로그아웃 기능
    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request){

        HttpSession session = request.getSession(false);
        if(session == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        session.invalidate();
        return new ResponseEntity<>(HttpStatus.OK);
    }

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

    //유저 목록 조회

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