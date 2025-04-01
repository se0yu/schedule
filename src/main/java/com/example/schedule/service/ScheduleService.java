package com.example.schedule.service;

import com.example.schedule.dto.CreateScheduleResponseDto;

public interface ScheduleService {

    CreateScheduleResponseDto saveSchedule(String title, String contents);
}
