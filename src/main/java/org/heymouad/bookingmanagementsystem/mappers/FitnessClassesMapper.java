package org.heymouad.bookingmanagementsystem.mappers;

import org.heymouad.bookingmanagementsystem.dtos.FitnessClassesDto;
import org.heymouad.bookingmanagementsystem.entities.FitnessClasses;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FitnessClassesMapper {
    FitnessClasses toFitnessClasses(FitnessClassesDto fitnessClassesDto);
    FitnessClassesDto toFitnessClassesDto(FitnessClasses fitnessClasses);
}
