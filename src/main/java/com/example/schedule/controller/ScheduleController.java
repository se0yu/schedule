package com.example.schedule.controller;

import com.example.schedule.common.Const;
import com.example.schedule.dto.LoginResponseDto;
import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;


    //일정 생성 기능
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> saveSchedule (
            HttpServletRequest httpServletRequest,
            @Valid @RequestBody ScheduleRequestDto requestDto
            ){

        LoginResponseDto loginUser = (LoginResponseDto) httpServletRequest.getSession().getAttribute(Const.LOGIN_USER);

        ScheduleResponseDto responseDto= scheduleService.saveSchedule(requestDto,loginUser.getId());
            return new ResponseEntity<>(responseDto,HttpStatus.CREATED);
    }

    //일정 전체 조회
    @GetMapping
    public ResponseEntity<Page<ScheduleResponseDto>> findAllSchedules(
             @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        Pageable pageable = PageRequest.of(page, size, Sort.by("updatedAt").descending());
        Page<ScheduleResponseDto> responseDto = scheduleService.findAllSchedules(pageable);

        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }


    //일정 단일 조회
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long id){

        ScheduleResponseDto responseDto = scheduleService.findScheduleById(id);

        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    //일정 수정
    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> upadateSchedule(
            HttpServletRequest httpServletRequest,
            @PathVariable Long id,
            @Valid @RequestBody ScheduleRequestDto requestDto
    ){
        LoginResponseDto loginUser = (LoginResponseDto) httpServletRequest.getSession().getAttribute(Const.LOGIN_USER);
        Schedule updateSchedule = scheduleService.updateSchedule(id, requestDto, loginUser.getId());
        ScheduleResponseDto responseDto= ScheduleResponseDto.toDto(updateSchedule);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    //일정 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(
            HttpServletRequest httpServletRequest,
            @PathVariable Long id
            ){
        LoginResponseDto loginUser = (LoginResponseDto) httpServletRequest.getSession().getAttribute(Const.LOGIN_USER);

        scheduleService.deleteSchedule(loginUser,id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
