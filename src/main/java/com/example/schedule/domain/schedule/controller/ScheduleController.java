package com.example.schedule.domain.schedule.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.schedule.common.Const;
import com.example.schedule.domain.schedule.dto.ScheduleRequestDto;
import com.example.schedule.domain.schedule.dto.ScheduleResponseDto;
import com.example.schedule.domain.schedule.dto.ScheduleSearchDto;
import com.example.schedule.domain.schedule.entity.Schedule;
import com.example.schedule.domain.schedule.service.ScheduleService;
import com.example.schedule.domain.user.dto.LoginResponseDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

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
            @RequestParam(defaultValue = "5") int size,
            HttpServletRequest httpServletRequest

    ){

        LoginResponseDto loginUser = (LoginResponseDto) httpServletRequest.getSession().getAttribute(Const.LOGIN_USER);
        Pageable pageable = PageRequest.of(page, size, Sort.by("updatedAt").descending());
        Page<ScheduleResponseDto> responseDto = scheduleService.findAllSchedules(pageable, loginUser.getId());

        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }


    //일정 단일 조회
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(
        @PathVariable Long id,
        HttpServletRequest httpServletRequest
    ){
        LoginResponseDto loginUser = (LoginResponseDto) httpServletRequest.getSession().getAttribute(Const.LOGIN_USER);
        ScheduleResponseDto responseDto = scheduleService.findScheduleById(id, loginUser.getId());

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

    @GetMapping("/search")
    public ResponseEntity<Page<ScheduleResponseDto>> searchSchedule(
        @ModelAttribute ScheduleSearchDto scheduleSearchDto,
        Pageable pageable
    ){
        scheduleService.searchSchedules(pageable, scheduleSearchDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
