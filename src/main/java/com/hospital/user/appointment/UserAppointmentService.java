package com.hospital.user.appointment;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.hospital.common.dto.DepartmentDTO;
import com.hospital.common.dto.DoctorDTO;
import com.hospital.common.dto.DoctorScheduleDTO;
import com.hospital.user.appointment.dto.UserAppointmentConfirmDTO;
import com.hospital.user.appointment.dto.UserAppointmentRequestDTO;
import com.hospital.user.appointment.dto.UserAppointmentShowDTO;

import lombok.NoArgsConstructor;

/**
 * 병원 진료 예약 업무를 구현하는 클래스
 */
@NoArgsConstructor
public class UserAppointmentService {

	UserAppointmentDAO uaDAO = UserAppointmentDAO.getInstance();

	/**
	 * 진료과를 찾는 일.
	 * 
	 * @return
	 */
	public List<DepartmentDTO> searchDepartmentList() {
		List<DepartmentDTO> deptList = null;

		try {
			deptList = uaDAO.selectDepartmentList();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return deptList;
	}

	/**
	 * 진료과에 속한 의료진들을 찾는 일.
	 * 
	 * @param deptNo
	 * @return
	 */
	public List<DoctorDTO> searchDoctorList(String deptNo) {
		List<DoctorDTO> doctorList = null;

		try {
			doctorList = uaDAO.selectDoctorList(deptNo);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return doctorList;
	}

	/**
	 * 의사의 일정을 찾는 일.
	 * 
	 * @param doctorLicenseNo
	 * @return
	 */
	public List<DoctorScheduleDTO> searchDoctorSchedule(int doctorLicenseNo) {
		List<DoctorScheduleDTO> dsList = null;

		try {
			dsList = uaDAO.selectDoctorSchedule(doctorLicenseNo);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dsList;
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
		List<String> availableTimes = new ArrayList<String>();
		List<String> reservedTimes = null;
		List<DoctorScheduleDTO> dsList = null;

		try {
			reservedTimes = uaDAO.selectReservedTime(doctorLicenseNo, appointmentDate);
			dsList = uaDAO.selectDoctorSchedule(doctorLicenseNo);

			DoctorScheduleDTO dsDTO = null;

			int appointDayOfWeek = appointmentDate.toLocalDate().getDayOfWeek().getValue();

			for (int i = 0; i < dsList.size(); i++) {
				dsDTO = dsList.get(i);

				if (dsDTO.getDayOfWeek() == appointDayOfWeek) {
					break;
				}
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
			// TODO Auto-generated catch block
			e.printStackTrace();
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

		try {
			int addCnt = uaDAO.selectAppointmentConflict(requestDTO);

			if (addCnt == 0) {
				reservable = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
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
		
		if (!checkReservable(requestDTO)) {
			// 예약 불가 안내 코드.
			return uacDTO;
		}
		
		try {
			uaDAO.insertAppointment(requestDTO);
			uacDTO = uaDAO.selectAppointmentConfirm(requestDTO);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return uacDTO;
	}
	
	/**
	 * 수정된 예약을 확정짓는 일.
	 * 
	 * @param requestDTO
	 * @return
	 */
	public UserAppointmentConfirmDTO reserveAppointment(String appointmentNo, String patientNo, UserAppointmentRequestDTO requestDTO) {
		UserAppointmentConfirmDTO uacDTO = null;
		
		if (!checkReservable(requestDTO)) {
			// 예약 불가 안내 코드.
			return uacDTO;
		}
		
		try {
			uaDAO.updateAppointment(appointmentNo, patientNo, requestDTO);
			uacDTO = uaDAO.selectAppointmentConfirm(requestDTO);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return uacDTO;
	}
	

	/**
	 * 예약 정보를 확인하는 일.
	 * 
	 * @param appointmentNo
	 * @return
	 */
	public UserAppointmentConfirmDTO searchAppointmentConfirm(String appointmentNo) {
		UserAppointmentConfirmDTO uacDTO = null;
		
		try {
			uacDTO = uaDAO.selectAppointmentConfirm(appointmentNo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return uacDTO;
	}
	
	public UserAppointmentConfirmDTO searchAppointmentConfirm(UserAppointmentRequestDTO requestDTO) {

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
		
		try {
			int cnt = uaDAO.updateCancelAppointment(appointmentNo, patientNo);
			if (cnt == 1) {
				cancelFlag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cancelFlag;
	}
	
	/**
	 * @param patientNo
	 * @return
	 */
	public List<UserAppointmentShowDTO> searchAppointmentDetail(String patientNo) {
		List<UserAppointmentShowDTO> uasDTOList = null;
		
		try {
			uasDTOList = uaDAO.selectAppointmentDetail(patientNo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return uasDTOList;
	}
}
