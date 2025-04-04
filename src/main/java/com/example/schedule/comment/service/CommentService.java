package com.example.schedule.comment.service;

import com.example.schedule.comment.dto.CommentRequestDto;
import com.example.schedule.comment.dto.CommentResponseDto;
import com.example.schedule.comment.entity.Comment;

import java.util.List;

public interface CommentService {

    CommentResponseDto saveComment(Long scheduleId, Long loginUserId, CommentRequestDto requestDto);

    List<CommentResponseDto> findAllComments(Long scheduleId);

    Comment updateComment(Long schedlueId, Long id, Long userId, CommentRequestDto requestDto);

    void deleteComment(Long loginUserId, Long id);
}
