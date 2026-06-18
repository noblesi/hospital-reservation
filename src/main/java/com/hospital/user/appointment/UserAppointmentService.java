package com.hospital.user.appointment;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import com.hospital.common.DepartmentDTO;
import com.hospital.common.DoctorDTO;
import com.hospital.common.DoctorScheduleDTO;

import lombok.NoArgsConstructor;

/**
 * 병원 진료 예약 업무를 구현하는 클래스
 */
@NoArgsConstructor
public class UserAppointmentService {
	
	UserAppointmentDAO uaDAO = UserAppointmentDAO.getInstance();
	
	public List<DepartmentDTO> searchDepartmentList() {
		List<DepartmentDTO> deptList = null;
		
		try {
			deptList = uaDAO.selectDepartmentList();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return deptList;
	}

	public List<DoctorDTO> searchDoctorList(String deptNo) {

		return null;
	}

	public UserAppointmentOptionDTO searchDoctorDetail(int doctorLicenseNo) {

		return null;
	}

	public List<DoctorScheduleDTO> searchDoctorSchedule(int doctorLicenseNo) {

		return null;
	}

	/**
	 * @param doctorLicenseNo
	 * @param appointmentDate
	 * @return 진료 가능한 시간대 목록
	 */
	public List searchAvailableTime(int doctorLicenseNo, Date appointmentDate) {

		return null;
	}

	public boolean checkReservable(UserAppointmentRequestDTO requestDTO) {

		return false;
	}

	public UserAppointmentConfirmDTO reserveAppointment(UserAppointmentRequestDTO requestDTO) {

		return null;
	}

	public UserAppointmentConfirmDTO searchAppointmentConfirm(String appointmentNo) {

		return null;
	}

	public boolean cancelAppointment(String appointmentNo, String patientNo) {

		return false;
	}
}
