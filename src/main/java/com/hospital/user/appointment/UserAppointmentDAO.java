package com.hospital.user.appointment;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hospital.common.util.DBConnection;
import com.hospital.common.dto.DepartmentDTO;
import com.hospital.common.dto.DoctorDTO;
import com.hospital.common.dto.DoctorScheduleDTO;
import com.hospital.user.appointment.dto.UserAppointmentConfirmDTO;
import com.hospital.user.appointment.dto.UserAppointmentRequestDTO;
import com.hospital.user.appointment.dto.UserAppointmentShowDTO;

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

	/**
	 * 입력받은 진료과의 의료진의 목록을 찾는 일.
	 * 
	 * @param deptNo
	 * @return
	 * @throws SQLException
	 */
	public List<DoctorDTO> selectDoctorList(String deptNo) throws SQLException {
		List<DoctorDTO> doctorList = new ArrayList<DoctorDTO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBConnection.getConnection();

			StringBuilder querySb = new StringBuilder();
			querySb //
					.append("	select	doctor_license_no, dept_no, name, phone_num, position_code, intro_title, intro_content, thumbnail_url, detail_image_url, create_date, specialty, status_code ")
					.append("	from	doctor ") //
					.append("	where 	dept_no = ? ");

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
	
	/**
	 * 사용자가 검색한 키워드와 일치하는 의료진을 찾는 일
	 * 
	 * @param keyword
	 * @return
	 * @throws SQLException
	 */
	public List<DoctorDTO> selectDoctorListByKeyword(String keyword) throws SQLException {
		List<DoctorDTO> doctorList = new ArrayList<DoctorDTO>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBConnection.getConnection();
			
			StringBuilder querySb = new StringBuilder();
			querySb //
			.append("	select	doctor_license_no, dept_no, name, phone_num, position_code, intro_title, intro_content, thumbnail_url, detail_image_url, create_date, specialty, status_code")
			.append("	from	doctor"	) //
			.append("	where	name like '%' || ? || '%' or specialty like '%' || ? || '%'"	);
			
			pstmt = con.prepareStatement(querySb.toString());
			
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			
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

	public UserAppointmentOptionDTO selectDoctorDetail(int doctorLicenseNo) throws SQLException {
		UserAppointmentOptionDTO optionDTO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBConnection.getConnection();

			StringBuilder querySb = new StringBuilder();
			querySb
					.append("	select d.dept_no, dept.dept_name, d.doctor_license_no, d.name doctor_name, ")
					.append("	       d.position_code, d.specialty, d.thumbnail_url ")
					.append("	from doctor d ")
					.append("	join department dept on d.dept_no = dept.dept_no ")
					.append("	where d.doctor_license_no = ? ");

			pstmt = con.prepareStatement(querySb.toString());
			pstmt.setInt(1, doctorLicenseNo);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				optionDTO = new UserAppointmentOptionDTO();
				optionDTO.setDeptNo(rs.getString("dept_no"));
				optionDTO.setDeptName(rs.getString("dept_name"));
				optionDTO.setDoctorLicenseNo(rs.getInt("doctor_license_no"));
				optionDTO.setDoctorName(rs.getString("doctor_name"));
				optionDTO.setPositionName(rs.getString("position_code"));
				optionDTO.setSpecialty(rs.getString("specialty"));
				optionDTO.setThumbnailUrl(rs.getString("thumbnail_url"));
				optionDTO.setReservable(true);
			}
		} finally {
			DBConnection.close(rs, pstmt, con);
		}

		return optionDTO;
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
	 * 이미 예약된 시간을 찾는 일.
	 * 
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
					.append("	where DOCTOR_LICENSE_NO = ? and APPOINTMENT_DATE = ?	")
					.append("	and NVL(TRIM(status), ' ') <> '예약취소'	");

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

	public int selectAppointmentConflict(UserAppointmentRequestDTO requestDTO) throws SQLException {
		int conflictCnt = 0;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBConnection.getConnection();

			StringBuilder querySb = new StringBuilder();
			querySb
					.append("	select count(*) cnt ")
					.append("	from appointment ")
					.append("	where doctor_license_no = ? ")
					.append("	and appointment_date = ? ")
					.append("	and appointment_time = ? ")
					.append("	and NVL(TRIM(status), ' ') <> '예약취소' ");

			pstmt = con.prepareStatement(querySb.toString());
			pstmt.setInt(1, requestDTO.getDoctorLicenseNo());
			pstmt.setDate(2, requestDTO.getAppointmentDate());
			pstmt.setString(3, requestDTO.getAppointmentTime());
			rs = pstmt.executeQuery();

			if (rs.next()) {
				conflictCnt = rs.getInt("cnt");
			}
		} finally {
			DBConnection.close(rs, pstmt, con);
		}

		return conflictCnt;
	}

	public String insertAppointment(UserAppointmentRequestDTO requestDTO) throws SQLException {
		String appointmentNo = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBConnection.getConnection();

			StringBuilder querySb = new StringBuilder();
			querySb
					.append("	insert into appointment ")
					.append("	(patient_no, doctor_license_no, appointment_date, appointment_time, requirement, status) ")
					.append("	values (?, ?, ?, ?, ?, ?) ");

			pstmt = con.prepareStatement(querySb.toString());
			pstmt.setString(1, requestDTO.getPatientNo());
			pstmt.setInt(2, requestDTO.getDoctorLicenseNo());
			pstmt.setDate(3, requestDTO.getAppointmentDate());
			pstmt.setString(4, requestDTO.getAppointmentTime());
			pstmt.setString(5, defaultText(requestDTO.getRequirement()));
			pstmt.setString(6, defaultStatus(requestDTO.getStatus()));

			int insertCnt = pstmt.executeUpdate();
			if (insertCnt > 0) {
				appointmentNo = selectLatestAppointmentNo(requestDTO);
			}
		} finally {
			DBConnection.close(pstmt, con);
		}

		return appointmentNo;
	}

	public UserAppointmentConfirmDTO selectAppointmentConfirm(String appointmentNo) throws SQLException {
		UserAppointmentConfirmDTO confirmDTO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBConnection.getConnection();

			StringBuilder querySb = new StringBuilder();
			querySb
					.append("	select a.appointment_no, a.patient_no, m.name patient_name, ")
					.append("	       m.phone_number, m.email, dept.dept_name, d.name doctor_name, ")
					.append("	       a.appointment_date, a.appointment_time, a.requirement, a.status, a.created_at ")
					.append("	from appointment a ")
					.append("	join member m on a.patient_no = m.patient_no ")
					.append("	join doctor d on a.doctor_license_no = d.doctor_license_no ")
					.append("	join department dept on d.dept_no = dept.dept_no ")
					.append("	where a.appointment_no = ? ");

			pstmt = con.prepareStatement(querySb.toString());
			pstmt.setString(1, appointmentNo);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				confirmDTO = mapAppointmentConfirm(rs);
			}
		} finally {
			DBConnection.close(rs, pstmt, con);
		}

		return confirmDTO;
	}

	public int updateCancelAppointment(String appointmentNo, String patientNo) throws SQLException {
		int updateCnt = 0;

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DBConnection.getConnection();

			StringBuilder querySb = new StringBuilder();
			querySb
					.append("	update appointment ")
					.append("	set status = '예약취소', canceled_at = sysdate ")
					.append("	where appointment_no = ? ")
					.append("	and patient_no = ? ")
					.append("	and NVL(TRIM(status), ' ') <> '예약취소' ");

			pstmt = con.prepareStatement(querySb.toString());
			pstmt.setString(1, appointmentNo);
			pstmt.setString(2, patientNo);
			updateCnt = pstmt.executeUpdate();
		} finally {
			DBConnection.close(pstmt, con);
		}

		return updateCnt;
	}

	private String selectLatestAppointmentNo(UserAppointmentRequestDTO requestDTO) throws SQLException {
		String appointmentNo = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBConnection.getConnection();

			StringBuilder querySb = new StringBuilder();
			querySb
					.append("	select appointment_no ")
					.append("	from appointment ")
					.append("	where patient_no = ? ")
					.append("	and doctor_license_no = ? ")
					.append("	and appointment_date = ? ")
					.append("	and appointment_time = ? ")
					.append("	order by created_at desc ");

			pstmt = con.prepareStatement(querySb.toString());
			pstmt.setString(1, requestDTO.getPatientNo());
			pstmt.setInt(2, requestDTO.getDoctorLicenseNo());
			pstmt.setDate(3, requestDTO.getAppointmentDate());
			pstmt.setString(4, requestDTO.getAppointmentTime());
			rs = pstmt.executeQuery();

			if (rs.next()) {
				appointmentNo = rs.getString("appointment_no");
			}
		} finally {
			DBConnection.close(rs, pstmt, con);
		}

		return appointmentNo;
	}

	private UserAppointmentConfirmDTO mapAppointmentConfirm(ResultSet rs) throws SQLException {
		UserAppointmentConfirmDTO confirmDTO = new UserAppointmentConfirmDTO();
		confirmDTO.setAppointmentNo(rs.getString("appointment_no"));
		confirmDTO.setPatientNo(rs.getString("patient_no"));
		confirmDTO.setPatientName(rs.getString("patient_name"));
		confirmDTO.setPhoneNumber(rs.getString("phone_number"));
		confirmDTO.setEmail(rs.getString("email"));
		confirmDTO.setDeptName(rs.getString("dept_name"));
		confirmDTO.setDoctorName(rs.getString("doctor_name"));
		confirmDTO.setAppointmentDate(rs.getDate("appointment_date"));
		confirmDTO.setAppointmentTime(rs.getString("appointment_time"));
		confirmDTO.setRequirement(rs.getString("requirement"));
		String status = rs.getString("status");
		confirmDTO.setStatus(status == null ? null : status.trim());
		confirmDTO.setCreatedAt(rs.getDate("created_at"));
		return confirmDTO;
	}

	private String defaultText(String value) {
		return value == null || value.isBlank() ? "없음" : value.trim();
	}

	private String defaultStatus(String status) {
		return status == null || status.isBlank() ? "승인 대기" : status.trim();
	}

}
