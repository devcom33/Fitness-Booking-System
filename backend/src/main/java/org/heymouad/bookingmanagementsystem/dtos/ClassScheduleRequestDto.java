package org.heymouad.bookingmanagementsystem.dtos;


import java.time.ZonedDateTime;
import java.util.UUID;

public record ClassScheduleRequestDto(UUID fitnessClassId,
                                      ZonedDateTime startTime,
                                      ZonedDateTime endTime,
                                      RecurringScheduleTemplateDto templateDto) {
}
