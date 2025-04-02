package com.example.schedule.service;

import com.example.schedule.dto.LoginResponseDto;
import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.entity.User;
import com.example.schedule.repository.ScheduleRepository;
import com.example.schedule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
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
        User loginUser = userRepository.findById(userId).
                orElseThrow(() -> new RuntimeException("User not found"));

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
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"존재하지 않는 게시글 입니다."));

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

        List<ScheduleResponseDto> scheduleList = scheduleRepository.findAll().stream()
                .map(ScheduleResponseDto::toDto)
                .toList();
        return scheduleList;
    }


    //일정 수정
    @Override
    @Transactional
    public ScheduleResponseDto updateSchedule(Long id,ScheduleRequestDto requestDto, Long userId) {

        //id값에 해당하는 일정 데이터 가져오기
        Schedule savedSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 게시글입니다."));

        //createaAt이 update되지 않아 null로 출력되는 문제 발생
        //createdAt 변수에 따로 담아 dto에 넣어줌
        //추후에 수정 필요
        LocalDateTime createdAt = savedSchedule.getCreatedAt();

        //로그인한 유저의 데이터 가져오기
        User loginUser = userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found"));

        //로그인한 유저와 작성자가 동일하지 않을 경우 에러 출력
        if(!loginUser.getId().equals(savedSchedule.getUser().getId())){
            throw new RuntimeException("작성자가 아닙니다.");
        }
        Schedule schedule = new Schedule(id,requestDto.getTitle(),requestDto.getContents(), loginUser);
        Schedule updatedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(updatedSchedule.getId(),
                updatedSchedule.getTitle(),
                updatedSchedule.getContents(),
                updatedSchedule.getUser().getUsername(),
                createdAt,
                updatedSchedule.getUpdatedAt()

        );
    }

    @Override
    @Transactional
    public void deleteSchedule(LoginResponseDto loginUser, Long id) {

        //id값에 해당하는 일정 데이터 가져오기
        Schedule savedSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 게시글입니다."));

        //로그인한 유저와 작성자가 동일하지 않을 경우 에러 출력
        if(!loginUser.getId().equals(savedSchedule.getUser().getId())){
            throw new RuntimeException("작성자가 아닙니다.");
        }
        scheduleRepository.deleteById(id);
    }
}
