package com.hospital.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.member.FindAccountService;

public class MemberResetPasswordProcessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final FindAccountService findAccountService = new FindAccountService();
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String loginId = (String)request.getSession().getAttribute("resetLoginId");
		
		String newPassword = request.getParameter("newPassword");
		String confirmPassword = request.getParameter("confirmPassword");
		
		if(loginId == null) {
			request.getSession().setAttribute("findPasswordMsg", "비정상적인 접근입니다.");
			response.sendRedirect(request.getContextPath() + "/views/member/findPassword.jsp");
			return;
		}//end if
		
		if(newPassword == null || "".equals(newPassword.trim())) {
			request.getSession().setAttribute("findPasswordMsg", "새 비밀번호를 입력해주세요,");
			response.sendRedirect(request.getContextPath() + "/views/member/findPassword.jsp?reset=Y");
			return;
		}//end if

		if(confirmPassword == null || "".equals(confirmPassword.trim())) {
			request.getSession().setAttribute("findPasswordMsg", "비밀번호 확인을 입력해주세요.");
			response.sendRedirect(request.getContextPath() + "/views/member/findPassword.jsp?reset=Y");
			return;
		}//end if

		if(!newPassword.equals(confirmPassword)) {
			request.getSession().setAttribute("findPasswordMsg", "비밀번호가 일치하지 않습니다.");
			response.sendRedirect(request.getContextPath() + "/views/member/findPassword.jsp?reset=Y");
			return;
		}//end if
		
		boolean updated = findAccountService.resetPassword(loginId, newPassword);
		
		if(updated) {
			request.getSession().removeAttribute("resetLoginId");
			request.getSession().setAttribute("loginMessage", "비밀번호가 변경되었습니다.");
			response.sendRedirect(request.getContextPath() + "/member/login.do");
			return;
		}//end if

		request.getSession().setAttribute("findPasswordMsg", "비밀번호 변경에 실패했습니다.");
		response.sendRedirect(request.getContextPath() + "/views/member/findPassword.jsp?reset=Y");
	}//doPost
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/views/member/findPassword.jsp");
	}//doGet
}//class
