package org.heymouad.bookingmanagementsystem.services.servicesImpl;

import lombok.RequiredArgsConstructor;
import org.heymouad.bookingmanagementsystem.dtos.CategoryName;
import org.heymouad.bookingmanagementsystem.entities.Category;
import org.heymouad.bookingmanagementsystem.repositories.CategoryRepository;
import org.heymouad.bookingmanagementsystem.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryName> getCategories() {
        return categoryRepository.findAllNames();
    }
}
