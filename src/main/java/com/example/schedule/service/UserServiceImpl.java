package com.example.schedule.service;

import com.example.schedule.dto.LoginRequestDto;
import com.example.schedule.dto.LoginResponseDto;
import com.example.schedule.dto.SignUpResponseDto;
import com.example.schedule.dto.UserResponseDto;
import com.example.schedule.entity.User;
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
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"존재하지 않는 아이디입니다."));

        return new UserResponseDto(user.getUsername(), user.getEmail());
    }

    //회원탈퇴
    @Override
    @Transactional
    public void signOut(Long id, String password) {

        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"존재하지 않는 아이디입니다.");
        }

        User user = optionalUser.get();
        if(!password.equals(user.getPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"비밀번호가 틀렸습니다.");
        }

        userRepository.delete(user);
    }
}
