package com.example.schedule.domain.schedule.repository;

import com.example.schedule.domain.schedule.entity.Schedule;
import com.example.schedule.exception.CustomException;
import com.example.schedule.exception.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    default Schedule findByIdOrElseThrow(Long id){
        return findById(id)
                .orElseThrow(()->new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));
    }
    Page<Schedule> findAll(Pageable pageable);
}
