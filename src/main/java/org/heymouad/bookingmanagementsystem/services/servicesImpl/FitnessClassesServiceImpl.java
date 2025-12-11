package org.heymouad.bookingmanagementsystem.services.servicesImpl;

import lombok.RequiredArgsConstructor;
import org.heymouad.bookingmanagementsystem.dtos.FitnessClassesDto;
import org.heymouad.bookingmanagementsystem.entities.FitnessClasses;
import org.heymouad.bookingmanagementsystem.exceptions.ResourceNotFoundException;
import org.heymouad.bookingmanagementsystem.mappers.FitnessClassesMapper;
import org.heymouad.bookingmanagementsystem.repositories.FitnessClassesRepository;
import org.heymouad.bookingmanagementsystem.services.FitnessClassesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional
@RequiredArgsConstructor
@Service
public class FitnessClassesServiceImpl implements FitnessClassesService {
    private final FitnessClassesRepository fitnessClassesRepository;

    /**
     * Create a new Fitness Class entity
     * @param fitnessClass The Entity containing the details of the fitnessclass to be created
     * @return The newly created and saved FitnessClasses Entity
     */
    @Override
    public FitnessClasses createFitnessClasses(FitnessClasses fitnessClass) {
        return fitnessClassesRepository.save(fitnessClass);
    }

    /**
     * updates an existing FitnessClass entity identified by its ID
     * @param id The unique id of the FitnessClass to update
     * @param fitnessClass the Entity containing the updated fields for the fitness class
     * @return The updated FitnessClasses Entity
     * @throws ResourceNotFoundException If no Fitness Class is found with the given ID
     */
    @Override
    public FitnessClasses updateFitnessClasses(UUID id, FitnessClasses fitnessClass) {
        FitnessClasses entityToUpdate = fitnessClassesRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Fitness Class", id));

        entityToUpdate.setName(fitnessClass.getName());
        entityToUpdate.setDescription(fitnessClass.getDescription());
        entityToUpdate.setCategory(fitnessClass.getCategory());
        entityToUpdate.setCapacity(fitnessClass.getCapacity());
        entityToUpdate.setDurationMinutes(fitnessClass.getDurationMinutes());

        return fitnessClassesRepository.save(entityToUpdate);
    }

    /**
     * Retrieves a single Fitness Class by its unique id
     * @param id The ID of the Fitness Class to retrieve
     * @return The requested FitnessClasses Entity
     * @throws ResourceNotFoundException If no Fitness Class is found with the given ID
     */
    @Transactional(readOnly = true)
    @Override
    public FitnessClasses getFitnessClassesById(UUID id) {
        return fitnessClassesRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Fitness Class", id));
    }

    /**
     * Retrieves all Fitness Class entities from the database
     * @return A list of all FitnessClasses Entities
     */
    @Transactional(readOnly = true)
    @Override
    public List<FitnessClasses> getAllFitnessClasses() {
        return fitnessClassesRepository.findAll();
    }
}
