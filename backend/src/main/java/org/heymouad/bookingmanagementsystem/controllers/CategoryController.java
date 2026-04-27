package org.heymouad.bookingmanagementsystem.controllers;


import lombok.RequiredArgsConstructor;
import org.heymouad.bookingmanagementsystem.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(path = "/api/v1/categories", produces = "application/json")
@PreAuthorize("hasRole('INSTRUCTOR')")
@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<String>> getCategories() {
        return ResponseEntity.ok(categoryService.getCategories());
    }
}
