package org.heymouad.bookingmanagementsystem.dtos.admin;

import lombok.Builder;

@Builder
public record DashboardStatsDTO(
        long totalInstructors,
        long totalClients,
        long totalConfirmedBookings,
        long totalScheduledSessions) {
}