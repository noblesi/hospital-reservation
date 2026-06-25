package com.hospital.admin.doctor.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.admin.doctor.AdminDoctorService;
import com.hospital.admin.doctor.dto.AdminDoctorFormDTO;
import com.hospital.admin.doctor.dto.AdminDoctorFormOptionDTO;

public class AdminDoctorFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final AdminDoctorService adminDoctorService = new AdminDoctorService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer doctorLicenseNo = parseInt(request.getParameter("doctorLicenseNo"));
		if (doctorLicenseNo != null) {
			AdminDoctorFormDTO formDTO = adminDoctorService.searchDoctorDetail(doctorLicenseNo);
			request.setAttribute("doctor", formDTO.getDoctorDTO());
			request.setAttribute("departmentList", formDTO.getDepartmentList());
			request.setAttribute("statusList", formDTO.getStatusList());
			request.setAttribute("positionList", formDTO.getPositionList());
			request.setAttribute("careerList", formDTO.getCareerList());
			request.setAttribute("scheduleList", formDTO.getScheduleList());
			request.setAttribute("educationList", formDTO.getEducationList());
		} else {
			AdminDoctorFormOptionDTO formOptions = adminDoctorService.getDoctorFormOptions();
			request.setAttribute("departmentList", formOptions.getDepartmentList());
			request.setAttribute("statusList", formOptions.getStatusList());
			request.setAttribute("positionList", formOptions.getPositionList());
		}

		request.setAttribute("adminMenu", "doctor");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/doctor/adminDoctorDetail.jsp");
		dispatcher.forward(request, response);
	}

	private Integer parseInt(String value) {
		if (value == null || value.isBlank() || !value.matches("\\d+")) {
			return null;
		}

		return Integer.valueOf(value);
	}
}
