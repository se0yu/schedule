package com.example.schedule.domain.schedule.service;

import com.example.schedule.domain.user.dto.LoginResponseDto;
import com.example.schedule.domain.schedule.dto.ScheduleRequestDto;
import com.example.schedule.domain.schedule.dto.ScheduleResponseDto;
import com.example.schedule.domain.schedule.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ScheduleService {

    ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto, Long userId);

    Page<ScheduleResponseDto> findAllSchedules(Pageable pageable);

    ScheduleResponseDto findScheduleById(Long id);

    Schedule updateSchedule(Long id, ScheduleRequestDto requestDto, Long userId);

    void deleteSchedule(LoginResponseDto loginUser, Long id);
}
