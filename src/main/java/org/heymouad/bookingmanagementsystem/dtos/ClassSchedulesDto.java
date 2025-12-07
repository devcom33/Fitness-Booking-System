package org.heymouad.bookingmanagementsystem.dtos;

import org.heymouad.bookingmanagementsystem.enums.DayOfWeek;

import java.time.ZonedDateTime;

public record ClassSchedulesDto(InstructorDto instructorDto,
                                FitnessClassesDto fitnessClassesDto,
                                ZonedDateTime startTime,
                                ZonedDateTime endTime,
                                RecurringScheduleTemplateDto recurringTemplate) {
}
