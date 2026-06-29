package com.hospital.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 회원탈퇴 최종 확인 취소를 처리하는 Servlet Controller.
 *
 * 기존 cancelWithdrawalProcess.jsp에서 처리하던 탈퇴 비밀번호 확인 세션값 제거와
 * 회원탈퇴 화면 이동 처리를 Servlet으로 분리한다.
 */
public class MemberCancelWithdrawalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().removeAttribute("withdrawalPasswordVerified");
		response.sendRedirect(request.getContextPath() + "/member/withdraw.do");
	}//doPost

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}//doGet
}//class
