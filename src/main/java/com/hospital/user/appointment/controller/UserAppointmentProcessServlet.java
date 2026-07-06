package com.hospital.user.appointment.controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.user.appointment.UserAppointmentService;
import com.hospital.user.appointment.dto.UserAppointmentConfirmDTO;
import com.hospital.user.appointment.dto.UserAppointmentRequestDTO;

public class UserAppointmentProcessServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserAppointmentService userAppointmentService = new UserAppointmentService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String patientNo = UserAppointmentSessionUtil.getLoginPatientNo(request);
        
        // 사용자가 로그인 하지 않았으면 로그인 페이지로 이동시킨다.
        if (UserAppointmentSessionUtil.isBlank(patientNo)) {
            response.sendRedirect(request.getContextPath() + "/views/member/login.jsp");
            return;
        }
        
        Integer doctorLicenseNo = parseInt(request.getParameter("doctorLicenseNo"));
        Date appointmentDate = parseDate(request.getParameter("appointmentDate"));
        String appointmentTime = request.getParameter("appointmentTime");
        String requirement = request.getParameter("requirement");
        if (doctorLicenseNo == null || appointmentDate == null || UserAppointmentSessionUtil.isBlank(appointmentTime)
                || UserAppointmentSessionUtil.isBlank(requirement)) {
            request.getSession().setAttribute("errorMessage", "예약 정보를 다시 확인해주세요.");
            response.sendRedirect(request.getContextPath() + "/appointment/reserve.do");
            return;
        }

        String status = "예약대기";
        UserAppointmentRequestDTO uarDTO = new UserAppointmentRequestDTO(
            patientNo, doctorLicenseNo, appointmentDate, appointmentTime, requirement, status
        );

        String modifyApptNo = request.getParameter("appointmentNo");

        if (UserAppointmentSessionUtil.isBlank(modifyApptNo)) {
            UserAppointmentConfirmDTO uacDTO = userAppointmentService.reserveAppointment(uarDTO);
            if (uacDTO == null) {
                request.getSession().setAttribute("errorMessage", "이미 예약된 시간입니다. 다시 시도해주세요.");
                response.sendRedirect(request.getContextPath() + "/appointment/reserve.do");
                return;
            }

            response.sendRedirect(request.getContextPath() + "/appointment/success.do?apptNo=" + uacDTO.getAppointmentNo());
            return;
        }

        UserAppointmentConfirmDTO uacDTO = userAppointmentService.reserveAppointment(modifyApptNo, patientNo, uarDTO);
        if (uacDTO == null) {
            request.getSession().setAttribute("errorMessage", "이미 예약된 시간입니다. 다시 시도해주세요.");
            response.sendRedirect(request.getContextPath() + "/appointment/reserve.do");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/appointment/success.do?apptNo=" + uacDTO.getAppointmentNo());
    }

    private Integer parseInt(String value) {
        if (UserAppointmentSessionUtil.isBlank(value) || !value.matches("\\d+")) {
            return null;
        }

        return Integer.valueOf(value);
    }

    private Date parseDate(String value) {
        try {
            return UserAppointmentSessionUtil.isBlank(value) ? null : Date.valueOf(value);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
