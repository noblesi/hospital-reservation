package com.hospital.user.appointment.test;


import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import com.hospital.user.appointment.UserAppointmentDAO;


class TestDbCon {

	@Test
	void test() {
		try {
			UserAppointmentDAO uaDAO = UserAppointmentDAO.getInstance();
			
			System.out.println(uaDAO.selectDepartmentList());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
