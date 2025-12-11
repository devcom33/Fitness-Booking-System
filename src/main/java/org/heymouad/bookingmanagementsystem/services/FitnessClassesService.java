package org.heymouad.bookingmanagementsystem.services;

import org.heymouad.bookingmanagementsystem.dtos.FitnessClassesDto;
import org.heymouad.bookingmanagementsystem.entities.FitnessClasses;

import java.util.List;
import java.util.UUID;


public interface FitnessClassesService {
    FitnessClasses createFitnessClasses(FitnessClasses fitnessClasses);
    FitnessClasses updateFitnessClasses(UUID id, FitnessClasses fitnessClasses);
    FitnessClasses getFitnessClassesById(UUID id);
    List<FitnessClasses> getAllFitnessClasses();
}
