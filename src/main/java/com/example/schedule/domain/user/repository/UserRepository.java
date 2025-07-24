package com.example.schedule.domain.user.repository;

import com.example.schedule.domain.user.entity.User;
import com.example.schedule.exception.CustomException;
import com.example.schedule.exception.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    default User findByIdOrElseThrow(Long id){
        return findById(id)
                .orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));
    }
}
