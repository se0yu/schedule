package com.example.schedule.domain.schedule.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.schedule.domain.schedule.dto.ScheduleSearchDto;
import com.example.schedule.domain.schedule.entity.Schedule;

public interface ScheduleSearch {

	Page<Schedule> searchSchedule(ScheduleSearchDto scheduleSearchDto, Pageable pageable);

}
