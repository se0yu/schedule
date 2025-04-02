package com.example.schedule.service;

import com.example.schedule.dto.CreateScheduleRequestDto;
import com.example.schedule.dto.CreateScheduleResponseDto;
import com.example.schedule.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {

    CreateScheduleResponseDto saveSchedule(CreateScheduleRequestDto requestDto, Long userId);

    List<ScheduleResponseDto> findAllSchedules();

    ScheduleResponseDto findScheduleById(Long id);
}
