package com.hospital.admin.appointment.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.admin.appointment.AdminAppointmentService;
import com.hospital.admin.appointment.dto.AdminAppointmentSearchDTO;

public class AdminAppointmentListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final AdminAppointmentService adminAppointmentService = new AdminAppointmentService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminAppointmentSearchDTO searchDTO = createSearchDTO(request);
		AdminAppointmentService.AdminAppointmentPage appointmentPage = adminAppointmentService.getAppointmentPage(searchDTO);

		request.setAttribute("appointmentList", appointmentPage.getAppointmentList());
		request.setAttribute("searchDTO", searchDTO);
		request.setAttribute("pagination", appointmentPage.getPagination());
		request.setAttribute("baseUrl", "/admin/reservation/list.do");
		request.setAttribute("paginationQueryString", buildPaginationQueryString(searchDTO));
		request.setAttribute("adminMenu", "reservation");

		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/appointment/adminAppointmentList.jsp");
		dispatcher.forward(request, response);
	}//doGet

	private AdminAppointmentSearchDTO createSearchDTO(HttpServletRequest request) {
		AdminAppointmentSearchDTO searchDTO = new AdminAppointmentSearchDTO();
		searchDTO.setSearchType(request.getParameter("searchType"));
		searchDTO.setSearchKeyword(request.getParameter("searchKeyword"));
		searchDTO.setStatus(request.getParameter("status"));
		searchDTO.setStartDate(request.getParameter("startDate"));
		searchDTO.setEndDate(request.getParameter("endDate"));
		searchDTO.setCurrentPage(parseInt(request.getParameter("currentPage"), 1));
		return searchDTO;
	}//createSearchDTO

	private String buildPaginationQueryString(AdminAppointmentSearchDTO searchDTO) {
		StringBuilder queryString = new StringBuilder();
		appendQueryParam(queryString, "searchType", searchDTO.getSearchType());
		appendQueryParam(queryString, "searchKeyword", searchDTO.getSearchKeyword());
		appendQueryParam(queryString, "status", searchDTO.getStatus());
		appendQueryParam(queryString, "startDate", searchDTO.getStartDate());
		appendQueryParam(queryString, "endDate", searchDTO.getEndDate());
		return queryString.toString();
	}//buildPaginationQueryString

	private void appendQueryParam(StringBuilder queryString, String name, String value) {
		if(value == null || value.isBlank()) {
			return;
		}// end if

		queryString.append('&')
				.append(name)
				.append('=')
				.append(URLEncoder.encode(value, StandardCharsets.UTF_8));
	}//appendQueryParam

	private int parseInt(String value, int defaultValue) {
		try {
			return value == null ? defaultValue : Integer.parseInt(value);
		} catch(NumberFormatException e) {
			return defaultValue;
		}// end try catch
	}//parseInt
}//class
