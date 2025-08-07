package com.example.schedule.domain.schedule.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.schedule.domain.schedule.dto.ScheduleRequestDto;
import com.example.schedule.domain.schedule.dto.ScheduleResponseDto;
import com.example.schedule.domain.schedule.dto.ScheduleSearchDto;
import com.example.schedule.domain.schedule.entity.Schedule;
import com.example.schedule.domain.user.dto.LoginResponseDto;

public interface ScheduleService {

    ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto, Long userId);

    Page<ScheduleResponseDto> findAllSchedules(Pageable pageable, Long userId);

    ScheduleResponseDto findScheduleById(Long id, Long userId);

    Schedule updateSchedule(Long id, ScheduleRequestDto requestDto, Long userId);

    void deleteSchedule(LoginResponseDto loginUser, Long id);

    Page<ScheduleResponseDto> searchSchedules(Pageable pageable, ScheduleSearchDto scheduleSearchDto);
}
