package com.example.schedule.comment.service;

import com.example.schedule.comment.dto.CommentRequestDto;
import com.example.schedule.comment.dto.CommentResponseDto;
import com.example.schedule.dto.LoginResponseDto;

import java.util.List;

public interface CommentService {

    CommentResponseDto saveComment(Long scheduleId, Long loginUserId, CommentRequestDto requestDto);

    List<CommentResponseDto> findAllSchedules();

    CommentResponseDto updateComment(Long schedlueId, CommentRequestDto requestDto, Long userId);

    void deleteComment(LoginResponseDto loginUser, Long id);
}
