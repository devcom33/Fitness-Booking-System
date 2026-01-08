package org.heymouad.bookingmanagementsystem.dtos;

import java.time.ZonedDateTime;

public record RecurringScheduleTemplateDto (
        String rrule,
        ZonedDateTime startDateTime,
        ZonedDateTime endDateTime
){
}
