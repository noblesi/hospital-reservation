package com.hospital.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.member.MemberRegisterService;

public class MemberIdCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final MemberRegisterService memberRegisterService = new MemberRegisterService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loginId = trimToNull(request.getParameter("id"));
		boolean checked = loginId != null;
		boolean validPattern = loginId != null && isValidLoginId(loginId);
		boolean idAvailable = false;

		if (validPattern) {
			idAvailable = !memberRegisterService.checkLoginIdDuplicate(loginId);
		}

		request.setAttribute("loginId", loginId);
		request.setAttribute("checked", checked);
		request.setAttribute("validPattern", validPattern);
		request.setAttribute("idAvailable", idAvailable);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/member/idDup.jsp");
		dispatcher.forward(request, response);
	}

	private boolean isValidLoginId(String loginId) {
		return loginId.matches("^[가-힣]{3,}$")
				|| loginId.matches("^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{6,12}$");
	}

	private String trimToNull(String value) {
		if (value == null || value.trim().isEmpty()) {
			return null;
		}

		return value.trim();
	}
}
