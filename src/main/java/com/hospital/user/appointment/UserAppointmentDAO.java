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
import com.hospital.user.appointment.dto.UserAppointmentOptionDTO;
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
	
	public DepartmentDTO selectDepartment(String deptNo) throws SQLException {
		DepartmentDTO deptDTO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBConnection.getConnection();

			String query = "	select dept_no, dept_name, description, is_active_yn, dept_loc from department where dept_no = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, deptNo);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				deptDTO = new DepartmentDTO();

				deptDTO.setDeptNo(rs.getString("dept_no"));
				deptDTO.setDeptName(rs.getString("dept_name"));
				deptDTO.setDescription(rs.getString("description"));
				deptDTO.setIsActiveYn(rs.getString("is_active_yn"));
				deptDTO.setDeptLoc(rs.getString("dept_loc"));
			}

		} finally {
			DBConnection.close(rs, pstmt, con);
		}

		return deptDTO;
	}

	/**
	 * 진료과 목록 정보 조회
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<DepartmentDTO> selectDepartmentList() throws SQLException {
		List<DepartmentDTO> deptList = new ArrayList<>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBConnection.getConnection();

			String query = "	select dept_no, dept_name, description, is_active_yn, dept_loc from department where is_active_yn = 'Y'	";
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
	public List<UserAppointmentOptionDTO> selectDoctorListByKeyword(String keyword) throws SQLException {
		List<UserAppointmentOptionDTO> uaoDTOList = new ArrayList<>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBConnection.getConnection();

			StringBuilder querySb = new StringBuilder();
			querySb //
					.append("	select	doctor_license_no, d.dept_no dept_no, dept_name, name, thumbnail_url, specialty	 ")
					.append("	from	doctor d, department de ") //
					.append("	where	d.dept_no = de.dept_no and (name like '%' || ? || '%' or specialty like '%' || ? || '%')	");

			pstmt = con.prepareStatement(querySb.toString());

			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);

			rs = pstmt.executeQuery();

			UserAppointmentOptionDTO uaoDTO = null;
			if (rs != null) {
				while (rs.next()) {
					uaoDTO = new UserAppointmentOptionDTO();

					uaoDTO.setDeptNo(rs.getString("dept_no"));
					uaoDTO.setDeptName(rs.getString("dept_name"));
					uaoDTO.setDoctorLicenseNo(rs.getInt("doctor_license_no"));
					uaoDTO.setDoctorName(rs.getString("name"));
					uaoDTO.setSpecialty(rs.getString("specialty"));
					uaoDTO.setThumbnailUrl(rs.getString("thumbnail_url"));
					
					uaoDTOList.add(uaoDTO);
				}
			}

		} finally {
			DBConnection.close(rs, pstmt, con);
		}
		
		return uaoDTOList;
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
					.append("	where DOCTOR_LICENSE_NO = ?  	and APPOINTMENT_DATE = ?	")
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

	public List<String> selectReservedTime(int dln, Date appointmentDate, String excludeAppointmentNo) throws SQLException {
		List<String> reservedTimes = new ArrayList<String>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBConnection.getConnection();

			StringBuilder querySb = new StringBuilder();
			querySb
					.append("	select 	DOCTOR_LICENSE_NO, APPOINTMENT_DATE, APPOINTMENT_TIME	")
					.append("	from 	appointment	")
					.append("	where DOCTOR_LICENSE_NO = ?  	and APPOINTMENT_DATE = ?	")
					.append("	and NVL(TRIM(status), ' ') <> '예약취소'	")
					.append("	and APPOINTMENT_NO <> ?	");

			pstmt = con.prepareStatement(querySb.toString());

			pstmt.setInt(1, dln);
			pstmt.setDate(2, appointmentDate);
			pstmt.setString(3, excludeAppointmentNo);

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

	/**
	 * 사용자가 원하는 예약일, 시간과 동일한 시간대에 중복된 예약이 존재하는지 확인하는 일.
	 * 
	 * @param requestDTO
	 * @return 요청받은 예약의 날짜, 시간, 의사가 전부 같은 예약 건수. 0건이면 진료 예약이 가능한 것.
	 * @throws SQLException
	 */
	public int selectAppointmentConflict(UserAppointmentRequestDTO requestDTO) throws SQLException {
		int cnt = 0;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBConnection.getConnection();

			StringBuilder querySb = new StringBuilder();
			querySb //
					.append("	select 	APPOINTMENT_NO	") //
					.append("	from 	APPOINTMENT	") //
					.append("	where	DOCTOR_LICENSE_NO = ? and APPOINTMENT_DATE = ? and APPOINTMENT_TIME = ? and not status = '예약취소'	");

			pstmt = con.prepareStatement(querySb.toString());

			pstmt.setInt(1, requestDTO.getDoctorLicenseNo());
			pstmt.setDate(2, requestDTO.getAppointmentDate());
			pstmt.setString(3, requestDTO.getAppointmentTime());

			rs = pstmt.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					cnt = cnt + 1;
				}
			}

		} finally {
			DBConnection.close(rs, pstmt, con);
		}

		return cnt;
	}

	public int selectAppointmentConflict(UserAppointmentRequestDTO requestDTO, String excludeAppointmentNo) throws SQLException {
		int cnt = 0;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBConnection.getConnection();

			StringBuilder querySb = new StringBuilder();
			querySb
					.append("	select 	APPOINTMENT_NO	")
					.append("	from 	APPOINTMENT	")
					.append("	where	DOCTOR_LICENSE_NO = ? and APPOINTMENT_DATE = ? and APPOINTMENT_TIME = ? and not status = '예약취소'	")
					.append("	and		APPOINTMENT_NO <> ?	");

			pstmt = con.prepareStatement(querySb.toString());

			pstmt.setInt(1, requestDTO.getDoctorLicenseNo());
			pstmt.setDate(2, requestDTO.getAppointmentDate());
			pstmt.setString(3, requestDTO.getAppointmentTime());
			pstmt.setString(4, excludeAppointmentNo);

			rs = pstmt.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					cnt = cnt + 1;
				}
			}

		} finally {
			DBConnection.close(rs, pstmt, con);
		}

		return cnt;
	}

	/**
	 * 유저가 요청한 예약을 DB에 입력하는 일. 중복된 예약건수가 0 건일때 예약을 commit 하는 코드 추가 - 2026-06-25
	 * 
	 * @param requestDTO
	 * @return
	 * @throws SQLException
	 */
	public int insertAppointment(UserAppointmentRequestDTO requestDTO) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		int cnt = 0;

		try {
			con = DBConnection.getConnection();

			con.setAutoCommit(false);

			StringBuilder querySb = new StringBuilder();
			querySb //
					.append("	insert into appointment(PATIENT_NO, DOCTOR_LICENSE_NO, APPOINTMENT_DATE, APPOINTMENT_TIME, REQUIREMENT, STATUS, active_slot_key)	")
					.append("	values(?, ?, ?, ?, ?, ?, ?)	");

			pstmt = con.prepareStatement(querySb.toString());

			pstmt.setString(1, requestDTO.getPatientNo());
			pstmt.setInt(2, requestDTO.getDoctorLicenseNo());
			pstmt.setDate(3, requestDTO.getAppointmentDate());
			pstmt.setString(4, requestDTO.getAppointmentTime());
			pstmt.setString(5, requestDTO.getRequirement());
			pstmt.setString(6, requestDTO.getStatus());
			pstmt.setString(7, requestDTO.getDoctorLicenseNo() + requestDTO.getAppointmentDate().toString() + requestDTO.getAppointmentTime());

			if (selectAppointmentConflict(requestDTO) == 0) {
				cnt = pstmt.executeUpdate();
				con.commit();
			} else {
				con.rollback();
			}

		} finally {
			DBConnection.close(pstmt, con);
		}

		return cnt;
	}

	/**
	 * 예약을 수정하는 일
	 * 
	 * @return
	 * @throws SQLException
	 */
	public int updateAppointment(String appointmentNo, String patientNo, UserAppointmentRequestDTO requestDTO)
			throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		int cnt = 0;

		try {
			con = DBConnection.getConnection();
			con.setAutoCommit(false);

			StringBuilder querySb = new StringBuilder();
			querySb //
					.append("	update 	appointment	")
					.append("	set 	doctor_license_no = ?, appointment_date = ?, appointment_time = ?, requirement = ?, status = ?, active_slot_key = ?	")
					.append("	where 	appointment_no = ?	and patient_no = ?	")
					.append("	and 	status in ('예약대기', '예약완료')	");

			pstmt = con.prepareStatement(querySb.toString());

			pstmt.setInt(1, requestDTO.getDoctorLicenseNo());
			pstmt.setDate(2, requestDTO.getAppointmentDate());
			pstmt.setString(3, requestDTO.getAppointmentTime());
			pstmt.setString(4, requestDTO.getRequirement());
			pstmt.setString(5, requestDTO.getStatus());
			pstmt.setString(6, requestDTO.getDoctorLicenseNo() + requestDTO.getAppointmentDate().toString() + requestDTO.getAppointmentTime());
			pstmt.setString(7, appointmentNo);
			pstmt.setString(8, patientNo);

			cnt = pstmt.executeUpdate();
			if (cnt == 1) {
				con.commit();
			} else {
				con.rollback();
			}

		} catch (SQLException e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException rollbackException) {
					e.addSuppressed(rollbackException);
				}
			}
			throw e;
		} finally {
			DBConnection.close(pstmt, con);
		}

		return cnt;
	}

	public UserAppointmentConfirmDTO selectChangeableAppointment(String appointmentNo, String patientNo)
			throws SQLException {
		UserAppointmentConfirmDTO uacDTO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBConnection.getConnection();

			StringBuilder querySb = new StringBuilder();
			querySb
					.append("	select 	A.APPOINTMENT_NO, A.PATIENT_NO PATIENT_NO, M.name patient_Name, ")
					.append("			M.phone_number phone_number, M.email email, DE.dept_no dept_no, DE.dept_name dept_name, ")
					.append("			D.doctor_license_no doctor_license_no, D.name doctor_name, APPOINTMENT_DATE, APPOINTMENT_TIME, REQUIREMENT, STATUS, CREATED_AT	")
					.append("	from 	appointment A, doctor D, MEMBER M, DEPARTMENT DE	")
					.append("	where	A.APPOINTMENT_NO = ? and A.PATIENT_NO = ?	")
					.append("	and		A.STATUS in ('예약대기', '예약완료')	")
					.append("	and		A.DOCTOR_LICENSE_NO = D.DOCTOR_LICENSE_NO	")
					.append("	and		M.PATIENT_NO = A.PATIENT_NO	")
					.append("	and		DE.DEPT_NO = D.DEPT_NO	");

			pstmt = con.prepareStatement(querySb.toString());

			pstmt.setString(1, appointmentNo);
			pstmt.setString(2, patientNo);

			rs = pstmt.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					uacDTO = new UserAppointmentConfirmDTO();

					uacDTO.setAppointmentNo(rs.getString("APPOINTMENT_NO"));
					uacDTO.setPatientNo(rs.getString("PATIENT_NO"));
					uacDTO.setPatientName(rs.getString("patient_Name"));
					uacDTO.setPhoneNumber(rs.getString("phone_number"));
					uacDTO.setEmail(rs.getString("email"));
					uacDTO.setDeptNo(rs.getString("dept_no"));
					uacDTO.setDeptName(rs.getString("dept_name"));
					uacDTO.setDoctorLicenseNo(rs.getInt("doctor_license_no"));
					uacDTO.setDoctorName(rs.getString("doctor_name"));
					uacDTO.setAppointmentDate(rs.getDate("APPOINTMENT_DATE"));
					uacDTO.setAppointmentTime(rs.getString("APPOINTMENT_TIME"));
					uacDTO.setRequirement(rs.getString("REQUIREMENT"));
					uacDTO.setStatus(rs.getString("STATUS"));
					uacDTO.setCreatedAt(rs.getDate("CREATED_AT"));
				}
			}

		} finally {
			DBConnection.close(rs, pstmt, con);
		}

		return uacDTO;
	}

	/**
	 * 유저의 예약 정보를 확인하는 일
	 * 
	 * @param appointmentNo
	 * @return
	 * @throws SQLException
	 */
	public UserAppointmentConfirmDTO selectAppointmentConfirm(String appointmentNo) throws SQLException {
		UserAppointmentConfirmDTO uacDTO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBConnection.getConnection();

			StringBuilder querySb = new StringBuilder();
			querySb //
					.append("	select 	A.APPOINTMENT_NO, A.PATIENT_NO PATIENT_NO, M.name patient_Name, M.phone_number phone_number, M.email email, DE.dept_name dept_name, D.name doctor_name, APPOINTMENT_DATE, APPOINTMENT_TIME, REQUIREMENT, STATUS, CREATED_AT	")
					.append("	from 	appointment A, doctor D, MEMBER M, DEPARTMENT DE	") //
					.append("	where	APPOINTMENT_NO = ? and A.DOCTOR_LICENSE_NO = D.DOCTOR_LICENSE_NO and M.PATIENT_NO = A.PATIENT_NO and DE.DEPT_NO = D.DEPT_NO	");

			pstmt = con.prepareStatement(querySb.toString());

			pstmt.setString(1, appointmentNo);

			rs = pstmt.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					uacDTO = new UserAppointmentConfirmDTO();

					uacDTO.setAppointmentNo(rs.getString("APPOINTMENT_NO"));
					uacDTO.setPatientNo(rs.getString("PATIENT_NO"));
					uacDTO.setPatientName(rs.getString("patient_Name"));
					uacDTO.setPhoneNumber(rs.getString("phone_number"));
					uacDTO.setEmail(rs.getString("email"));
					uacDTO.setDeptName(rs.getString("dept_name"));
					uacDTO.setDoctorName(rs.getString("doctor_name"));
					uacDTO.setAppointmentDate(rs.getDate("APPOINTMENT_DATE"));
					uacDTO.setAppointmentTime(rs.getString("APPOINTMENT_TIME"));
					uacDTO.setRequirement(rs.getString("REQUIREMENT"));
					uacDTO.setStatus(rs.getString("STATUS"));
					uacDTO.setCreatedAt(rs.getDate("CREATED_AT"));
				}
			}

		} finally {
			DBConnection.close(rs, pstmt, con);
		}

		return uacDTO;
	}

	/**
	 * 유저의 예약 정보를 확인하는 일
	 * 
	 * @param dln
	 * @param appointmentDate
	 * @param appointmentTime
	 * @return
	 * @throws SQLException
	 */
	public UserAppointmentConfirmDTO selectAppointmentConfirm(UserAppointmentRequestDTO requestDTO)
			throws SQLException {
		UserAppointmentConfirmDTO uacDTO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBConnection.getConnection();

			StringBuilder querySb = new StringBuilder();
			querySb //
					.append("	select 	A.APPOINTMENT_NO, A.PATIENT_NO PATIENT_NO, M.name patient_Name, M.phone_number phone_number, M.email email, DE.dept_name dept_name, D.name doctor_name, APPOINTMENT_DATE, APPOINTMENT_TIME, REQUIREMENT, STATUS, CREATED_AT	")
					.append("	from 	appointment A, doctor D, MEMBER M, DEPARTMENT DE	") //
					.append("	where	A.DOCTOR_LICENSE_NO = ? and  APPOINTMENT_DATE = ? and  APPOINTMENT_TIME = ?	")
					.append("	and A.DOCTOR_LICENSE_NO = D.DOCTOR_LICENSE_NO and M.PATIENT_NO = A.PATIENT_NO and DE.DEPT_NO = D.DEPT_NO	");

			pstmt = con.prepareStatement(querySb.toString());

			pstmt.setInt(1, requestDTO.getDoctorLicenseNo());
			pstmt.setDate(2, requestDTO.getAppointmentDate());
			pstmt.setString(3, requestDTO.getAppointmentTime());

			rs = pstmt.executeQuery();

			uacDTO = new UserAppointmentConfirmDTO();
			if (rs != null) {
				while (rs.next()) {
					uacDTO.setAppointmentNo(rs.getString("APPOINTMENT_NO"));
					uacDTO.setPatientNo(rs.getString("PATIENT_NO"));
					uacDTO.setPatientName(rs.getString("patient_Name"));
					uacDTO.setPhoneNumber(rs.getString("phone_number"));
					uacDTO.setEmail(rs.getString("email"));
					uacDTO.setDeptName(rs.getString("dept_name"));
					uacDTO.setDoctorName(rs.getString("doctor_name"));
					uacDTO.setAppointmentDate(rs.getDate("APPOINTMENT_DATE"));
					uacDTO.setAppointmentTime(rs.getString("APPOINTMENT_TIME"));
					uacDTO.setRequirement(rs.getString("REQUIREMENT"));
					uacDTO.setStatus(rs.getString("STATUS"));
					uacDTO.setCreatedAt(rs.getDate("CREATED_AT"));
				}
			}

		} finally {
			DBConnection.close(rs, pstmt, con);
		}

		return uacDTO;
	}

	/**
	 * 예약을 취소하는 일
	 * 
	 * @param appointmentNo
	 * @param patientNo
	 * @return
	 * @throws SQLException
	 */
	public int updateCancelAppointment(String appointmentNo, String patientNo) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		int cnt = 0;

		try {
			con = DBConnection.getConnection();
			con.setAutoCommit(false);

			StringBuilder querySb = new StringBuilder();
			querySb //
					.append("	update 	APPOINTMENT	") //
					.append("	set		status = '예약취소', canceled_at = sysdate, active_slot_key = null	")
					.append("	where 	appointment_no = ? and patient_no = ?	");

			pstmt = con.prepareStatement(querySb.toString());

			pstmt.setString(1, appointmentNo);
			pstmt.setString(2, patientNo);

			cnt = pstmt.executeUpdate();

			if (cnt == 1) {
				con.commit();
			} else {
				con.rollback();
			}

		} finally {
			DBConnection.close(pstmt, con);
		}

		return cnt;
	}

	/**
	 * @param patientNo
	 * @return
	 * @throws SQLException
	 */
	public List<UserAppointmentShowDTO> selectAppointmentDetail(String patientNo) throws SQLException {
		List<UserAppointmentShowDTO> uasDTOList = new ArrayList<>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBConnection.getConnection();

			StringBuilder querySb = new StringBuilder();
			querySb //
					.append("	select 	APPOINTMENT_NO, THUMBNAIL_URL, DEPT_NAME, NAME, CREATED_AT, APPOINTMENT_DATE, APPOINTMENT_TIME, DEPT_LOC	")
					.append("	from 	APPOINTMENT A, DOCTOR D, DEPARTMENT DE	") //
					.append("	where	PATIENT_NO = ? AND NOT STATUS = '예약취소'  AND A.DOCTOR_LICENSE_NO = D.DOCTOR_LICENSE_NO AND D.DEPT_NO = DE.DEPT_NO	");

			pstmt = con.prepareStatement(querySb.toString());

			pstmt.setString(1, patientNo);

			rs = pstmt.executeQuery();

			UserAppointmentShowDTO uasDTO = null;
			if (rs != null) {
				while (rs.next()) {
					uasDTO = new UserAppointmentShowDTO();

					uasDTO.setAppointmentNo(rs.getString("APPOINTMENT_NO"));
					uasDTO.setThumbnailUrl(rs.getString("THUMBNAIL_URL"));
					uasDTO.setDeptName(rs.getString("DEPT_NAME"));
					uasDTO.setDoctorName(rs.getString("NAME"));
					uasDTO.setCreatedAt(rs.getDate("CREATED_AT"));
					uasDTO.setAppointmentDate(rs.getDate("APPOINTMENT_DATE"));
					uasDTO.setAppointmentTime(rs.getString("APPOINTMENT_TIME"));
					uasDTO.setDeptLoc(rs.getString("DEPT_LOC"));

					uasDTOList.add(uasDTO);
				}
			}

		} finally {
			DBConnection.close(rs, pstmt, con);
		}

		return uasDTOList;
	}

}
