package com.example.schedule.service;

import com.example.schedule.dto.LoginResponseDto;
import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.entity.User;
import com.example.schedule.exception.CustomException;
import com.example.schedule.exception.ErrorCode;
import com.example.schedule.repository.ScheduleRepository;
import com.example.schedule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Page<ScheduleResponseDto> findAllSchedules(Pageable pageable) {

        Page<Schedule> schedulePage = scheduleRepository.findAll(pageable);
        return schedulePage.map(ScheduleResponseDto::toDto);
    }


    //일정 수정
    @Override
    @Transactional
    public ScheduleResponseDto updateSchedule(Long id,ScheduleRequestDto requestDto, Long userId) {

        //id값에 해당하는 일정 데이터 가져오기
        Schedule savedSchedule = scheduleRepository.findByIdOrElseThrow(id);

        //로그인한 유저와 작성자가 동일하지 않을 경우 에러 출력
        if(!userId.equals(savedSchedule.getUser().getId())){
            throw new CustomException(ErrorCode.MISMATCH_USER);
        }

        savedSchedule.updateSchedule(requestDto.getTitle(), requestDto.getContents());
        scheduleRepository.flush();
        Schedule updatedSchedule = scheduleRepository.findByIdOrElseThrow(id);

        return ScheduleResponseDto.toDto(updatedSchedule);
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
