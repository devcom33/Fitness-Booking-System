package org.heymouad.bookingmanagementsystem.dtos;


import lombok.Builder;
import org.heymouad.bookingmanagementsystem.dtos.instructor.InstructorDto;
import org.heymouad.bookingmanagementsystem.enums.ScheduleStatus;

import java.time.ZonedDateTime;
import java.util.UUID;

@Builder
public record ClassScheduleResponseDto(UUID id,
                                       FitnessClassesDto fitnessClassDto,
                                       InstructorDto instructorDto,
                                       ScheduleStatus status,
                                       ZonedDateTime startTime,
                                       ZonedDateTime endTime,
                                       RecurringScheduleTemplateDto templateDto) {
}
