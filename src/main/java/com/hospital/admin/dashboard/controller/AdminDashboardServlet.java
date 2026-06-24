package com.hospital.admin.dashboard.controller;

import com.hospital.admin.dashboard.AdminDashboardService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class AdminDashboardServlet extends HttpServlet {
    private final AdminDashboardService adminDashboardService = new AdminDashboardService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("dashboardSummary", adminDashboardService.getDashboardSummary());
            request.setAttribute("monthlyAppointmentStatus", adminDashboardService.getMonthlyAppointmentStatus());
            request.setAttribute("weekdayAppointmentStatus", adminDashboardService.getWeekdayAppointmentStatus());
            request.setAttribute("adminMenu", "dashboard");

            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/dashboard/index.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("관리자 대시보드를 조회하지 못했습니다.", e);
        }
    }
}
