package org.heymouad.bookingmanagementsystem.dtos;

import org.heymouad.bookingmanagementsystem.enums.DayOfWeek;

import java.time.ZonedDateTime;

public record ClassSchedulesDto(InstructorDto instructorDto,
                                FitnessClassesDto fitnessClassesDto,
                                DayOfWeek dayOfWeek,
                                ZonedDateTime startTime,
                                ZonedDateTime endTime,
                                boolean recurring) {
}
