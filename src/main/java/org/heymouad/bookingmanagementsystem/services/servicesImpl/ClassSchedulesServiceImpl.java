package org.heymouad.bookingmanagementsystem.services.servicesImpl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.fortuna.ical4j.model.Recur;
import org.heymouad.bookingmanagementsystem.dtos.ClassSchedulesDto;
import org.heymouad.bookingmanagementsystem.dtos.RecurringScheduleTemplateDto;
import org.heymouad.bookingmanagementsystem.entities.ClassSchedules;
import org.heymouad.bookingmanagementsystem.entities.RecurringScheduleTemplate;
import org.heymouad.bookingmanagementsystem.exceptions.InstructorBusyException;
import org.heymouad.bookingmanagementsystem.exceptions.InvalidScheduleException;
import org.heymouad.bookingmanagementsystem.mappers.ClassSchedulesMapper;
import org.heymouad.bookingmanagementsystem.mappers.RecurringScheduleTemplateMapper;
import org.heymouad.bookingmanagementsystem.repositories.ClassSchedulesRepository;
import org.heymouad.bookingmanagementsystem.repositories.FitnessClassesRepository;
import org.heymouad.bookingmanagementsystem.repositories.InstructorRepository;
import org.heymouad.bookingmanagementsystem.repositories.RecurringScheduleTemplateRepository;
import org.heymouad.bookingmanagementsystem.services.ClassSchedulesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Transactional
@RequiredArgsConstructor
@Service
public class ClassSchedulesServiceImpl implements ClassSchedulesService {
    private final ClassSchedulesRepository classSchedulesRepository;
    private final ClassSchedulesMapper classSchedulesMapper;
    private final InstructorRepository instructorRepository;
    private final FitnessClassesRepository fitnessClassesRepository;
    private final RecurringScheduleTemplateRepository recurringScheduleTemplateRepository;
    private final RecurringScheduleTemplateMapper recurringScheduleTemplateMapper;

    /**
     * This Method Handles the creation of a new Class Schedule, managing both single, one-off classes
     * and long running recurring class series.
     * @param classSchedulesDto the DTO containing class details and optional recurrence rule.
     * @return A list of created schedules (one for a single class, many for a series)
     */
    @Override
    public List<ClassSchedulesDto> createClassSchedules(ClassSchedulesDto classSchedulesDto) {
        ZonedDateTime startTime = classSchedulesDto.startTime();
        ZonedDateTime endTime = classSchedulesDto.endTime();

        if (!startTime.isBefore(endTime))
        {
            throw new InvalidScheduleException("Start time must be before end time.");
        }

        UUID instructorId = classSchedulesDto.instructorDto().id();

        if (!instructorRepository.existsById(instructorId)) {
            throw new EntityNotFoundException("Instructor with ID " + instructorId + " not found.");
        }

        UUID classId = classSchedulesDto.fitnessClassesDto().id();
        if (!fitnessClassesRepository.existsById(classId)) {
            throw new EntityNotFoundException("Fitness Class with ID " + classId + " not found.");
        }

        if (classSchedulesDto.recurringTemplate().rrule().isBlank())
        {
            return saveOneOffSchedule(classSchedulesDto);
        }

        return saveRecurringSchedules(classSchedulesDto);
    }

    /**
     * Retrieves all existing class schedules from the database.
     * @return A list of all ClassSchedulesDto objects.
     */
    @Transactional(readOnly = true)
    @Override
    public List<ClassSchedulesDto> getAllClassSchedules() {
        return classSchedulesRepository
                .findAll()
                .stream()
                .map(classSchedulesMapper::toClassSchedulesDto).toList();
    }

    /**
     * Handles the persistence and validation for a single  class schedule.
     * @param classSchedulesDto The DTO containing the single schedule details.
     * @return A list containing the single created ClassSchedulesDto.
     */
    private List<ClassSchedulesDto> saveOneOffSchedule(ClassSchedulesDto classSchedulesDto) {
        ZonedDateTime startTime = classSchedulesDto.startTime();
        ZonedDateTime endTime = classSchedulesDto.endTime();
        UUID instructorId = classSchedulesDto.instructorDto().id();
        boolean overlap = classSchedulesRepository.existsClassSchedulesByInstructorIdAndOverlap(startTime, endTime, instructorId);

        if (overlap)
        {
            throw new InstructorBusyException("Instructor is already scheduled during this time slot.");
        }        ClassSchedules classSchedules = classSchedulesMapper.toClassSchedules(classSchedulesDto);
        return List.of(classSchedulesMapper.toClassSchedulesDto(classSchedulesRepository.save(classSchedules)));
    }

    /**
     * Handles the generation, validation, and persistence for an entire recurring schedule series.
     * @param classSchedulesDto The DTO containing the recurrence template and class details.
     * @return A list of all created ClassSchedulesDto instances in the series.
     * @throws InstructorBusyException If any generated instance conflicts with an existing schedule.
     */
    private List<ClassSchedulesDto> saveRecurringSchedules(ClassSchedulesDto classSchedulesDto)
    {
        RecurringScheduleTemplateDto template = classSchedulesDto.recurringTemplate();
        RecurringScheduleTemplate savedTemplate = recurringScheduleTemplateRepository.save(recurringScheduleTemplateMapper.toEntity(template));
        Recur recur = new Recur(template.rrule());
        ZonedDateTime periodStart = template.startDateTime();
        ZonedDateTime periodEnd = template.endDateTime();


        var dates = recur.getDates(periodStart, periodEnd);

        Duration duration = Duration.between(classSchedulesDto.startTime(), classSchedulesDto.endTime());

        List<ClassSchedules> classSchedulesDtoList = new ArrayList<>();

        for (var date : dates)
        {
            // Calculate the end time for this instance
            ZonedDateTime instantStart = (ZonedDateTime) date;
            ZonedDateTime instantEnd = instantStart.plus(duration);

            // Overlap check
            boolean overlap = classSchedulesRepository.existsClassSchedulesByInstructorIdAndOverlap(
                    instantStart,
                    instantEnd,
                    classSchedulesDto.instructorDto().id()
            );

            if (overlap)
            {
                throw new InstructorBusyException("Instructor is busy on a recurring date");
            }

            ClassSchedules classSchedule = classSchedulesMapper.toClassSchedules(classSchedulesDto);

            classSchedule.setStartTime(instantStart);
            classSchedule.setEndTime(instantEnd);
            classSchedule.setTemplate(savedTemplate);

            classSchedulesDtoList.add(classSchedule);
        }

        List<ClassSchedules> classSchedules = classSchedulesRepository.saveAll(classSchedulesDtoList);

        return classSchedules.stream()
                .map(classSchedulesMapper::toClassSchedulesDto)
                .toList();
    }

}