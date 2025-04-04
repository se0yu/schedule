package com.example.schedule.comment.controller;


import com.example.schedule.comment.dto.CommentRequestDto;
import com.example.schedule.comment.dto.CommentResponseDto;
import com.example.schedule.comment.service.CommentService;
import com.example.schedule.common.Const;
import com.example.schedule.dto.LoginResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

        CommentResponseDto responseDto = commentService.updateComment(scheduleId, id ,loginUser.getId(), requestDto);

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
