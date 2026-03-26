package org.heymouad.bookingmanagementsystem.mappers;


import org.heymouad.bookingmanagementsystem.dtos.ClassScheduleRequestDto;
import org.heymouad.bookingmanagementsystem.dtos.ClassScheduleResponseDto;
import org.heymouad.bookingmanagementsystem.dtos.admin.AdminClassScheduleResponseDto;
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
    @Mapping(target = "template", source = "templateDto")
    ClassSchedules toEntity(ClassScheduleRequestDto classScheduleRequestDto);

    @Mapping(target = "fitnessClassDto", source = "fitnessClass")
    @Mapping(target = "instructorDto", source = "instructor")
    @Mapping(target = "templateDto", source = "template")
    ClassScheduleResponseDto toResponseDto(ClassSchedules classSchedules);


    @Mapping(target = "fitnessClassDto", source = "classSchedules.fitnessClass")
    @Mapping(target = "instructorName", source = "classSchedules.instructor.user.name")
    @Mapping(target = "templateDto", source = "classSchedules.template")
    @Mapping(target = "capacity", source = "classSchedules.fitnessClass.capacity")
    @Mapping(target = "bookedCount", source = "bookedCount")
    AdminClassScheduleResponseDto toAdminResponseDto(ClassSchedules classSchedules, int bookedCount);


    default FitnessClass mapFitnessClassId(UUID id){
        return id == null ? null : FitnessClass.builder().id(id).build();
    }

    default Instructor mapInstructorId(UUID id){
        return id == null ? null : Instructor.builder().id(id).build();
    }
}
