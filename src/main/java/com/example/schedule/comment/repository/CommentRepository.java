package com.example.schedule.comment.repository;

import com.example.schedule.comment.entity.Comment;
import com.example.schedule.exception.CustomException;
import com.example.schedule.exception.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    default Comment findByIdOrElseThrow(Long id){
        return findById(id)
                .orElseThrow(()->new CustomException(ErrorCode.COMMENT_NOT_FOUND));
    }
}
