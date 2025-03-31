package com.example.schedule.service;

import com.example.schedule.dto.SignUpResponseDto;
import com.example.schedule.dto.UserResponseDto;
import com.example.schedule.entity.User;
import com.example.schedule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public SignUpResponseDto signUp(String username, String email, String password){

        User user = new User(username,email, password);
        User savedUser = userRepository.save(user);

        return new SignUpResponseDto(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail());
    }

    @Override
    public UserResponseDto findUserById(Long id) {

        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"존재하지 않는 아이디입니다.");
        }

        User user = optionalUser.get();
        return new UserResponseDto(user.getUsername(), user.getEmail());
    }

    @Override
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
