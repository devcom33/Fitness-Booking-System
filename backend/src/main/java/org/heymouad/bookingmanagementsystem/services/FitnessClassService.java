package org.heymouad.bookingmanagementsystem.services;

import org.heymouad.bookingmanagementsystem.entities.FitnessClass;

import java.util.List;
import java.util.UUID;


public interface FitnessClassService {
    FitnessClass createFitnessClasses(FitnessClass fitnessClass, String categoryName);
    FitnessClass updateFitnessClasses(UUID id, FitnessClass fitnessClass, String categoryName);
    FitnessClass getFitnessClassesById(UUID id);
    List<FitnessClass> getAllFitnessClasses();
}
