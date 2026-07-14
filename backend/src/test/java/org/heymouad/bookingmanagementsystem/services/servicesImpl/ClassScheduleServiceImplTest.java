package org.heymouad.bookingmanagementsystem.services.servicesImpl;


import org.heymouad.bookingmanagementsystem.entities.*;
import org.heymouad.bookingmanagementsystem.enums.UserRole;
import org.heymouad.bookingmanagementsystem.repositories.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ClassScheduleServiceImplTest {
    @Mock
    private ClassScheduleRepository classScheduleRepository;
    @Mock
    private InstructorRepository instructorRepository;
    @Mock
    private FitnessClassRepository fitnessClassRepository;
    @InjectMocks
    private ClassScheduleServiceImpl classScheduleService;
    @Mock
    private RecurringScheduleTemplateRepository recurringScheduleTemplateRepository;
    @Mock
    private BookingRepository bookingRepository;


    @Test
    void createClassSchedules_ShouldSaveAndReturnEntity()
    {
        // Given
        ZonedDateTime startTime = ZonedDateTime.now();
        ZonedDateTime endTime = startTime.plusHours(1);

        // ids
        UUID roleId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID instructorId = UUID.randomUUID();
        UUID classId = UUID.randomUUID();

        Role role = Role.builder()
                .id(roleId)
                .name(UserRole.CLIENT)
                .build();

        User user = User.builder()
                .id(userId)
                .email("email@example.com")
                .password("12345678")
                .role(role)
                .build();

        Category category = Category.builder()
                .name("HIIT")
                .build();

        Instructor instructor = Instructor.builder()
                .id(instructorId)
                .user(user)
                .bio("Not Yet")
                .build();

        FitnessClass fitnessClass = FitnessClass.builder()
                .id(classId)
                .name("class name 1")
                .capacity(20)
                .description("class description")
                .category(category)
                .durationMinutes(60)
                .build();

        ClassSchedules classSchedules = ClassSchedules.builder()
                .id(UUID.randomUUID())
                .instructor(instructor)
                .fitnessClass(fitnessClass)
                .startTime(startTime)
                .endTime(endTime)
                .template(null)
                .build();

        when(instructorRepository.existsById(instructorId)).thenReturn(true);
        when(fitnessClassRepository.existsById(classId)).thenReturn(true);
        when(classScheduleRepository.existsClassSchedulesByInstructorIdAndOverlap(startTime, endTime, instructorId))
                .thenReturn(false);
        when(classScheduleRepository.save(classSchedules)).thenReturn(classSchedules);
        List<ClassSchedules> results = classScheduleService.createClassSchedules(classSchedules);

        assertThat(results)
                .hasSize(1)
                .containsExactly(classSchedules);

        verify(classScheduleRepository).save(classSchedules);
    }


}
