package org.heymouad.bookingmanagementsystem.mappers;


import org.heymouad.bookingmanagementsystem.dtos.ClassScheduleRequestDto;
import org.heymouad.bookingmanagementsystem.dtos.ClassScheduleResponseDto;
import org.heymouad.bookingmanagementsystem.entities.ClassSchedules;
import org.heymouad.bookingmanagementsystem.entities.FitnessClass;
import org.heymouad.bookingmanagementsystem.entities.Instructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring",
        uses = {
            FitnessClassesMapper.class,
                InstructorMapper.class,
                RecurringScheduleTemplateMapper.class
        })
public interface ClassScheduleMapper {

    @Mapping(target = "fitnessClass", source = "fitnessClassId")
    @Mapping(target = "instructor", source = "instructorId")
    @Mapping(target = "template", source = "templateDto")
    ClassSchedules toEntity(ClassScheduleRequestDto classScheduleRequestDto);

    @Mapping(target = "fitnessClassDto", source = "fitnessClass")
    @Mapping(target = "instructorDto", source = "instructor")
    @Mapping(target = "templateDto", source = "template")
    ClassScheduleResponseDto toResponseDto(ClassSchedules classSchedules);

    default FitnessClass mapFitnessClassId(UUID id){
        return id == null ? null : FitnessClass.builder().id(id).build();
    }

    default Instructor mapInstructorId(UUID id){
        return id == null ? null : Instructor.builder().id(id).build();
    }
}
