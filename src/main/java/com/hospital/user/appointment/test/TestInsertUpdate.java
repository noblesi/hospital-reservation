package com.hospital.user.appointment.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import com.hospital.user.appointment.UserAppointmentDAO;
import com.hospital.user.appointment.dto.UserAppointmentRequestDTO;

class TestInsertUpdate {

	@Test
	void test() {
		UserAppointmentDAO uaDAO = UserAppointmentDAO.getInstance();
		
		UserAppointmentRequestDTO uar = new UserAppointmentRequestDTO("P00000001", 123456, new Date(126, 7, 30), "10:30", "온 몸이 아픔", "승인 대기");
		
		try {
			System.out.println(uaDAO.selectAppointmentConflict(uar));
			System.out.println(uaDAO.selectAppointmentConfirm("A2606230021"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
