package com.example.schedule.domain.schedule.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleSearchDto {

		private String keyword;
		private Boolean completed;
		private LocalDate startDate;
		private LocalDate endDate;
}
