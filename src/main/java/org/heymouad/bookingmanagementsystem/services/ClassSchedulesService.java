package org.heymouad.bookingmanagementsystem.services;

import org.heymouad.bookingmanagementsystem.dtos.ClassSchedulesDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClassSchedulesService {
    List<ClassSchedulesDto> createClassSchedules(ClassSchedulesDto classSchedulesDto);
    List<ClassSchedulesDto> getAllClassSchedules();
}
