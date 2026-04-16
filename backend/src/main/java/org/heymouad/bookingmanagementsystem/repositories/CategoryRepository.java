package org.heymouad.bookingmanagementsystem.repositories;

import org.heymouad.bookingmanagementsystem.entities.Category;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends Repository<Category, UUID> {
    Optional<Category> findCategoryByName(String name);
}
