package com.example.schedule.domain.comment.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.schedule.common.Const;
import com.example.schedule.domain.comment.dto.CommentRequestDto;
import com.example.schedule.domain.comment.dto.CommentResponseDto;
import com.example.schedule.domain.comment.entity.Comment;
import com.example.schedule.domain.comment.service.CommentService;
import com.example.schedule.domain.user.dto.LoginResponseDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/schedules/{scheduleId}/comments")
@RequiredArgsConstructor
public class CommentController {


    private final CommentService commentService;

    //댓글 작성 기능
    @PostMapping
    public ResponseEntity<CommentResponseDto> saveComment(
            @PathVariable Long scheduleId,
            HttpServletRequest httpServletRequest,
            @Valid @RequestBody CommentRequestDto requsetDto
    ){

        LoginResponseDto loginUser = (LoginResponseDto) httpServletRequest.getSession().getAttribute(Const.LOGIN_USER);
        CommentResponseDto responseDto = commentService.saveComment(scheduleId,loginUser.getId(),requsetDto);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    //댓글 전체 조회 기능
    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> findAllComments(
            @PathVariable Long scheduleId
    ){

        List<CommentResponseDto> responseDto = commentService.findAllComments(scheduleId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    //댓글 수정
    @PatchMapping("/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long scheduleId,
            @PathVariable Long id,
            HttpServletRequest httpServletRequest,
            @Valid @RequestBody CommentRequestDto requestDto
    ){
        LoginResponseDto loginUser = (LoginResponseDto) httpServletRequest.getSession().getAttribute(Const.LOGIN_USER);
        Comment updatedComment = commentService.updateComment(scheduleId, id ,loginUser.getId(), requestDto);
        CommentResponseDto responseDto = CommentResponseDto.toDto(updatedComment);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(
            HttpServletRequest httpServletRequest,
            @PathVariable Long id
    ){
        LoginResponseDto loginUser = (LoginResponseDto) httpServletRequest.getSession().getAttribute(Const.LOGIN_USER);

        commentService.deleteComment(loginUser.getId(),id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
