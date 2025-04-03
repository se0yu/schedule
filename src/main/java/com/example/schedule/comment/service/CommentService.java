package com.example.schedule.comment.service;

import com.example.schedule.comment.dto.CommentRequsetDto;
import com.example.schedule.comment.dto.CommentResponseDto;

public interface CommentService {

    CommentResponseDto saveComment(Long scheduleId,Long loginUserId,CommentRequsetDto requsetDto);
}
