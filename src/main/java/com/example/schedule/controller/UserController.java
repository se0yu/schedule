package com.example.schedule.controller;

import com.example.schedule.common.Const;
import com.example.schedule.dto.*;
import com.example.schedule.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

        LoginResponseDto loginResponseDto = userService.login(requestDto);

        HttpSession session = request.getSession();
        session.setAttribute(Const.LOGIN_USER,loginResponseDto);


        return new ResponseEntity<>(loginResponseDto,HttpStatus.OK);
    }

    //로그아웃 기능
    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response){

        HttpSession session = request.getSession(false);

        //로그인 하지 않은 상태로 접근할 시 오류 출력
        if(session == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        //서버 세션 삭제
        session.invalidate();

        //세션 쿠키 삭제
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

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
//    @GetMapping
//    public ResponseEntity<List<UserResponseDto>> findAllUser(){
//
//
//    }


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