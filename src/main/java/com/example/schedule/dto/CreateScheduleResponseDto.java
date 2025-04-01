package com.example.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateScheduleResponseDto {

    private final Long id;

    private final String title;

    private final String contents;


}
