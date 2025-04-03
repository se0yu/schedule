package com.example.schedule.comment.service;

import com.example.schedule.comment.dto.CommentRequsetDto;
import com.example.schedule.comment.dto.CommentResponseDto;
import com.example.schedule.comment.entity.Comment;
import com.example.schedule.comment.repository.CommentRepository;
import com.example.schedule.entity.Schedule;
import com.example.schedule.entity.User;
import com.example.schedule.repository.ScheduleRepository;
import com.example.schedule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    @Override
    @Transactional
    public CommentResponseDto saveComment(Long scheduleId, Long loginUserId, CommentRequsetDto requsetDto) {

        //해당하는 게시글과 작성하는 유저 데이터 호출
        User loginUser = userRepository.findByIdOrElseThrow(loginUserId);
        Schedule savedSchedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        Comment comment = new Comment(requsetDto.getContent(), loginUser,savedSchedule);
        Comment savedComment = commentRepository.save(comment);

        return new CommentResponseDto(savedComment.getId(),
                                    savedComment.getSchedule().getId(),
                                    savedComment.getUser().getUsername(),
                                    savedComment.getContent(),
                                    savedComment.getCreatedAt(),
                                    savedComment.getUpdatedAt()
        );
    }
}
