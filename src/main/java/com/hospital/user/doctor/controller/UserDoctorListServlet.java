package com.hospital.user.doctor.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.common.dto.DepartmentDTO;
import com.hospital.common.dto.DoctorDTO;
import com.hospital.user.appointment.UserAppointmentService;

public class UserDoctorListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final UserAppointmentService userAppointmentService = new UserAppointmentService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String paramDeptNo = request.getParameter("deptNo");
		DepartmentDTO departmentDTO = null;
		List<DoctorDTO> list = userAppointmentService.searchDoctorList(paramDeptNo);
		try {
			departmentDTO = userAppointmentService.searchDepartment(paramDeptNo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("doctorList", list);
		request.setAttribute("departmentDTO", departmentDTO);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/user/doctor/doctorList.jsp");
		dispatcher.forward(request, response);
	}
}
