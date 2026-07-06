package com.hospital.user.appointment.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.user.appointment.UserAppointmentService;

public class UserAppointmentCancelServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserAppointmentService userAppointmentService = new UserAppointmentService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String patientNo = UserAppointmentSessionUtil.getLoginPatientNo(request);
        if (UserAppointmentSessionUtil.isBlank(patientNo)) {
            response.sendRedirect(request.getContextPath() + "/views/member/login.jsp");
            return;
        }

        String appointmentNo = request.getParameter("appointmentNo");
        if (UserAppointmentSessionUtil.isBlank(appointmentNo)) {
            request.getSession().setAttribute("errorMessage", "취소할 예약 정보를 확인할 수 없습니다.");
            response.sendRedirect(request.getContextPath() + "/appointment/list.do");
            return;
        }

        boolean cancelFlag = userAppointmentService.cancelAppointment(appointmentNo, patientNo);
        request.getSession().setAttribute(cancelFlag ? "message" : "errorMessage",
                cancelFlag ? "예약이 취소되었습니다." : "예약 취소에 실패했습니다. 잠시 후 다시 시도해 주세요.");
        response.sendRedirect(request.getContextPath() + "/appointment/list.do");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
