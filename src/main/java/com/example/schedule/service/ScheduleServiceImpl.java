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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        return new ScheduleResponseDto(savedSchedule.getId(),
                                            savedSchedule.getTitle(),
                                            savedSchedule.getContents(),
                                            savedSchedule.getUser().getUsername(),
                                            savedSchedule.getCreatedAt(),
                                            savedSchedule.getUpdatedAt()

        );
    }

    //일정 단일 조회
    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);

        return new ScheduleResponseDto(schedule.getId(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getUser().getUsername(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt()
        );
    }

    //일정 전체 조회
    @Override
    public List<ScheduleResponseDto> findAllSchedules() {

        return scheduleRepository.findAll().stream().map(ScheduleResponseDto::toDto)
                .toList();
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


        return new ScheduleResponseDto(savedSchedule.getId(),
                savedSchedule.getTitle(),
                savedSchedule.getContents(),
                savedSchedule.getUser().getUsername(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getUpdatedAt()

        );
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
