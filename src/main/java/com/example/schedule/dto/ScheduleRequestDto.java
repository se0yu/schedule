package com.example.schedule.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleRequestDto {

    @Size(max=10, message = "제목은 10글자를 초과할 수 없습니다.")
    private final String title;

    private final String contents;


}
