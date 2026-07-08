package com.hospital.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.common.MemberDTO;
import com.hospital.member.UserMyPageService;

public class MemberCancelAppointmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final UserMyPageService userMyPageService = new UserMyPageService();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MemberDTO loginUser = (MemberDTO) request.getSession().getAttribute("loginUser");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath() + "/views/member/login.jsp");
			return;
		}// end if

		String appointmentNo = request.getParameter("appointmentNo");

		if (appointmentNo == null || appointmentNo.trim().isEmpty()) {
			request.getSession().setAttribute("mypageMessage", "잘못된 예약 취소 요청입니다.");
			response.sendRedirect(request.getContextPath() + "/member/mypage.do");
			return;
		}// end if

		MemberDTO memberInfo = userMyPageService.searchMemberInfo(loginUser.getLoginId());
		boolean canceled = false;

		if (memberInfo != null && memberInfo.getPatientNo() != null) {
			canceled = userMyPageService.cancelAppointment(appointmentNo.trim(), memberInfo.getPatientNo());
		}// end if

		request.getSession().setAttribute("mypageMessage",
				canceled ? "예약이 취소되었습니다." : "예약을 취소하지 못했습니다. 예약 상태를 확인해주세요.");
		response.sendRedirect(request.getContextPath() + "/member/mypage.do");
	}// doPost

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/member/mypage.do");
	}// doGet
}// class
