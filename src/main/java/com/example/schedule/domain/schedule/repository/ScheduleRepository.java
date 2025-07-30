package com.example.schedule.domain.schedule.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.schedule.common.exception.CustomException;
import com.example.schedule.common.exception.ErrorCode;
import com.example.schedule.domain.schedule.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    default Schedule findByIdOrElseThrow(Long id){
        return findById(id)
                .orElseThrow(()->new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));
    }
    Page<Schedule> findAllById(Pageable pageable, Long userId);
}
