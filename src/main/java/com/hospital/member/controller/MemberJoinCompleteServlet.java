package com.hospital.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.common.MemberDTO;
import com.hospital.member.MemberRegisterService;

public class MemberJoinCompleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final MemberRegisterService memberRegisterService = new MemberRegisterService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String registerLoginId = (String) request.getSession().getAttribute("registerLoginId");

		if (registerLoginId == null || registerLoginId.trim().isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/member/join.do");
			return;
		}

		MemberDTO member = memberRegisterService.searchRegister(registerLoginId);
		request.setAttribute("member", member);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/member/joinComplete.jsp");
		dispatcher.forward(request, response);
	}
}
