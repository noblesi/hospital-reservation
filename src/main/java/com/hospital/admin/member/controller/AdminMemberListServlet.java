package com.hospital.admin.member.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.admin.member.AdminMemberService;
import com.hospital.admin.member.dto.AdminMemberSearchDTO;

public class AdminMemberListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final AdminMemberService adminMemberService = new AdminMemberService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminMemberSearchDTO searchDTO = createSearchDTO(request);
		AdminMemberService.AdminMemberPage memberPage = adminMemberService.getMemberPage(searchDTO);

		request.setAttribute("memberList", memberPage.getMemberList());
		request.setAttribute("searchDTO", searchDTO);
		request.setAttribute("pagination", memberPage.getPagination());
		request.setAttribute("baseUrl", "/admin/member/list.do");
		request.setAttribute("paginationQueryString", buildPaginationQueryString(searchDTO));
		request.setAttribute("adminMenu", "member");

		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/member/adminMemberList.jsp");
		dispatcher.forward(request, response);
	}//doGet

	private AdminMemberSearchDTO createSearchDTO(HttpServletRequest request) {
		AdminMemberSearchDTO searchDTO = new AdminMemberSearchDTO();
		searchDTO.setSearchType(request.getParameter("searchType"));
		searchDTO.setSearchKeyword(request.getParameter("searchKeyword"));
		searchDTO.setStatus(request.getParameter("status"));
		searchDTO.setCurrentPage(parseInt(request.getParameter("currentPage"), 1));
		return searchDTO;
	}//createSearchDTO

	private String buildPaginationQueryString(AdminMemberSearchDTO searchDTO) {
		StringBuilder queryString = new StringBuilder();
		appendQueryParam(queryString, "searchType", searchDTO.getSearchType());
		appendQueryParam(queryString, "searchKeyword", searchDTO.getSearchKeyword());
		appendQueryParam(queryString, "status", searchDTO.getStatus());
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
