package com.example.schedule.domain.schedule.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.schedule.common.exception.CustomException;
import com.example.schedule.common.exception.ErrorCode;
import com.example.schedule.domain.schedule.dto.ScheduleRequestDto;
import com.example.schedule.domain.schedule.dto.ScheduleResponseDto;
import com.example.schedule.domain.schedule.entity.Schedule;
import com.example.schedule.domain.schedule.repository.ScheduleRepository;
import com.example.schedule.domain.user.dto.LoginResponseDto;
import com.example.schedule.domain.user.entity.User;
import com.example.schedule.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService{


    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;


    //일정 생성
    @Override
    @Transactional
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto, Long userId) {

        //로그인한 유저의 정보 가져오기
        User loginUser = userRepository.findByIdOrElseThrow(userId);

        Schedule schedule = new Schedule(requestDto.getTitle(), requestDto.getContents(), loginUser);
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return ScheduleResponseDto.toDto(savedSchedule);
    }

    //일정 단일 조회
    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        Schedule savedSchedule = scheduleRepository.findByIdOrElseThrow(id);

        return ScheduleResponseDto.toDto(savedSchedule);
    }

    //일정 전체 조회
    @Override
    public Page<ScheduleResponseDto> findAllSchedules(Pageable pageable, Long userId) {

        Page<Schedule> schedulePage = scheduleRepository.findAllById(pageable, userId);
        return schedulePage.map(ScheduleResponseDto::toDto);
    }


    //일정 수정
    @Override
    @Transactional
    public Schedule updateSchedule(Long id,ScheduleRequestDto requestDto, Long userId) {

        //id값에 해당하는 일정 데이터 가져오기
        Schedule savedSchedule = scheduleRepository.findByIdOrElseThrow(id);

        //로그인한 유저와 작성자가 동일하지 않을 경우 에러 출력
        if(!userId.equals(savedSchedule.getUser().getId())){
            throw new CustomException(ErrorCode.MISMATCH_USER);
        }

        savedSchedule.updateSchedule(requestDto.getTitle(), requestDto.getContents());

        return savedSchedule;
    }

    @Override
    @Transactional
    public void deleteSchedule(LoginResponseDto loginUser, Long id) {

        //id값에 해당하는 일정 데이터 가져오기
        Schedule savedSchedule = scheduleRepository.findByIdOrElseThrow(id);

        //로그인한 유저와 작성자가 동일하지 않을 경우 에러 출력
        if(!loginUser.getId().equals(savedSchedule.getUser().getId())){
            throw new CustomException(ErrorCode.MISMATCH_USER);
        }
        scheduleRepository.deleteById(id);
    }
}
