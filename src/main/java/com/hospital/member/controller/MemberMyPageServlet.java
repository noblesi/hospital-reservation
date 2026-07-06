package com.hospital.member.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.common.MemberDTO;
import com.hospital.member.UserMyPageService;
import com.hospital.member.dto.UserAppointmentDTO;
import com.hospital.member.dto.UserMedicalRecordDTO;

public class MemberMyPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final UserMyPageService userMyPageService = new UserMyPageService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MemberDTO loginUser = (MemberDTO) request.getSession().getAttribute("loginUser");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath() + "/views/member/login.jsp");
			return;
		}// end if

		MemberDTO memberInfo = userMyPageService.searchMemberInfo(loginUser.getLoginId());
		String patientNo = "";

		if (memberInfo != null && memberInfo.getPatientNo() != null) {
			patientNo = memberInfo.getPatientNo();
		}// end if

		List<UserAppointmentDTO> appList = userMyPageService.searchAppointmentList(patientNo);
		List<UserAppointmentDTO> manageAppList = userMyPageService.searchManageAppointmentList(patientNo);
		List<UserMedicalRecordDTO> medicalList = userMyPageService.searchMedicalRecordList(patientNo);

		request.setAttribute("memberInfo", memberInfo);
		request.setAttribute("appointmentCount", appList.size());
		request.setAttribute("medicalCount", medicalList.size());
		request.setAttribute("appList", appList);
		request.setAttribute("manageAppList", manageAppList);
		request.setAttribute("medicalList", medicalList);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/member/myPage.jsp");
		dispatcher.forward(request, response);
	}// doGet
}// class
