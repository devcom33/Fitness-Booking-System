package org.heymouad.bookingmanagementsystem.dtos.admin;

import org.heymouad.bookingmanagementsystem.dtos.FitnessClassesDto;
import org.heymouad.bookingmanagementsystem.dtos.RecurringScheduleTemplateDto;
import org.heymouad.bookingmanagementsystem.enums.ScheduleStatus;

import java.time.ZonedDateTime;
import java.util.UUID;

public record AdminClassScheduleResponseDto(
        UUID id,
        FitnessClassesDto fitnessClassDto,
        String instructorName,
        ScheduleStatus status,
        ZonedDateTime startTime,
        ZonedDateTime endTime,
        RecurringScheduleTemplateDto templateDto,
        int capacity,
        int bookedCount
) {}