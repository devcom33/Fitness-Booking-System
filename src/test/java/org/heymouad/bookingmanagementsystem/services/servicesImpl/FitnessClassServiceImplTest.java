package org.heymouad.bookingmanagementsystem.services.servicesImpl;


import org.heymouad.bookingmanagementsystem.entities.FitnessClass;
import org.heymouad.bookingmanagementsystem.exceptions.ResourceNotFoundException;
import org.heymouad.bookingmanagementsystem.repositories.FitnessClassRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FitnessClassServiceImplTest {
    @Mock
    private FitnessClassRepository fitnessClassRepository;

    @InjectMocks
    private FitnessClassServiceImpl fitnessClassService;


    @Test
    void createFitnessClasses_shouldSaveAndReturnEntity() {
        FitnessClass fitnessClass = FitnessClass.builder()
                .name("Yoga")
                .capacity(20)
                .durationMinutes(60)
                .category("Wellness")
                .build();

        when(fitnessClassRepository.save(fitnessClass))
                .thenReturn(fitnessClass);

        FitnessClass result = fitnessClassService.createFitnessClasses(fitnessClass);

        assertNotNull(result);
        assertEquals("Yoga", result.getName());
        verify(fitnessClassRepository).save(fitnessClass);
    }
    @Test
    void updateFitnessClasses_shouldUpdateAndReturnEntity() {
        UUID id = UUID.randomUUID();
        FitnessClass existingClass = FitnessClass.builder()
                .id(id)
                .name("Pilates")
                .capacity(15)
                .durationMinutes(45)
                .category("Fitness")
                .build();

        FitnessClass updatedInfo = FitnessClass.builder()
                .name("Advanced Pilates")
                .capacity(20)
                .durationMinutes(50)
                .category("Advanced Fitness")
                .build();

        when(fitnessClassRepository.findById(id)).thenReturn(Optional.of(existingClass));
        when(fitnessClassRepository.save(existingClass)).thenReturn(existingClass);

        FitnessClass updatedClass = fitnessClassService.updateFitnessClasses(id, updatedInfo);
        assertNotNull(updatedClass);
        assertEquals("Advanced Pilates", updatedClass.getName());
        assertEquals(20, updatedClass.getCapacity());
        verify(fitnessClassRepository).findById(id);
        verify(fitnessClassRepository).save(existingClass);
    }

    @Test
    void updateFitnessClasses_shouldThrowExceptionWhenNotFound(){
        UUID id = UUID.randomUUID();

        FitnessClass updatedInfo = FitnessClass.builder()
                .name("Advanced Pilates")
                .capacity(20)
                .durationMinutes(50)
                .category("Advanced Fitness")
                .build();

        when(fitnessClassRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> fitnessClassService.updateFitnessClasses(id, updatedInfo));

        verify(fitnessClassRepository).findById(id);

    }


    @Test
    public void getFitnessClassesById_shouldReturnEntity() {
        UUID id = UUID.randomUUID();

        FitnessClass existing = FitnessClass.builder()
                .id(id)
                .name("Pilates")
                .capacity(15)
                .durationMinutes(45)
                .category("Fitness")
                .build();

        when(fitnessClassRepository.findById(id)).thenReturn(Optional.of(existing));

        FitnessClass fitnessClass = fitnessClassService.getFitnessClassesById(id);
        assertNotNull(fitnessClass);
        assertEquals("Pilates", fitnessClass.getName());
        assertEquals(15, fitnessClass.getCapacity());
        assertEquals(45, fitnessClass.getDurationMinutes());
        assertEquals("Fitness", fitnessClass.getCategory());

        // verify - It ensures your service does its job properly by using the repository, not by cheating with hardcoded values
        verify(fitnessClassRepository).findById(id);
    }

    @Test
    public void getFitnessClassesById_shouldThrowExceptionWhenNotFound() {
        UUID id = UUID.randomUUID();

        when(fitnessClassRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> fitnessClassService.getFitnessClassesById(id));

        verify(fitnessClassRepository).findById(id);
    }


    @Test
    public void getAllFitnessClasses_shouldReturnAllEntities()
    {
        UUID id = UUID.randomUUID();

        FitnessClass class1 = FitnessClass.builder()
                .id(id)
                .name("Pilates")
                .capacity(15)
                .durationMinutes(45)
                .category("Fitness")
                .build();

        UUID id2 = UUID.randomUUID();

        FitnessClass class2 = FitnessClass.builder()
                .id(id2)
                .name("Advanced Pilates")
                .capacity(20)
                .durationMinutes(50)
                .category("Advanced Fitness")
                .build();

        when(fitnessClassRepository.findAll()).thenReturn(List.of(class1, class2));

        List<FitnessClass> fitnessClassList = fitnessClassService.getAllFitnessClasses();

        assertNotNull(fitnessClassList);
        assertEquals(2, fitnessClassList.size());
        assertEquals(class1, fitnessClassList.get(0));
        assertEquals(class2, fitnessClassList.get(1));

        verify(fitnessClassRepository).findAll();
    }
}