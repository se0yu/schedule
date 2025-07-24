package com.example.schedule.domain.comment.repository;

import com.example.schedule.domain.comment.entity.Comment;
import com.example.schedule.common.exception.CustomException;
import com.example.schedule.common.exception.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    default Comment findByIdOrElseThrow(Long id){
        return findById(id)
                .orElseThrow(()->new CustomException(ErrorCode.COMMENT_NOT_FOUND));
    }

    List<Comment> findByScheduleId(Long scheduleId);
}
