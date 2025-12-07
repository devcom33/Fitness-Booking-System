package org.heymouad.bookingmanagementsystem.dtos;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

public record RecurringScheduleTemplateDto (
        UUID id,
        String rrule,
        ZonedDateTime startDateTime,
        ZonedDateTime endDateTime
){
}
