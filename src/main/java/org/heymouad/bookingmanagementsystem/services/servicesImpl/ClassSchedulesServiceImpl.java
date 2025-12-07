package org.heymouad.bookingmanagementsystem.services.servicesImpl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.heymouad.bookingmanagementsystem.dtos.ClassSchedulesDto;
import org.heymouad.bookingmanagementsystem.dtos.InstructorDto;
import org.heymouad.bookingmanagementsystem.entities.ClassSchedules;
import org.heymouad.bookingmanagementsystem.exceptions.InstructorBusyException;
import org.heymouad.bookingmanagementsystem.exceptions.InvalidScheduleException;
import org.heymouad.bookingmanagementsystem.mappers.ClassSchedulesMapper;
import org.heymouad.bookingmanagementsystem.repositories.ClassSchedulesRepository;
import org.heymouad.bookingmanagementsystem.repositories.FitnessClassesRepository;
import org.heymouad.bookingmanagementsystem.repositories.InstructorRepository;
import org.heymouad.bookingmanagementsystem.services.ClassSchedulesService;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class ClassSchedulesServiceImpl implements ClassSchedulesService {
    private final ClassSchedulesRepository classSchedulesRepository;
    private final ClassSchedulesMapper classSchedulesMapper;
    private final InstructorRepository instructorRepository;
    private final FitnessClassesRepository fitnessClassesRepository;

    @Override
    public ClassSchedulesDto createClassSchedules(ClassSchedulesDto classSchedulesDto) {
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

        boolean overlap = classSchedulesRepository.existsClassSchedulesByInstructorIdAndOverlap(startTime, endTime, instructorId);

        if (overlap)
        {
            throw new InstructorBusyException("Instructor is already scheduled during this time slot.");
        }

        ClassSchedules classSchedules = classSchedulesMapper.toClassSchedules(classSchedulesDto);
        ClassSchedules savedClassSchedules = classSchedulesRepository.save(classSchedules);

        return classSchedulesMapper.toClassSchedulesDto(savedClassSchedules);
    }
}
