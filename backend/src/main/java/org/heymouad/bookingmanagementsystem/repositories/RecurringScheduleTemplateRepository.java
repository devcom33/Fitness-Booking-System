package org.heymouad.bookingmanagementsystem.repositories;

import org.heymouad.bookingmanagementsystem.entities.RecurringScheduleTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecurringScheduleTemplateRepository extends JpaRepository<RecurringScheduleTemplate, UUID> {
}
