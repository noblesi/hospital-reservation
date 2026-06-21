package com.hospital.user.appointment;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hospital.common.DBConnection;
import com.hospital.common.dto.DepartmentDTO;
import com.hospital.common.dto.DoctorDTO;
import com.hospital.common.dto.DoctorScheduleDTO;
import com.hospital.user.appointment.dto.UserAppointmentConfirmDTO;
import com.hospital.user.appointment.dto.UserAppointmentOptionDTO;
import com.hospital.user.appointment.dto.UserAppointmentRequestDTO;

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

	public List<DepartmentDTO> selectDepartmentList() throws SQLException {
		List<DepartmentDTO> deptList = new ArrayList<>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBConnection.getConnection();

			String query = "select dept_no, dept_name, description, is_active_yn, dept_loc from department";
			pstmt = con.prepareStatement(query);

			rs = pstmt.executeQuery();

			DepartmentDTO deptDTO = null;

			if (rs != null) {
				while (rs.next()) {
					deptDTO = new DepartmentDTO();

					deptDTO.setDeptNo(rs.getString("dept_no"));
					deptDTO.setDeptName(rs.getString("dept_name"));
					deptDTO.setDescription(rs.getString("description"));
					deptDTO.setIsActiveYn(rs.getString("is_active_yn"));
					deptDTO.setDeptLoc(rs.getString("dept_loc"));

					deptList.add(deptDTO);
				}
			}

		} finally {
			DBConnection.close(rs, pstmt, con);
		}

		return deptList;
	}

	public List<DoctorDTO> selectDoctorList(String deptNo) throws SQLException {
		List<DoctorDTO> doctorList = new ArrayList<DoctorDTO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBConnection.getConnection();

			StringBuilder querySb = new StringBuilder();
			querySb //
					.append("	select	doctor_license_no, dept_no, name, phone_num, position_code, intro_title, intro_content, thumbnail_url, detail_image_url, create_date, specialty, status_code")
					.append("	from	doctor") //
					.append("	where 	dept_no = ?");

			pstmt = con.prepareStatement(querySb.toString());
			
			pstmt.setString(1, deptNo);
			
			rs = pstmt.executeQuery();
			
			DoctorDTO dDTO = null;
			if (rs != null) {
				while (rs.next()) {
					dDTO = new DoctorDTO();
					
					dDTO.setDoctorLicenseNo(rs.getInt("doctor_license_no"));
					dDTO.setDeptNo(rs.getString("dept_no"));
					dDTO.setName(rs.getString("name"));
					dDTO.setPhoneNum(rs.getString("phone_num"));
					dDTO.setPositionCode(rs.getString("position_code"));
					dDTO.setIntroTitle(rs.getString("intro_title"));
					dDTO.setIntroContent(rs.getString("intro_content"));
					dDTO.setThumbnailUrl(rs.getString("thumbnail_url"));
					dDTO.setDetailImageUrl(rs.getString("detail_image_url"));
					dDTO.setCreatedDate(rs.getDate("create_date"));
					dDTO.setSpecialty(rs.getString("specialty"));
					dDTO.setStatusCode(rs.getString("status_code"));
					
					doctorList.add(dDTO);
				}
			}

		} finally {
			DBConnection.close(rs, pstmt, con);
		}

		return doctorList;
	}

	public UserAppointmentOptionDTO selectDoctorDetail(int doctorLicenseNo) {

		return null;
	}

	
	/**
	 * 의사의 진료 요일, 시작 시간, 끝 시간 검색.
	 * 
	 * @param dln
	 * @return
	 * @throws SQLException
	 */
	public List<DoctorScheduleDTO> selectDoctorSchedule(int dln) throws SQLException {
		List<DoctorScheduleDTO> dsList = new ArrayList<>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBConnection.getConnection();

			StringBuilder querySb = new StringBuilder();
			querySb //
					.append("	select  SCHEDULE_NO, DOCTOR_LICENSE_NO, DAY_OF_WEEK, START_TIME, END_TIME, STATUS	")
					.append("	from 	doctor_schedule	") //
					.append("	where 	doctor_license_no = ?	");

			pstmt = con.prepareStatement(querySb.toString());
			
			pstmt.setInt(1, dln);
			
			rs = pstmt.executeQuery();
			
			DoctorScheduleDTO dsDTO = null;
			if (rs != null) {
				while (rs.next()) {
					dsDTO = new DoctorScheduleDTO();
					
					dsDTO.setScheduleNo(rs.getInt("SCHEDULE_NO"));
					dsDTO.setDoctorLicenseNo(rs.getInt("DOCTOR_LICENSE_NO"));
					dsDTO.setDayOfWeek(rs.getInt("DAY_OF_WEEK"));
					dsDTO.setStartTime(rs.getString("START_TIME"));
					dsDTO.setEndTime(rs.getString("END_TIME"));
					dsDTO.setStatus(rs.getString("STATUS"));
					
					dsList.add(dsDTO);
				}
			}

		} finally {
			DBConnection.close(rs, pstmt, con);
		}
		
		return dsList;
	}

	/**
	 * @param doctorLicenseNo
	 * @param appointmentDate
	 * @return 이미 예약된 진료 시간.
	 */
	public List<String> selectReservedTime(int dln, Date appointmentDate) throws SQLException {
		List<String> reservedTimes = new ArrayList<String>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBConnection.getConnection();

			StringBuilder querySb = new StringBuilder();
			querySb //
					.append("	select 	DOCTOR_LICENSE_NO, APPOINTMENT_DATE, APPOINTMENT_TIME	")
					.append("	from 	appointment	") //
					.append("	where DOCTOR_LICENSE_NO = ? and APPOINTMENT_DATE = ?	");

			pstmt = con.prepareStatement(querySb.toString());
			
			pstmt.setInt(1, dln);
			pstmt.setDate(2, appointmentDate);
			
			rs = pstmt.executeQuery();
			
			if (rs != null) {
				while (rs.next()) {
					reservedTimes.add(rs.getString("APPOINTMENT_TIME"));
				}
			}

		} finally {
			DBConnection.close(rs, pstmt, con);
		}
		
		return reservedTimes;
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
