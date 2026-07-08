package com.hospital.user.appointment;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.hospital.common.dto.DepartmentDTO;
import com.hospital.common.dto.DoctorDTO;
import com.hospital.common.dto.DoctorScheduleDTO;
import com.hospital.common.util.GetKey;
import com.hospital.user.appointment.dto.UserAppointmentConfirmDTO;
import com.hospital.user.appointment.dto.UserAppointmentOptionDTO;
import com.hospital.user.appointment.dto.UserAppointmentRequestDTO;
import com.hospital.user.appointment.dto.UserAppointmentShowDTO;

import kr.co.sist.chipher.DataDecryption;
import lombok.NoArgsConstructor;

/**
 * 병원 진료 예약 업무를 구현하는 클래스
 */
@NoArgsConstructor
public class UserAppointmentService {

	private static final Logger LOGGER = Logger.getLogger(UserAppointmentService.class.getName());

	UserAppointmentDAO uaDAO = UserAppointmentDAO.getInstance();

	/**
	 * 진료과를 찾는 일.
	 * 
	 * @return
	 */
	public List<DepartmentDTO> searchDepartmentList() {
		try {
			return uaDAO.selectDepartmentList();
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "진료과 목록 조회 실패", e);
		}

		return Collections.emptyList();
	}
	
	public DepartmentDTO searchDepartment(String deptNo) throws SQLException {
		return uaDAO.selectDepartment(deptNo);
	}
	
	/**
	 * 진료과에 속한 의료진들을 찾는 일.
	 * 
	 * @param deptNo
	 * @return
	 */
	public List<DoctorDTO> searchDoctorList(String deptNo) {
		if (deptNo == null || "".equals(deptNo)) {
			return Collections.emptyList();
		}

		try {
			return uaDAO.selectDoctorList(deptNo);
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "진료과별 의료진 목록 조회 실패: " + deptNo, e);
		}

		return Collections.emptyList();
	}

	/**
	 * 의료진명 또는 세부전공 keyword로 의료진을 검색한다.
	 */
	public List<UserAppointmentOptionDTO> searchDoctorListByKeyword(String keyword) {
		if (keyword == null || keyword.isBlank()) {
			return Collections.emptyList();
		}

		try {
			return uaDAO.selectDoctorListByKeyword(keyword.trim());
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "의료진 keyword 검색 실패: " + keyword, e);
		}

		return Collections.emptyList();
	}

	public UserAppointmentOptionDTO searchDoctorDetail(int doctorLicenseNo) {
		UserAppointmentOptionDTO optionDTO = null;

		try {
			optionDTO = uaDAO.selectDoctorDetail(doctorLicenseNo);
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "의료진 상세 조회 실패: " + doctorLicenseNo, e);
		}

		return optionDTO;
	}

	/**
	 * 의사의 일정을 찾는 일.
	 * 
	 * @param doctorLicenseNo
	 * @return
	 */
	public List<DoctorScheduleDTO> searchDoctorSchedule(int doctorLicenseNo) {
		try {
			return uaDAO.selectDoctorSchedule(doctorLicenseNo);
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "의료진 일정 조회 실패: " + doctorLicenseNo, e);
		}

		return Collections.emptyList();
	}

	/**
	 * 해당 일자 의사의 진료 가능 시간 목록을 찾는 일.
	 * 
	 * @param doctorLicenseNo 진료 받고 싶은 의사 번호
	 * @param appointmentDate 진료 받고 싶은 날짜
	 * @return 진료 가능한 시간대 목록
	 * @throws SQLException
	 */
	public List<String> searchAvailableTime(int doctorLicenseNo, Date appointmentDate) {
		return searchAvailableTime(doctorLicenseNo, appointmentDate, null);
	}

	public List<String> searchAvailableTime(int doctorLicenseNo, Date appointmentDate, String excludeAppointmentNo) {
		List<String> availableTimes = new ArrayList<String>();
		List<String> reservedTimes = null;
		List<DoctorScheduleDTO> dsList = null;

		if (appointmentDate == null) {
			return availableTimes;
		}

		try {
			if (excludeAppointmentNo == null || excludeAppointmentNo.isBlank()) {
				reservedTimes = uaDAO.selectReservedTime(doctorLicenseNo, appointmentDate);
			} else {
				reservedTimes = uaDAO.selectReservedTime(doctorLicenseNo, appointmentDate, excludeAppointmentNo);
			}
			dsList = uaDAO.selectDoctorSchedule(doctorLicenseNo);

			DoctorScheduleDTO dsDTO = null;

			int appointDayOfWeek = appointmentDate.toLocalDate().getDayOfWeek().getValue();

			for(int i = 0; i < dsList.size(); i++) {
				DoctorScheduleDTO currentScheduleDTO = dsList.get(i);

				if (currentScheduleDTO.getDayOfWeek() == appointDayOfWeek) {
					dsDTO = currentScheduleDTO;
					break;
				}
			}

			if (dsDTO == null || dsDTO.getStartTime() == null || dsDTO.getEndTime() == null) {
				return availableTimes;
			}

			LocalTime startTime = LocalTime.parse(dsDTO.getStartTime());
			LocalTime endTime = LocalTime.parse(dsDTO.getEndTime());

			LocalTime timeLd = startTime;

			while (!timeLd.equals(endTime)) {
				if (!reservedTimes.contains(timeLd.toString())) {
					availableTimes.add(timeLd.toString());
				}

				timeLd = timeLd.plusMinutes(30);
			}

		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "진료 가능 시간 조회 실패", e);
		}

		return availableTimes;
	}

	/**
	 * 요청한 예약이 가능한지(다른 예약과 겹치거나 시간이 차지 않았는지) 확인하는 일.
	 * 
	 * @param requestDTO
	 * @return
	 */
	public boolean checkReservable(UserAppointmentRequestDTO requestDTO) {
		boolean reservable = false;

		if (requestDTO == null) {
			return reservable;
		}

		try {
			int addCnt = uaDAO.selectAppointmentConflict(requestDTO);

			if (addCnt == 0) {
				reservable = true;
			}

		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "예약 중복 확인 실패", e);
		}

		return reservable;
	}

	public boolean checkReservable(UserAppointmentRequestDTO requestDTO, String excludeAppointmentNo) {
		boolean reservable = false;

		if (requestDTO == null || excludeAppointmentNo == null || excludeAppointmentNo.isBlank()) {
			return reservable;
		}

		try {
			int addCnt = uaDAO.selectAppointmentConflict(requestDTO, excludeAppointmentNo);

			if (addCnt == 0) {
				reservable = true;
			}

		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "예약 변경 중복 확인 실패: " + excludeAppointmentNo, e);
		}

		return reservable;
	}

	/**
	 * 예약을 확정 짓는 일.
	 * 
	 * @param requestDTO
	 * @return
	 */
	public UserAppointmentConfirmDTO reserveAppointment(UserAppointmentRequestDTO requestDTO) {
		UserAppointmentConfirmDTO uacDTO = null;

		if (requestDTO == null) {
			return uacDTO;
		}

		// 중복된 시간이 있으면 예약을 진행하지 않는다.
		if (!checkReservable(requestDTO)) {

			return null;
		}

		try {
			uaDAO.insertAppointment(requestDTO);
			uacDTO = uaDAO.selectAppointmentConfirm(requestDTO);
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "예약 등록 실패", e);
		}

		return uacDTO;
	}

	/**
	 * 수정된 예약을 확정짓는 일.
	 * 
	 * @param requestDTO
	 * @return
	 */
	public UserAppointmentConfirmDTO reserveAppointment(String appointmentNo, String patientNo,
			UserAppointmentRequestDTO requestDTO) {
		UserAppointmentConfirmDTO uacDTO = null;

		if (appointmentNo == null || appointmentNo.isBlank()
				|| patientNo == null || patientNo.isBlank()
				|| requestDTO == null) {
			return uacDTO;
		}

		if (!checkReservable(requestDTO, appointmentNo)) {
			// 예약 불가 안내 코드.
			return uacDTO;
		}

		try {
			int updateCnt = uaDAO.updateAppointment(appointmentNo, patientNo, requestDTO);
			if (updateCnt == 1) {
				uacDTO = uaDAO.selectAppointmentConfirm(appointmentNo);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "예약 변경 실패: " + appointmentNo, e);
		}

		return uacDTO;
	}

	public UserAppointmentConfirmDTO searchChangeableAppointment(String appointmentNo, String patientNo) {
		if (appointmentNo == null || appointmentNo.isBlank()
				|| patientNo == null || patientNo.isBlank()) {
			return null;
		}

		try {
			return uaDAO.selectChangeableAppointment(appointmentNo, patientNo);
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "예약 변경 대상 조회 실패: " + appointmentNo, e);
		}

		return null;
	}

	/**
	 * 예약 정보를 확인하는 일.
	 * 
	 * @param appointmentNo
	 * @return
	 */
	public UserAppointmentConfirmDTO searchAppointmentConfirm(String appointmentNo) {
		UserAppointmentConfirmDTO uacDTO = null;

		if (appointmentNo == null || "".equals(appointmentNo)) {
			return uacDTO;
		}

		try {
			uacDTO = uaDAO.selectAppointmentConfirm(appointmentNo);
			
			DataDecryption dd = new DataDecryption(GetKey.getKey());
			
			uacDTO.setPhoneNumber(dd.decrypt(uacDTO.getPhoneNumber()));
			uacDTO.setEmail(dd.decrypt(uacDTO.getEmail()));
			
			return uacDTO;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "예약 확인 조회 실패: " + appointmentNo, e);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return uacDTO;
	}

	public UserAppointmentConfirmDTO searchAppointmentConfirm(UserAppointmentRequestDTO requestDTO) {

		if (requestDTO == null) {
			return null;
		}

		try {
			UserAppointmentConfirmDTO uacDTO = uaDAO.selectAppointmentConfirm(requestDTO);
			
			DataDecryption dd = new DataDecryption(GetKey.getKey());
			
			uacDTO.setPhoneNumber(dd.decrypt(uacDTO.getPhoneNumber()));
			uacDTO.setEmail(dd.decrypt(uacDTO.getEmail()));
			
			return uacDTO;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "예약 요청 정보 확인 조회 실패", e);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 예약을 취소하는 일.
	 * 
	 * @param appointmentNo
	 * @param patientNo
	 * @return
	 */
	public boolean cancelAppointment(String appointmentNo, String patientNo) {
		boolean cancelFlag = false;

		if (appointmentNo == null || "".equals(appointmentNo) || patientNo == null || "".equals(patientNo)) {
			return cancelFlag;
		}

		try {
			int cnt = uaDAO.updateCancelAppointment(appointmentNo, patientNo);
			if (cnt == 1) {
				cancelFlag = true;
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "예약 취소 실패: " + appointmentNo, e);
		}

		return cancelFlag;
	}

	/**
	 * 환자의 예약 목록 조회
	 * 
	 * @param patientNo
	 * @return
	 */
	public List<UserAppointmentShowDTO> searchAppointmentDetail(String patientNo) {
		if (patientNo == null || "".equals(patientNo)) {
			return Collections.emptyList();
		}

		try {
			return uaDAO.selectAppointmentDetail(patientNo);
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "환자 예약 목록 조회 실패: " + patientNo, e);
		}

		return Collections.emptyList();
	}
}
