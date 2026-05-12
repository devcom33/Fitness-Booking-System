package org.heymouad.bookingmanagementsystem.services.servicesImpl;


import org.heymouad.bookingmanagementsystem.entities.Category;
import org.heymouad.bookingmanagementsystem.entities.FitnessClass;
import org.heymouad.bookingmanagementsystem.exceptions.ResourceNotFoundException;
import org.heymouad.bookingmanagementsystem.repositories.CategoryRepository;
import org.heymouad.bookingmanagementsystem.repositories.FitnessClassRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FitnessClassServiceImplTest {
    @Mock
    private FitnessClassRepository fitnessClassRepository;

    @InjectMocks
    private FitnessClassServiceImpl fitnessClassService;

    @Mock
    private CategoryRepository categoryRepository;


    @Test
    void createFitnessClasses_shouldSaveAndReturnEntity() {
        //Given
        String categoryName = "HIIT";
        Category mockCategory = Category.builder().name(categoryName).build();

        FitnessClass fitnessClass = FitnessClass.builder()
                .name("Gym")
                .capacity(20)
                .durationMinutes(60)
                .build();

        when(categoryRepository.findCategoryByName(categoryName))
                .thenReturn(Optional.of(mockCategory));

        when(fitnessClassRepository.save(any(FitnessClass.class)))
                .thenReturn(fitnessClass);

        FitnessClass result = fitnessClassService.createFitnessClasses(fitnessClass, categoryName);

        assertNotNull(result);
        assertEquals("Gym", result.getName());

        verify(categoryRepository).findCategoryByName(categoryName);
        verify(fitnessClassRepository).save(any(FitnessClass.class));
    }

    @Test
    void updateFitnessClasses_shouldUpdateAndReturnEntity() {
        //Given
        String categoryName = "HIIT";
        String updatedCategoryName = "Cardio";

        Category mockUpdatedCategoryName = Category.builder().name(updatedCategoryName).build();

        UUID id = UUID.randomUUID();

        FitnessClass existingClass = FitnessClass.builder()
                .id(id)
                .name("GYM")
                .capacity(15)
                .durationMinutes(45)
                .build();

        FitnessClass updatedInfo = FitnessClass.builder()
                .name("Advanced Gym")
                .capacity(20)
                .durationMinutes(50)
                .build();

        when(fitnessClassRepository.findById(id)).thenReturn(Optional.of(existingClass));
        when(categoryRepository.findCategoryByName(updatedCategoryName))
                .thenReturn(Optional.of(mockUpdatedCategoryName));
        when(fitnessClassRepository.save(existingClass)).thenReturn(existingClass);

        FitnessClass updatedClass = fitnessClassService.updateFitnessClasses(id, updatedInfo, updatedCategoryName);

        assertNotNull(updatedClass);
        assertEquals("Advanced Gym", updatedClass.getName());
        assertEquals(20, updatedClass.getCapacity());
        assertEquals(updatedCategoryName, updatedClass.getCategory().getName());

        verify(categoryRepository).findCategoryByName(updatedCategoryName);
        verify(fitnessClassRepository).findById(id);
        verify(fitnessClassRepository).save(existingClass);
    }

    @Test
    void updateFitnessClasses_shouldThrowExceptionWhenNotFound(){
        UUID id = UUID.randomUUID();
        String updatedCategoryName = "Cardio";

        FitnessClass updatedInfo = FitnessClass.builder()
                .name("Advanced Gym")
                .capacity(20)
                .durationMinutes(50)
                .build();

        when(fitnessClassRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> fitnessClassService.updateFitnessClasses(id, updatedInfo, updatedCategoryName))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Fitness Class Not Found with id");

        verify(fitnessClassRepository, never()).save(any());
        verify(fitnessClassRepository).findById(id);
    }


    @Test
    public void getFitnessClassesById_shouldReturnEntity() {
        String categoryName = "HIIT";
        Category mockCategory = Category.builder().name(categoryName).build();

        UUID id = UUID.randomUUID();

        FitnessClass existing = FitnessClass.builder()
                .id(id)
                .name("Gym")
                .capacity(15)
                .durationMinutes(45)
                .category(mockCategory)
                .build();

        when(fitnessClassRepository.findById(id)).thenReturn(Optional.of(existing));

        FitnessClass fitnessClass = fitnessClassService.getFitnessClassesById(id);

        assertThat(fitnessClass)
                .isNotNull()
                .hasFieldOrPropertyWithValue("name", "Gym")
                .hasFieldOrPropertyWithValue("capacity", 15)
                .hasFieldOrPropertyWithValue("durationMinutes", 45)
                .extracting(FitnessClass::getCategory)
                .hasFieldOrPropertyWithValue("name", "HIIT");

        // verify
        verify(fitnessClassRepository).findById(id);
    }

    @Test
    public void getFitnessClassesById_shouldThrowExceptionWhenNotFound() {
        UUID id = UUID.randomUUID();

        when(fitnessClassRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> fitnessClassService.getFitnessClassesById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(id.toString());

        verify(fitnessClassRepository).findById(id);
    }


    @Test
    public void getAllFitnessClasses_shouldReturnAllEntities()
    {
        String categoryName = "HIIT";
        String categoryName_2 = "Cardio";

        Category mockCategoryName = Category.builder().name(categoryName).build();
        Category mockCategoryName_2 = Category.builder().name(categoryName_2).build();

        UUID id = UUID.randomUUID();

        FitnessClass class1 = FitnessClass.builder()
                .id(id)
                .name("Gym")
                .capacity(15)
                .durationMinutes(45)
                .category(mockCategoryName)
                .build();

        UUID id2 = UUID.randomUUID();

        FitnessClass class2 = FitnessClass.builder()
                .id(id2)
                .name("Advanced Gym")
                .capacity(20)
                .durationMinutes(50)
                .category(mockCategoryName_2)
                .build();

        when(fitnessClassRepository.findAll()).thenReturn(List.of(class1, class2));

        List<FitnessClass> fitnessClassList = fitnessClassService.getAllFitnessClasses();

        assertThat(fitnessClassList)
                .hasSize(2)
                .containsExactly(class1, class2)
                .doesNotContainNull();

        verify(fitnessClassRepository).findAll();
    }
}