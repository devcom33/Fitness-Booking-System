package org.heymouad.bookingmanagementsystem.services;

import org.heymouad.bookingmanagementsystem.entities.ClassSchedules;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface ClassScheduleService {
    List<ClassSchedules> createClassSchedules(ClassSchedules classSchedules);
    ClassSchedules getClassScheduleById(UUID id);
    List<ClassSchedules> getAllClassSchedules();
    List<ClassSchedules> getMyClassSchedules(String userEmail);
}
