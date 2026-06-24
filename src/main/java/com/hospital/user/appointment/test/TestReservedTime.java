package com.hospital.user.appointment.test;



import java.sql.Date;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import com.hospital.user.appointment.UserAppointmentDAO;
import com.hospital.user.appointment.UserAppointmentService;



class TestReservedTime {

	@Test
	void test() {
		UserAppointmentDAO uaDAO = UserAppointmentDAO.getInstance();
		UserAppointmentService uas = new UserAppointmentService();
		
		try {
			Date date = new Date(126, 8, 11);
			System.out.println(uaDAO.selectReservedTime(123456, date));
			System.out.println(uas.searchAvailableTime(123456, date));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
