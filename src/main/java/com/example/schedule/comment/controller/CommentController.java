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

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    //댓글 전체 조회 기능
    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> findAllComments(){

        List<CommentResponseDto> responseDto = commentService.findAllSchedules();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
