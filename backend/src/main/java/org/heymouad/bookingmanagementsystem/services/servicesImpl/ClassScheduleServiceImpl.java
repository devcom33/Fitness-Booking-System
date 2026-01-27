package org.heymouad.bookingmanagementsystem.services.servicesImpl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.fortuna.ical4j.model.Recur;
import org.heymouad.bookingmanagementsystem.entities.*;
import org.heymouad.bookingmanagementsystem.enums.UserRole;
import org.heymouad.bookingmanagementsystem.exceptions.InstructorBusyException;
import org.heymouad.bookingmanagementsystem.exceptions.InvalidScheduleException;
import org.heymouad.bookingmanagementsystem.exceptions.ResourceNotFoundException;
import org.heymouad.bookingmanagementsystem.repositories.ClassScheduleRepository;
import org.heymouad.bookingmanagementsystem.repositories.FitnessClassRepository;
import org.heymouad.bookingmanagementsystem.repositories.InstructorRepository;
import org.heymouad.bookingmanagementsystem.repositories.RecurringScheduleTemplateRepository;
import org.heymouad.bookingmanagementsystem.services.ClassScheduleService;
import org.heymouad.bookingmanagementsystem.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class ClassScheduleServiceImpl implements ClassScheduleService {
    private final ClassScheduleRepository classScheduleRepository;
    private final InstructorRepository instructorRepository;
    private final FitnessClassRepository fitnessClassRepository;
    private final UserService userService;
    private final RecurringScheduleTemplateRepository recurringScheduleTemplateRepository;

    /**
     * Handles the creation of a new Class Schedule, managing both single, one-off classes
     * and long running recurring class series.
     * @param classSchedules the entity
     * @return A list of created entities
     */
    @Override
    public List<ClassSchedules> createClassSchedules(ClassSchedules classSchedules) {
        ZonedDateTime startTime = classSchedules.getStartTime();
        ZonedDateTime endTime = classSchedules.getEndTime();

        if (!startTime.isBefore(endTime))
        {
            throw new InvalidScheduleException("Start time must be before end time.");
        }

        UUID instructorId = classSchedules.getInstructor().getId();

        if (!instructorRepository.existsById(instructorId)) {
            throw new EntityNotFoundException("Instructor with ID " + instructorId + " not found.");
        }

        UUID classId = classSchedules.getFitnessClass().getId();
        if (!fitnessClassRepository.existsById(classId)) {
            throw new EntityNotFoundException("Fitness Class with ID " + classId + " not found.");
        }

        if (classSchedules.getTemplate().getRrule().isBlank())
        {
            return saveOneOffSchedule(classSchedules);
        }

        return saveRecurringSchedules(classSchedules);
    }

    /**
     * Retrieves a single Class Schedule by its unique identifier
     * @param id The ID of the Class Schedule to retrieve
     * @return The entity
     * @throws ResourceNotFoundException If the schedule is not found
     */
    @Transactional(readOnly = true)
    @Override
    public ClassSchedules getClassScheduleById(UUID id) {
        return classScheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class Schedule", id));
    }


    /**
     * Retrieves all existing class schedules from the database
     * @return A list of all entities
     */
    @Transactional(readOnly = true)
    @Override
    public List<ClassSchedules> getAllClassSchedules() {
        return classScheduleRepository.findAll();
    }


    /**
     * Handles the persistence and validation for a single class schedule
     * @param classSchedules The Entity containing the single schedule details
     * @return A list containing the single created entity
     * @throws InstructorBusyException If the instructor is already scheduled during this time slot
     */
    private List<ClassSchedules> saveOneOffSchedule(ClassSchedules classSchedules) {
        ZonedDateTime startTime = classSchedules.getStartTime();
        ZonedDateTime endTime = classSchedules.getEndTime();
        UUID instructorId = classSchedules.getInstructor().getId();

        boolean overlap = classScheduleRepository.existsClassSchedulesByInstructorIdAndOverlap(startTime, endTime, instructorId);

        if (overlap)
        {
            throw new InstructorBusyException("Instructor is already scheduled during this time slot.");
        }
        classSchedules.setTemplate(null);
        return List.of(classScheduleRepository.save(classSchedules));
    }

    /**
     * Handles the generation, validation, and persistence for an entire recurring schedule series.
     * @param classSchedules The initial ClassSchedules entity containing the recurrence template and class details
     * @return A list of all created entities in the series
     * @throws InstructorBusyException If any generated instance conflicts with an existing schedule
     */
    private List<ClassSchedules> saveRecurringSchedules(ClassSchedules classSchedules)
    {
        RecurringScheduleTemplate template = classSchedules.getTemplate();
        RecurringScheduleTemplate savedTemplate = recurringScheduleTemplateRepository.save(template);
        Recur recur = new Recur(template.getRrule());
        ZonedDateTime periodStart = template.getStartDateTime();
        ZonedDateTime periodEnd = template.getEndDateTime();

        var dates = recur.getDates(periodStart, periodEnd);

        Duration duration = Duration.between(classSchedules.getStartTime(), classSchedules.getEndTime());

        List<ClassSchedules> schedulesToSave = new ArrayList<>();

        FitnessClass fitnessClass = classSchedules.getFitnessClass();
        Instructor instructor = classSchedules.getInstructor();

        for (var date : dates)
        {
            // Calculate the end time for this instance
            ZonedDateTime instantStart = (ZonedDateTime) date;
            ZonedDateTime instantEnd = instantStart.plus(duration);

            // Overlap check
            boolean overlap = classScheduleRepository.existsClassSchedulesByInstructorIdAndOverlap(
                    instantStart,
                    instantEnd,
                    classSchedules.getInstructor().getId()
            );

            if (overlap)
            {
                throw new InstructorBusyException("Instructor is busy on a recurring date");
            }
            ClassSchedules classScheduleInstance = new ClassSchedules();

            classScheduleInstance.setFitnessClass(fitnessClass);
            classScheduleInstance.setInstructor(instructor);
            classScheduleInstance.setTemplate(savedTemplate);
            classScheduleInstance.setStartTime(instantStart);
            classScheduleInstance.setEndTime(instantEnd);

            schedulesToSave.add(classScheduleInstance);
        }

        return classScheduleRepository.saveAll(schedulesToSave);
    }


    /**
     * Retrieves my class schedules from the database
     * @return A list of all entities
     */
    @Transactional(readOnly = true)
    @Override
    public List<ClassSchedules> getMyClassSchedules(String userEmail) {
        return classScheduleRepository.findAllByUserEmail(userEmail);
    }
}