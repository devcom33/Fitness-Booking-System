package org.heymouad.bookingmanagementsystem.mappers;

import org.heymouad.bookingmanagementsystem.dtos.FitnessClassesDto;
import org.heymouad.bookingmanagementsystem.dtos.FitnessClassesResponseDto;
import org.heymouad.bookingmanagementsystem.entities.FitnessClass;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FitnessClassesMapper {

    // category is resolved manually in the service layer via CategoryRepository
    @Mapping(target = "category", ignore = true)
    FitnessClass toFitnessClasses(FitnessClassesDto fitnessClassesDto);

    @Mapping(target = "category", source = "category.name")
    FitnessClassesDto toFitnessClassesDto(FitnessClass fitnessClass);

    @Mapping(target = "category", source = "category.name")
    FitnessClassesResponseDto toFitnessClassesResponseDto(FitnessClass fitnessClass);
}