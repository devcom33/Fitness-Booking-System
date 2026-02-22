package org.heymouad.bookingmanagementsystem.mappers;

import org.heymouad.bookingmanagementsystem.dtos.instructor.InstructorDto;
import org.heymouad.bookingmanagementsystem.entities.Instructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;



@Mapper(componentModel = "spring")
public interface InstructorMapper {

    @Mapping(target = "userID", source = "user.id")
    InstructorDto toDto(Instructor instructor);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "id", ignore = true)
    Instructor toEntity(InstructorDto instructorDto);
}

