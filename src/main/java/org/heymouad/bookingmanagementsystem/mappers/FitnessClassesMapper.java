package org.heymouad.bookingmanagementsystem.mappers;

import org.heymouad.bookingmanagementsystem.dtos.FitnessClassesDto;
import org.heymouad.bookingmanagementsystem.entities.FitnessClass;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FitnessClassesMapper {
    FitnessClass toFitnessClasses(FitnessClassesDto fitnessClassesDto);
    FitnessClassesDto toFitnessClassesDto(FitnessClass fitnessClass);
}
