package com.example.schedule.domain.schedule.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.example.schedule.domain.schedule.dto.ScheduleSearchDto;
import com.example.schedule.domain.schedule.entity.QSchedule;
import com.example.schedule.domain.schedule.entity.Schedule;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ScheduleSearchImpl implements ScheduleSearch {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<Schedule> searchSchedule(ScheduleSearchDto scheduleSearchDto, Pageable pageable) {
		QSchedule schedule = QSchedule.schedule;
		// 조건절
		BooleanBuilder where = new BooleanBuilder();
		if (scheduleSearchDto.getKeyword() != null) {
			where.and(schedule.title.containsIgnoreCase(scheduleSearchDto.getKeyword())
				.or(schedule.contents.containsIgnoreCase(scheduleSearchDto.getKeyword())));
		}
		if (scheduleSearchDto.getCompleted() != null) {
			where.and(schedule.completed.eq(scheduleSearchDto.getCompleted()));
		}

		List<Schedule> results = queryFactory
			.selectFrom(schedule)
			.where(where)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(schedule.createdAt.desc())
			.fetch();

		long total = queryFactory
			.select(schedule.count())
			.from(schedule)
			.where(where)
			.fetchOne();

		return new PageImpl<>(results, pageable, total);
	}
}
