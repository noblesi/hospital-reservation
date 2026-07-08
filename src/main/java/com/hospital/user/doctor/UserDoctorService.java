package com.hospital.user.doctor;

import java.sql.SQLException;

import com.hospital.user.doctor.dto.UserDoctorDTO;

public class UserDoctorService {
	public UserDoctorDTO searchDoctorDetail(int doctorLicenseNo) {
		UserDoctorDTO udDTO = null;
		
		UserDoctorDAO udDAO = UserDoctorDAO.getInstance();
		
		try {
			udDTO = udDAO.selectDoctorDetail(doctorLicenseNo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return udDTO;
	}
}
