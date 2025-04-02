package com.example.schedule.service;

import com.example.schedule.dto.CreateScheduleRequestDto;
import com.example.schedule.dto.CreateScheduleResponseDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.entity.User;
import com.example.schedule.repository.ScheduleRepository;
import com.example.schedule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService{


    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"존재하지 않는 게시글 입니다."));

        return new ScheduleResponseDto(schedule.getId(),
                                        schedule.getTitle(),
                                        schedule.getContents(),
                                        schedule.getUser().getUsername(),
                                        schedule.getCreatedAt(),
                                        schedule.getUpdatedAt()
                );
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules() {

        List<ScheduleResponseDto> scheduleList = scheduleRepository.findAll().stream()
                                                .map(ScheduleResponseDto::toDto)
                                                .toList();
        return scheduleList;
    }

    @Override
    public CreateScheduleResponseDto saveSchedule(CreateScheduleRequestDto requestDto, Long userId) {

        User loginUser = userRepository.findById(userId).
                orElseThrow(() -> new RuntimeException("User not found"));

        Schedule schedule = new Schedule(requestDto.getTitle(), requestDto.getContents(), loginUser);
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new CreateScheduleResponseDto(savedSchedule.getId(),
                                            savedSchedule.getTitle(),
                                            savedSchedule.getContents(),
                                            savedSchedule.getUser().getUsername(),
                                            savedSchedule.getCreatedAt(),
                                            savedSchedule.getUpdatedAt()

        );
    }
}
