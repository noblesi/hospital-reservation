package com.hospital.reservation;

import java.sql.Date;
import java.util.List;

import com.hospital.common.DepartmentDTO;
import com.hospital.common.DoctorDTO;
import com.hospital.common.DoctorScheduleDTO;

/**
 * 병원 진료 예약 업무를 구현하는 클래스
 */
public class UserAppointmentService {

	public List<DepartmentDTO> searchDepartmentList() {

		return null;
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
