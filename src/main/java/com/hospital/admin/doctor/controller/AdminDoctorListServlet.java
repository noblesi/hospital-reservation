package com.hospital.admin.doctor.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.admin.doctor.AdminDoctorService;
import com.hospital.admin.doctor.dto.AdminDoctorFormOptionDTO;

public class AdminDoctorListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final AdminDoctorService adminDoctorService = new AdminDoctorService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminDoctorFormOptionDTO formOptions = adminDoctorService.getDoctorFormOptions();

		request.setAttribute("deptList", formOptions.getDepartmentList());
		request.setAttribute("statusList", formOptions.getStatusList());
		request.setAttribute("positionList", formOptions.getPositionList());
		request.setAttribute("doctorList", adminDoctorService.searchDoctorList());
		request.setAttribute("adminMenu", "doctor");

		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/doctor/adminDoctorListView.jsp");
		dispatcher.forward(request, response);
	}//doGet
}//class
