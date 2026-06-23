package com.hospital.user.appointment.test;


import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.hospital.user.appointment.UserAppointmentDAO;
import com.hospital.user.appointment.UserAppointmentService;


class TestDbCon {

	@Test
	void test() {
		LocalDate ld =  LocalDate.now();
		boolean leepYearFlag = ld.isLeapYear();
		
		for(int i = 1; i < ld.getMonth().length(leepYearFlag) + 1; i++) {
			System.out.println(i);
		}

	}
}
