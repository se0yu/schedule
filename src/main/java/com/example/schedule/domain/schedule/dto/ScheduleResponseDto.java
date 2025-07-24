package com.example.schedule.domain.schedule.dto;

import com.example.schedule.domain.schedule.entity.Schedule;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {

    private final Long id;
    private final String title;
    private final String contents;
    private final String username;

    //TimeStamp -> yyyy-mm-dd 형식의 String으로 반환
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDateTime updatedAt;




    public static ScheduleResponseDto toDto(Schedule schedule) {
        return new ScheduleResponseDto(schedule.getId(),
                                        schedule.getTitle(),
                                        schedule.getContents(),
                                        schedule.getUser().getUsername(),
                                        schedule.getCreatedAt(),
                                        schedule.getUpdatedAt()
                );
    }

}
