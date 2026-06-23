package com.hospital.admin.department.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.admin.department.AdminDepartmentService;
import com.hospital.admin.department.dto.AdminDepartmentSearchDTO;

public class AdminDepartmentListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final AdminDepartmentService adminDepartmentService = new AdminDepartmentService();

	/**
	 * 관리자 진료과 목록 요청을 처리하고 검색조건과 pagination 정보를 JSP로 전달한다.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminDepartmentSearchDTO searchDTO = createSearchDTO(request);
		AdminDepartmentService.AdminDepartmentPage departmentPage = adminDepartmentService.getDepartmentPage(searchDTO);

		request.setAttribute("departmentList", departmentPage.getDepartmentList());
		request.setAttribute("searchDTO", searchDTO);
		request.setAttribute("pagination", departmentPage.getPagination());
		request.setAttribute("baseUrl", "/admin/department/list.do");
		request.setAttribute("paginationQueryString", buildPaginationQueryString(searchDTO));
		request.setAttribute("adminMenu", "department");

		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/department/adminDepartmentList.jsp");
		dispatcher.forward(request, response);
	}//doGet

	private AdminDepartmentSearchDTO createSearchDTO(HttpServletRequest request) {
		AdminDepartmentSearchDTO searchDTO = new AdminDepartmentSearchDTO();
		searchDTO.setField(request.getParameter("field"));
		searchDTO.setKeyword(request.getParameter("keyword"));
		searchDTO.setIsActiveYn(request.getParameter("isActiveYn"));
		searchDTO.setCurrentPage(parseInt(request.getParameter("currentPage"), 1));
		return searchDTO;
	}//createSearchDTO

	/**
	 * pagination link에 유지할 진료과 검색조건 query string을 생성한다.
	 */
	private String buildPaginationQueryString(AdminDepartmentSearchDTO searchDTO) {
		StringBuilder queryString = new StringBuilder();
		appendQueryParam(queryString, "field", searchDTO.getField());
		appendQueryParam(queryString, "keyword", searchDTO.getKeyword());
		appendQueryParam(queryString, "isActiveYn", searchDTO.getIsActiveYn());
		return queryString.toString();
	}//buildPaginationQueryString

	/**
	 * 값이 있는 검색조건만 URL encoding해서 query string에 추가한다.
	 */
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
