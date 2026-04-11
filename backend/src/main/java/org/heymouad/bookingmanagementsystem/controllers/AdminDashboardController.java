package org.heymouad.bookingmanagementsystem.controllers;


import lombok.RequiredArgsConstructor;
import org.heymouad.bookingmanagementsystem.dtos.admin.BookingMonthlyCountDTO;
import org.heymouad.bookingmanagementsystem.dtos.admin.DashboardStatsDTO;
import org.heymouad.bookingmanagementsystem.services.AdminDashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequestMapping(path = "/api/v1/dashboard", produces = "application/json")
@RestController
@RequiredArgsConstructor
public class AdminDashboardController {
    private final AdminDashboardService adminDashboardService;

    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDTO> getDashboardStats() {
        return ResponseEntity.ok(adminDashboardService.getDashboardStats());
    }

    @GetMapping("/last-months-stats")
    public ResponseEntity<List<BookingMonthlyCountDTO>> getLastMonthsStats() {
        return ResponseEntity.ok(adminDashboardService.getLastMonthsStats(7));
    }

}
