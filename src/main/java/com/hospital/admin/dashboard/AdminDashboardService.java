package com.hospital.admin.dashboard;

import java.sql.SQLException;
import java.util.List;

public class AdminDashboardService {
    private final AdminDashboardDAO adminDashboardDAO;

    public AdminDashboardService() {
        this(new AdminDashboardDAO());
    }

    public AdminDashboardService(AdminDashboardDAO adminDashboardDAO) {
        this.adminDashboardDAO = adminDashboardDAO;
    }

    public AdminDashboardSummaryDTO getDashboardSummary() throws SQLException {
        return adminDashboardDAO.selectDashboardSummary();
    }

    public List<AdminDashboardChartDTO> getMonthlyAppointmentStatus() throws SQLException {
        return adminDashboardDAO.selectMonthlyAppointmentStatus();
    }

    public List<AdminDashboardChartDTO> getWeekdayAppointmentStatus() throws SQLException {
        return adminDashboardDAO.selectWeekdayAppointmentStatus();
    }
}
