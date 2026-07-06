package com.hospital.admin.appointment.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.admin.appointment.AdminAppointmentService;

public class AdminAppointmentStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final AdminAppointmentService adminAppointmentService = new AdminAppointmentService();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String appointmentNo = request.getParameter("appointmentNo");
		String status = resolveStatus(request.getParameter("action"), request.getParameter("status"));

		if(appointmentNo == null || appointmentNo.isBlank() || status == null) {
			request.getSession().setAttribute("errorMessage", "예약 상태 변경 요청이 올바르지 않습니다.");
			response.sendRedirect(request.getContextPath() + "/admin/reservation/list.do");
			return;
		}// end if

		boolean success = adminAppointmentService.changeAppointmentStatus(appointmentNo.trim(), status);
		if(success) {
			request.getSession().setAttribute("message", "예약 상태를 변경했습니다.");
		} else {
			request.getSession().setAttribute("errorMessage", "예약 상태를 변경하지 못했습니다.");
		}// end if else

		response.sendRedirect(request.getContextPath() + "/admin/reservation/list.do");
	}//doPost

	private String resolveStatus(String action, String status) {
		if("approve".equals(action)) {
			return "예약완료";
		}// end if

		if("cancel".equals(action)) {
			return "예약취소";
		}// end if

		if("예약대기".equals(status) || "예약완료".equals(status) || "예약취소".equals(status)) {
			return status;
		}// end if

		return null;
	}//resolveStatus
}//class
