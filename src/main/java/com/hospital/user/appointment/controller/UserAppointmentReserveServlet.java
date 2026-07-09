package com.hospital.user.appointment.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.user.appointment.UserAppointmentService;
import com.hospital.user.appointment.dto.UserAppointmentConfirmDTO;

public class UserAppointmentReserveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final UserAppointmentService userAppointmentService = new UserAppointmentService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("activeMenu", "guide");
		request.setAttribute("depth1", "진료안내");
		request.setAttribute("depth2", "인터넷 진료예약");

		String patientNo = UserAppointmentSessionUtil.getLoginPatientNo(request);

		// 사용자가 로그인 하지 않았으면 로그인 페이지로 이동시킨다.
		if (UserAppointmentSessionUtil.isBlank(patientNo)) {
			response.sendRedirect(request.getContextPath() + "/views/member/login.jsp");
			return;
		}

		String appointmentNo = request.getParameter("appointmentNo");
		if (!UserAppointmentSessionUtil.isBlank(appointmentNo)) {
			UserAppointmentConfirmDTO changeAppointment =
					userAppointmentService.searchChangeableAppointment(appointmentNo, patientNo);

			if (changeAppointment == null) {
				request.getSession().setAttribute("errorMessage", "변경 가능한 예약을 찾을 수 없습니다.");
				response.sendRedirect(request.getContextPath() + "/appointment/list.do");
				return;
			}

			request.setAttribute("changeAppointment", changeAppointment);
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/user/appointment/appointment.jsp");
		dispatcher.forward(request, response);
	}
}
