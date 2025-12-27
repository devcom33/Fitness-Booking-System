package org.heymouad.bookingmanagementsystem.services.servicesImpl;

import lombok.RequiredArgsConstructor;
import org.heymouad.bookingmanagementsystem.entities.FitnessClass;
import org.heymouad.bookingmanagementsystem.exceptions.ResourceNotFoundException;
import org.heymouad.bookingmanagementsystem.repositories.FitnessClassRepository;
import org.heymouad.bookingmanagementsystem.services.FitnessClassService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional
@RequiredArgsConstructor
@Service
public class FitnessClassServiceImpl implements FitnessClassService {
    private final FitnessClassRepository fitnessClassRepository;

    /**
     * Create a new Fitness Class entity
     * @param fitnessClass The Entity containing the details of the fitnessclass to be created
     * @return The newly created and saved FitnessClasses Entity
     */
    @Override
    public FitnessClass createFitnessClasses(FitnessClass fitnessClass) {
        return fitnessClassRepository.save(fitnessClass);
    }

    /**
     * updates an existing FitnessClass entity identified by its ID
     * @param id The unique id of the FitnessClass to update
     * @param fitnessClass the Entity containing the updated fields for the fitness class
     * @return The updated FitnessClasses Entity
     * @throws ResourceNotFoundException If no Fitness Class is found with the given ID
     */
    @Override
    public FitnessClass updateFitnessClasses(UUID id, FitnessClass fitnessClass) {
        FitnessClass entityToUpdate = fitnessClassRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Fitness Class", id));

        entityToUpdate.setName(fitnessClass.getName());
        entityToUpdate.setDescription(fitnessClass.getDescription());
        entityToUpdate.setCategory(fitnessClass.getCategory());
        entityToUpdate.setCapacity(fitnessClass.getCapacity());
        entityToUpdate.setDurationMinutes(fitnessClass.getDurationMinutes());

        return fitnessClassRepository.save(entityToUpdate);
    }

    /**
     * Retrieves a single Fitness Class by its unique id
     * @param id The ID of the Fitness Class to retrieve
     * @return The requested FitnessClasses Entity
     * @throws ResourceNotFoundException If no Fitness Class is found with the given ID
     */
    @Transactional(readOnly = true)
    @Override
    public FitnessClass getFitnessClassesById(UUID id) {
        return fitnessClassRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Fitness Class", id));
    }

    /**
     * Retrieves all Fitness Class entities from the database
     * @return A list of all FitnessClasses Entities
     */
    @Transactional(readOnly = true)
    @Override
    public List<FitnessClass> getAllFitnessClasses() {
        return fitnessClassRepository.findAll();
    }
}
