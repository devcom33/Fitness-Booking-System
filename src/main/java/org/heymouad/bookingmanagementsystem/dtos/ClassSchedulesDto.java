package org.heymouad.bookingmanagementsystem.dtos;


import java.time.ZonedDateTime;
import java.util.UUID;

public record ClassSchedulesDto(UUID id,
                                InstructorDto instructorDto,
                                FitnessClassesDto fitnessClassesDto,
                                ZonedDateTime startTime,
                                ZonedDateTime endTime,
                                RecurringScheduleTemplateDto recurringTemplate) {
}
