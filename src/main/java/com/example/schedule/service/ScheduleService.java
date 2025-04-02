package com.example.schedule.service;

import com.example.schedule.dto.CreateScheduleRequestDto;
import com.example.schedule.dto.CreateScheduleResponseDto;
import com.example.schedule.dto.ScheduleResponseDto;

public interface ScheduleService {

    ScheduleResponseDto findScheduleById(Long id);

    CreateScheduleResponseDto saveSchedule(CreateScheduleRequestDto requestDto, Long userId);
}
