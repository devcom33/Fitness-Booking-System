package org.heymouad.bookingmanagementsystem.repositories;

import org.heymouad.bookingmanagementsystem.dtos.CategoryName;
import org.heymouad.bookingmanagementsystem.entities.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends Repository<Category, UUID> {
    Optional<Category> findCategoryByName(String name);
    @Query("SELECT c.name FROM Category c")
    List<String> findAllNames();
}
