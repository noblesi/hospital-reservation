package com.hospital.reservation;

import java.sql.Date;
import java.util.List;

import com.hospital.common.DepartmentDTO;
import com.hospital.common.DoctorDTO;
import com.hospital.common.DoctorScheduleDTO;

/**
 * 병원 진료 예약에 관한 DB 업무를 구현하는 클래스
 */
public class UserAppointmentDAO {
	static UserAppointmentDAO userAppointmentDAO;

	private UserAppointmentDAO() {
	}

	/**
	 * 싱글톤 패턴 디자인 구현 method.
	 * 
	 * @return
	 */
	public static UserAppointmentDAO getInstance() {
		if (userAppointmentDAO == null) {
			userAppointmentDAO = new UserAppointmentDAO();
		}

		return userAppointmentDAO;
	}

	public List<DepartmentDTO> selectDepartmentList() {

		return null;
	}

	public List<DoctorDTO> selectDoctorList(String deptNo) {

		return null;
	}

	public UserAppointmentOptionDTO selectDoctorDetail(int doctorLicenseNo) {

		return null;
	}

	public List<DoctorScheduleDTO> selectDoctorSchedule(int doctorLicenseNo) {

		return null;
	}

	/**
	 * @param doctorLicenseNo
	 * @param appointmentDate
	 * @return 선택한 의사와 날짜의 예약된 시간들을 반환.
	 */
	public List selectReservedTime(int doctorLicenseNo, Date appointmentDate) {

		return null;
	}

	public int selectAppointmentConflict(UserAppointmentRequestDTO requestDTO) {

		return 0;
	}

	public int insertAppointment(UserAppointmentRequestDTO requestDTO) {

		return 0;
	}

	public UserAppointmentConfirmDTO selectAppointmentConfirm(String appointmentNo) {

		return null;
	}

	public int updateCancelAppointment(String appointmentNo, String patientNo) {

		return 0;
	}

}
