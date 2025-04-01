package com.example.schedule.service;

import com.example.schedule.dto.CreateScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService{

    private final ScheduleRepository scheduleRepository;
    @Override
    public CreateScheduleResponseDto saveSchedule(String title, String contents) {
        Schedule schedule = new Schedule(title,contents);
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new CreateScheduleResponseDto(savedSchedule.getId(),savedSchedule.getTitle(),savedSchedule.getContents());
    }
}
