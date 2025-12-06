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

    @Override
    public FitnessClassesDto createFitnessClasses(FitnessClassesDto fitnessClassesDto) {
        FitnessClasses fitnessClasses = fitnessClassesMapper.toFitnessClasses(fitnessClassesDto);
        FitnessClasses fitnessClasses_ = fitnessClassesRepository.save(fitnessClasses);

        return fitnessClassesMapper.toFitnessClassesDto(fitnessClasses_);
    }

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

    @Override
    public FitnessClassesDto getFitnessClassesById(UUID id) {
        FitnessClasses fitnessClasses = fitnessClassesRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Fitness Class Not Found with id: " + id));

        return fitnessClassesMapper.toFitnessClassesDto(fitnessClasses);
    }

    @Override
    public List<FitnessClassesDto> getAllFitnessClasses() {
        List<FitnessClasses> fitnessClassesList = fitnessClassesRepository.findAll();
        return fitnessClassesList
                .stream()
                .map(fitnessClassesMapper::toFitnessClassesDto)
                .toList();
    }

}
