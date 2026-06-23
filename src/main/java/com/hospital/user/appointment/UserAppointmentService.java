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
import com.hospital.user.appointment.dto.UserAppointmentOptionDTO;
import com.hospital.user.appointment.dto.UserAppointmentRequestDTO;

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
		List<DoctorDTO> doctorList = null;
		
		try {
			doctorList = uaDAO.selectDoctorList(deptNo);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return doctorList;
	}

	public UserAppointmentOptionDTO searchDoctorDetail(int doctorLicenseNo) {
		UserAppointmentOptionDTO optionDTO = null;

		try {
			optionDTO = uaDAO.selectDoctorDetail(doctorLicenseNo);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return optionDTO;
	}

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
			
			while(!timeLd.equals(endTime)) {
				if (!reservedTimes.contains(timeLd.toString())) {
					availableTimes.add(timeLd.toString());
				}
					
				timeLd = timeLd.plusMinutes(30);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return availableTimes;
	}

	public boolean checkReservable(UserAppointmentRequestDTO requestDTO) {
		if (!isValidRequest(requestDTO)) {
			return false;
		}

		try {
			return uaDAO.selectAppointmentConflict(requestDTO) == 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public UserAppointmentConfirmDTO reserveAppointment(UserAppointmentRequestDTO requestDTO) {
		if (!checkReservable(requestDTO)) {
			return null;
		}

		try {
			String appointmentNo = uaDAO.insertAppointment(requestDTO);
			if (appointmentNo == null || appointmentNo.isBlank()) {
				return null;
			}
			return uaDAO.selectAppointmentConfirm(appointmentNo);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public UserAppointmentConfirmDTO searchAppointmentConfirm(String appointmentNo) {
		if (appointmentNo == null || appointmentNo.isBlank()) {
			return null;
		}

		try {
			return uaDAO.selectAppointmentConfirm(appointmentNo.trim());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public boolean cancelAppointment(String appointmentNo, String patientNo) {
		if (appointmentNo == null || appointmentNo.isBlank() || patientNo == null || patientNo.isBlank()) {
			return false;
		}

		try {
			return uaDAO.updateCancelAppointment(appointmentNo.trim(), patientNo.trim()) > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	private boolean isValidRequest(UserAppointmentRequestDTO requestDTO) {
		return requestDTO != null
				&& requestDTO.getPatientNo() != null
				&& !requestDTO.getPatientNo().isBlank()
				&& requestDTO.getDoctorLicenseNo() > 0
				&& requestDTO.getAppointmentDate() != null
				&& requestDTO.getAppointmentTime() != null
				&& !requestDTO.getAppointmentTime().isBlank();
	}
}
