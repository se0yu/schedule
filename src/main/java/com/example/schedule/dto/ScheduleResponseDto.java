package com.example.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {

    private final Long id;
    private final String title;
    private final String contents;
    private final String username;
}
