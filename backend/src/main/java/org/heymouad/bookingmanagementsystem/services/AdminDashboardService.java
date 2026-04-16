package org.heymouad.bookingmanagementsystem.services;

import org.heymouad.bookingmanagementsystem.dtos.admin.BookingMonthlyCountDTO;
import org.heymouad.bookingmanagementsystem.dtos.admin.CategoriesStatsDTO;
import org.heymouad.bookingmanagementsystem.dtos.admin.DashboardStatsDTO;

import java.util.List;


public interface AdminDashboardService {
    DashboardStatsDTO getDashboardStats();
    List<BookingMonthlyCountDTO> getLastMonthsStats(int months);
    List<CategoriesStatsDTO> getCategoriesStats();
}
