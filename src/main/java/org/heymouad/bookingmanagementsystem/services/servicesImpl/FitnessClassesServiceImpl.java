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
    private final FitnessClassesMapper fitnessClassesMapper;

    /**
     * Persists a new Fitness Class entity based on the provided DTO
     * * @param fitnessClassesDto The DTO containing the details of the fitnessclass to be created
     * @return The DTO representation of the newly created and saved FitnessClass
     */
    @Override
    public FitnessClassesDto createFitnessClasses(FitnessClassesDto fitnessClassesDto) {
        FitnessClasses fitnessClasses = fitnessClassesMapper.toFitnessClasses(fitnessClassesDto);
        FitnessClasses savedEntity = fitnessClassesRepository.save(fitnessClasses);

        return fitnessClassesMapper.toFitnessClassesDto(savedEntity);
    }

    /**
     * Updates an existing FitnessClass entity identified by its ID
     * * @param id The unique id of the FitnessClass to update
     * @param fitnessClassesDto The DTO containing the updated fields for the fitness class
     * @return The DTO of the updated FitnessClass
     * @throws ResourceNotFoundException If no Fitness Class is found with the given ID
     */
    @Override
    public FitnessClassesDto updateFitnessClasses(UUID id, FitnessClassesDto fitnessClassesDto) {
        FitnessClasses entityToUpdate = fitnessClassesRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Fitness Class", id));

        entityToUpdate.setName(fitnessClassesDto.name());
        entityToUpdate.setDescription(fitnessClassesDto.description());
        entityToUpdate.setCategory(fitnessClassesDto.category());
        entityToUpdate.setCapacity(fitnessClassesDto.capacity());
        entityToUpdate.setDurationMinutes(fitnessClassesDto.durationMinutes());

        fitnessClassesRepository.save(entityToUpdate);

        return fitnessClassesMapper.toFitnessClassesDto(entityToUpdate);
    }

    /**
     * Retrieves a single Fitness Class by its unique id
     * * @param id The ID of the Fitness Class to retrieve
     * @return The DTO of the requested Fitness Class
     * @throws ResourceNotFoundException If no Fitness Class is found with the given ID
     */
    @Transactional(readOnly = true)
    @Override
    public FitnessClassesDto getFitnessClassesById(UUID id) {
        FitnessClasses fitnessClasses = fitnessClassesRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Fitness Class", id));

        return fitnessClassesMapper.toFitnessClassesDto(fitnessClasses);
    }

    /**
     * Retrieves all Fitness Class entities from the database
     * * @return A list of DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public List<FitnessClassesDto> getAllFitnessClasses() {
        List<FitnessClasses> fitnessClassesList = fitnessClassesRepository.findAll();
        return fitnessClassesList
                .stream()
                .map(fitnessClassesMapper::toFitnessClassesDto)
                .toList();
    }

}
