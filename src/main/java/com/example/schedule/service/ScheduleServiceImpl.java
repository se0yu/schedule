package com.example.schedule.service;

import com.example.schedule.dto.CreateScheduleRequestDto;
import com.example.schedule.dto.CreateScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.entity.User;
import com.example.schedule.repository.ScheduleRepository;
import com.example.schedule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService{

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Override
    public CreateScheduleResponseDto saveSchedule(CreateScheduleRequestDto requestDto, Long userId) {

        User loginUser = userRepository.findById(userId).
                orElseThrow(() -> new RuntimeException("User not found"));

        Schedule schedule = new Schedule(requestDto.getTitle(), requestDto.getContents(), loginUser);
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new CreateScheduleResponseDto(savedSchedule.getId(),
                                            savedSchedule.getTitle(),
                                            savedSchedule.getContents(),
                                            savedSchedule.getUser().getUsername());
    }
}
