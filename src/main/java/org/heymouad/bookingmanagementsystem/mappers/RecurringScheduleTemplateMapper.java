package org.heymouad.bookingmanagementsystem.mappers;

import org.heymouad.bookingmanagementsystem.dtos.RecurringScheduleTemplateDto;
import org.heymouad.bookingmanagementsystem.entities.RecurringScheduleTemplate;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface RecurringScheduleTemplateMapper {
    RecurringScheduleTemplate toEntity(RecurringScheduleTemplateDto recurringScheduleTemplateDto);
}
