package org.heymouad.bookingmanagementsystem.dtos;


import java.time.ZonedDateTime;
import java.util.UUID;

public record ClassScheduleRequestDto(UUID fitnessClassId,
                                      UUID instructorId,
                                      ZonedDateTime startTime,
                                      ZonedDateTime endTime,
                                      RecurringScheduleTemplateDto templateDto) {
}
