package org.heymouad.bookingmanagementsystem.services.servicesImpl;

import lombok.RequiredArgsConstructor;
import org.heymouad.bookingmanagementsystem.repositories.CategoryRepository;
import org.heymouad.bookingmanagementsystem.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<String> getCategories() {
        return categoryRepository.findAllNames();
    }
}
