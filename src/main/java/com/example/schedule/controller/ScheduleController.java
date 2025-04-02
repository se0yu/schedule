package com.example.schedule.controller;

import com.example.schedule.common.Const;
import com.example.schedule.dto.CreateScheduleRequestDto;
import com.example.schedule.dto.CreateScheduleResponseDto;
import com.example.schedule.dto.LoginResponseDto;
import com.example.schedule.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;


    //일정 생성 기능
    @PostMapping
    public ResponseEntity<CreateScheduleResponseDto> saveSchedule (
            HttpServletRequest httpServletRequest,
            @RequestBody CreateScheduleRequestDto requestDto
            ){

        LoginResponseDto loginUser = (LoginResponseDto) httpServletRequest.getSession().getAttribute(Const.LOGIN_USER);

        if (loginUser == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        CreateScheduleResponseDto responseDto= scheduleService.saveSchedule(requestDto,loginUser.getId());
            return new ResponseEntity<>(responseDto,HttpStatus.CREATED);
    }
}
