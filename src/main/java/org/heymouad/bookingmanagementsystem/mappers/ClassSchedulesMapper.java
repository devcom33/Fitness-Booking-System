package org.heymouad.bookingmanagementsystem.mappers;


import org.heymouad.bookingmanagementsystem.dtos.ClassSchedulesDto;
import org.heymouad.bookingmanagementsystem.entities.ClassSchedules;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClassSchedulesMapper {
    ClassSchedules toClassSchedules(ClassSchedulesDto classSchedulesDto);
    ClassSchedulesDto toClassSchedulesDto(ClassSchedules classSchedules);

}
