package org.heymouad.bookingmanagementsystem.services;

import org.heymouad.bookingmanagementsystem.dtos.CategoryName;

import java.util.List;

public interface CategoryService {
    List<CategoryName> getCategories();
}
