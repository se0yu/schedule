package com.example.schedule.comment.service;

import com.example.schedule.comment.dto.CommentRequestDto;
import com.example.schedule.comment.dto.CommentResponseDto;
import com.example.schedule.comment.entity.Comment;
import com.example.schedule.comment.repository.CommentRepository;
import com.example.schedule.entity.Schedule;
import com.example.schedule.entity.User;
import com.example.schedule.exception.CustomException;
import com.example.schedule.exception.ErrorCode;
import com.example.schedule.repository.ScheduleRepository;
import com.example.schedule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    @Override
    @Transactional
    public CommentResponseDto saveComment(Long scheduleId, Long loginUserId, CommentRequestDto requestDto) {

        //해당하는 게시글과 작성하는 유저 데이터 호출
        User loginUser = userRepository.findByIdOrElseThrow(loginUserId);
        Schedule savedSchedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        Comment comment = new Comment(requestDto.getContent(), loginUser,savedSchedule);
        Comment savedComment = commentRepository.save(comment);

        return new CommentResponseDto(savedComment.getId(),
                                    savedComment.getSchedule().getId(),
                                    savedComment.getUser().getUsername(),
                                    savedComment.getContent(),
                                    savedComment.getCreatedAt(),
                                    savedComment.getUpdatedAt()
        );
    }

    @Override
    public List<CommentResponseDto> findAllSchedules() {

        return commentRepository.findAll().stream().map(CommentResponseDto::toDto)
                .toList();
    }

    @Override
    @Transactional
    public CommentResponseDto updateComment(Long schedlueId,Long commentId,Long userId,CommentRequestDto requestDto) {

        Comment savedComment = commentRepository.findByIdOrElseThrow(commentId);

        //로그인한 유저와 작성자가 동일하지 않을 경우 에러 출력
        if(!userId.equals(savedComment.getUser().getId())){
            throw new CustomException(ErrorCode.MISMATCH_USER);
        }
        savedComment.updateComment(requestDto.getContent());
        return new CommentResponseDto(savedComment.getId(),
                                    savedComment.getSchedule().getId(),
                                    savedComment.getUser().getUsername(),
                                    savedComment.getContent(),
                                    savedComment.getCreatedAt(),
                                    savedComment.getUpdatedAt()
                );
    }

    @Override
    @Transactional
    public void deleteComment(Long loginUserId, Long commentId) {

        Comment savedComment = commentRepository.findByIdOrElseThrow(commentId);

        //로그인한 유저와 작성자가 동일하지 않을 경우 에러 출력
        if(!loginUserId.equals(savedComment.getUser().getId())){
            throw new CustomException(ErrorCode.MISMATCH_USER);
        }
        commentRepository.deleteById(commentId);
    }
}
