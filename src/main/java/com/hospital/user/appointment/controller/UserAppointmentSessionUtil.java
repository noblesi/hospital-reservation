package com.hospital.user.appointment.controller;

import javax.servlet.http.HttpServletRequest;

import com.hospital.common.MemberDTO;

final class UserAppointmentSessionUtil {

	private UserAppointmentSessionUtil() {
	}

	static String getLoginPatientNo(HttpServletRequest request) {
		MemberDTO loginUser = (MemberDTO) request.getSession().getAttribute("loginUser");
		return loginUser == null ? null : loginUser.getPatientNo();
	}

	static boolean isBlank(String value) {
		return value == null || value.isBlank();
	}
}
