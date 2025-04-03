package com.example.schedule.controller;

import com.example.schedule.common.Const;
import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.LoginResponseDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedules(){

        List<ScheduleResponseDto> responseDto = scheduleService.findAllSchedules();

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

        ScheduleResponseDto responseDto= scheduleService.updateSchedule(id,requestDto,loginUser.getId());
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
