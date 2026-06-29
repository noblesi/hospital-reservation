package com.hospital.admin.doctor.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.admin.doctor.AdminDoctorService;
import com.hospital.admin.doctor.dto.AdminDoctorFormOptionDTO;
import com.hospital.admin.doctor.dto.AdminDoctorSearchDTO;

public class AdminDoctorListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final AdminDoctorService adminDoctorService = new AdminDoctorService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminDoctorSearchDTO searchDTO = createSearchDTO(request);
		AdminDoctorFormOptionDTO formOptions = adminDoctorService.getDoctorFormOptions();

		request.setAttribute("deptList", formOptions.getDepartmentList());
		request.setAttribute("statusList", formOptions.getStatusList());
		request.setAttribute("positionList", formOptions.getPositionList());
		request.setAttribute("doctorList", adminDoctorService.searchDoctorList(searchDTO));
		request.setAttribute("searchDTO", searchDTO);
		request.setAttribute("baseUrl", "/admin/doctor/list.do");
		request.setAttribute("adminMenu", "doctor");

		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/doctor/adminDoctorListView.jsp");
		dispatcher.forward(request, response);
	}//doGet

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer doctorLicenseNo = parseInt(request.getParameter("doctorLicenseNo"));
		String statusCode = request.getParameter("statusCode");

		if(doctorLicenseNo == null || isBlank(statusCode)) {
			request.getSession().setAttribute("errorMessage", "의료진 상태 변경 요청이 올바르지 않습니다.");
			response.sendRedirect(request.getContextPath() + "/admin/doctor/list.do");
			return;
		}// end if

		boolean success = adminDoctorService.changeDoctorStatus(doctorLicenseNo, statusCode.trim());
		if(success) {
			request.getSession().setAttribute("message", "의료진 상태를 변경했습니다.");
		} else {
			request.getSession().setAttribute("errorMessage", "의료진 상태를 변경하지 못했습니다.");
		}// end if else

		response.sendRedirect(request.getContextPath() + "/admin/doctor/list.do");
	}//doPost

	private AdminDoctorSearchDTO createSearchDTO(HttpServletRequest request) {
		AdminDoctorSearchDTO searchDTO = new AdminDoctorSearchDTO();
		searchDTO.setDeptNo(trimToNull(request.getParameter("deptNo")));
		searchDTO.setName(trimToNull(request.getParameter("name")));
		searchDTO.setPositionCode(trimToNull(request.getParameter("positionCode")));
		searchDTO.setStatusCode(trimToNull(request.getParameter("statusCode")));
		return searchDTO;
	}//createSearchDTO

	private Integer parseInt(String value) {
		if(isBlank(value) || !value.matches("\\d+")) {
			return null;
		}// end if

		return Integer.valueOf(value);
	}//parseInt

	private String trimToNull(String value) {
		return isBlank(value) ? null : value.trim();
	}//trimToNull

	private boolean isBlank(String value) {
		return value == null || value.trim().isEmpty();
	}//isBlank
}//class
