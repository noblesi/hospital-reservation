package com.hospital.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hospital.common.util.DBConnection;
import com.hospital.common.MemberDTO;
import com.hospital.member.dto.UserAppointmentDTO;
import com.hospital.member.dto.UserMedicalRecordDTO;

/**
 * UserMyPageDAO
 * 마이페이지 관련 DB 작업을 처리하는 DAO
 */
public class UserMyPageDAO {

	private static UserMyPageDAO umpDAO;

	private UserMyPageDAO() {
	}//UserMyPageDAO

	public static UserMyPageDAO getInstance() {
		if (umpDAO == null) {
			umpDAO = new UserMyPageDAO();
		}//end if
		return umpDAO;
	}//getInstance

	/**
	 * 로그인 아이디에 해당하는 마이페이지 기본 회원 정보를 조회한다.
	 *
	 * @param loginId 로그인 아이디
	 * @return 환자번호와 이름을 담은 회원 DTO, 회원이 없으면 null
	 * @throws SQLException 회원 조회 중 DB 오류가 발생한 경우
	 */
	public MemberDTO selectMember(String loginId) throws SQLException {

		MemberDTO mDTO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBConnection.getConnection();

			StringBuilder sql = new StringBuilder();

			sql.append(" SELECT patient_no, name ")
			   .append(" FROM member ")
			   .append(" WHERE login_id = ? ");

			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, loginId);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				mDTO = new MemberDTO();
				mDTO.setPatientNo(rs.getString("patient_no"));
				mDTO.setName(rs.getString("name"));
			}//end if

		} finally {
			DBConnection.close(rs, pstmt, con);
		}//end finally

		return mDTO;
	}//selectMember

	/**
	 * 환자번호에 해당하는 오늘 이후 예약 내역을 가까운 예약일순으로 조회한다.
	 * 예약 현황 모달에서 취소 내역까지 확인할 수 있도록 예약취소 상태도 포함한다.
	 *
	 * @param patientNo 환자번호
	 * @return 예약 및 진료과·의료진 정보를 담은 예약 목록
	 * @throws SQLException 예약 내역 조회 중 DB 오류가 발생한 경우
	 */
	public List<UserAppointmentDTO> selectAppointmentList(String patientNo) throws SQLException {

		List<UserAppointmentDTO> list = new ArrayList<UserAppointmentDTO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBConnection.getConnection();

			StringBuilder sql = new StringBuilder();

			sql.append(" SELECT a.appointment_no, ")
			   .append("        a.patient_no, ")
			   .append("        a.doctor_license_no, ")
			   .append("        a.appointment_date, ")
			   .append("        a.appointment_time, ")
			   .append("        a.requirement, ")
			   .append("        a.status, ")
			   .append("        a.created_at, ")
			   .append("        a.canceled_at, ")
			   .append("        dept.dept_name department_name, ")
			   .append("        d.name doctor_name ")
			   .append(" FROM appointment a ")
			   .append(" JOIN doctor d ")
			   .append("   ON a.doctor_license_no = d.doctor_license_no ")
			   .append(" JOIN department dept ")
			   .append("   ON d.dept_no = dept.dept_no ")
			   .append(" WHERE a.patient_no = ? ")
			   .append(" AND a.appointment_date >= TRUNC(SYSDATE) ")
			   .append(" ORDER BY a.appointment_date ASC, a.appointment_time ASC ");

			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, patientNo);

			rs = pstmt.executeQuery();

			UserAppointmentDTO uaDTO = null;

			while (rs.next()) {
				uaDTO = new UserAppointmentDTO();

				uaDTO.setAppointmentNo(rs.getString("appointment_no"));
				uaDTO.setPatientNo(rs.getString("patient_no"));
				uaDTO.setDoctorLicenseNo(rs.getInt("doctor_license_no"));
				uaDTO.setAppointmentDate(rs.getDate("appointment_date"));
				uaDTO.setAppointmentTime(rs.getString("appointment_time"));
				uaDTO.setRequirement(rs.getString("requirement"));
				// CHAR 타입 상태값 뒤에 붙을 수 있는 공백을 제거하여 화면 필터 비교를 정상화한다.
				String appointmentStatus = rs.getString("status");
				uaDTO.setStatus(appointmentStatus == null ? null : appointmentStatus.trim());
				uaDTO.setCreatedAt(rs.getDate("created_at"));
				uaDTO.setCanceledAt(rs.getDate("canceled_at"));
				uaDTO.setDepartmentName(rs.getString("department_name"));
				uaDTO.setDoctorName(rs.getString("doctor_name"));

				list.add(uaDTO);
			}//end while

		} finally {
			DBConnection.close(rs, pstmt, con);
		}//end finally

		return list;
	}//selectAppointmentList

	/**
	 * 환자번호에 해당하는 예약 관리 목록을 조회한다.
	 * 마이페이지 하단의 예약 취소 및 변경 영역에서 현재일 기준 최근 3개월 예약까지 함께 확인할 수 있도록 사용한다.
	 *
	 * @param patientNo 환자번호
	 * @return 예약 취소 및 변경 영역에 표시할 예약 목록
	 * @throws SQLException 예약 내역 조회 중 DB 오류가 발생한 경우
	 */
	public List<UserAppointmentDTO> selectManageAppointmentList(String patientNo) throws SQLException {

		List<UserAppointmentDTO> list = new ArrayList<UserAppointmentDTO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBConnection.getConnection();

			StringBuilder sql = new StringBuilder();

			sql.append(" SELECT a.appointment_no, ")
			   .append("        a.patient_no, ")
			   .append("        a.doctor_license_no, ")
			   .append("        a.appointment_date, ")
			   .append("        a.appointment_time, ")
			   .append("        a.requirement, ")
			   .append("        a.status, ")
			   .append("        a.created_at, ")
			   .append("        a.canceled_at, ")
			   .append("        dept.dept_name department_name, ")
			   .append("        d.name doctor_name, ")
			   .append("        CASE ")
			   .append("             WHEN a.appointment_date >= TRUNC(SYSDATE) ")
			   .append("              AND NVL(TRIM(a.status), ' ') <> '예약취소' ")
			   .append("             THEN 'Y' ")
			   .append("             ELSE 'N' ")
			   .append("        END cancelable_yn ")
			   .append(" FROM appointment a ")
			   .append(" JOIN doctor d ")
			   .append("   ON a.doctor_license_no = d.doctor_license_no ")
			   .append(" JOIN department dept ")
			   .append("   ON d.dept_no = dept.dept_no ")
			   .append(" WHERE a.patient_no = ? ")
			   .append(" AND a.appointment_date >= ADD_MONTHS(TRUNC(SYSDATE), -3) ")
			   .append(" ORDER BY a.appointment_date DESC, a.appointment_time DESC ");

			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, patientNo);

			rs = pstmt.executeQuery();

			UserAppointmentDTO uaDTO = null;

			while (rs.next()) {
				uaDTO = new UserAppointmentDTO();

				uaDTO.setAppointmentNo(rs.getString("appointment_no"));
				uaDTO.setPatientNo(rs.getString("patient_no"));
				uaDTO.setDoctorLicenseNo(rs.getInt("doctor_license_no"));
				uaDTO.setAppointmentDate(rs.getDate("appointment_date"));
				uaDTO.setAppointmentTime(rs.getString("appointment_time"));
				uaDTO.setRequirement(rs.getString("requirement"));
				// CHAR 타입 상태값 뒤에 붙을 수 있는 공백을 제거하여 화면 색상 비교를 정상화한다.
				String appointmentStatus = rs.getString("status");
				uaDTO.setStatus(appointmentStatus == null ? null : appointmentStatus.trim());
				uaDTO.setCreatedAt(rs.getDate("created_at"));
				uaDTO.setCanceledAt(rs.getDate("canceled_at"));
				uaDTO.setDepartmentName(rs.getString("department_name"));
				uaDTO.setDoctorName(rs.getString("doctor_name"));
				uaDTO.setCancelable("Y".equals(rs.getString("cancelable_yn")));

				list.add(uaDTO);
			}//end while

		} finally {
			DBConnection.close(rs, pstmt, con);
		}//end finally

		return list;
	}//selectManageAppointmentList

	/**
	 * 환자번호에 해당하는 진료 기록을 최신 진료일순으로 조회한다.
	 *
	 * @param patientNo 환자번호
	 * @return 진료 기록 및 진료과·의료진 정보를 담은 목록
	 * @throws SQLException 진료 기록 조회 중 DB 오류가 발생한 경우
	 */
	public List<UserMedicalRecordDTO> selectMedicalRecordList(String patientNo) throws SQLException {

		List<UserMedicalRecordDTO> list = new ArrayList<UserMedicalRecordDTO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBConnection.getConnection();

			StringBuilder sql = new StringBuilder();

			sql.append(" SELECT mr.record_no, ")
			   .append("        mr.appointment_no, ")
			   .append("        mr.patient_no, ")
			   .append("        mr.treatment_date, ")
			   .append("        mr.status, ")
			   .append("        dept.dept_name, ")
			   .append("        d.name doctor_name ")
			   .append(" FROM medical_record mr ")
			   .append(" LEFT JOIN appointment a ")
			   .append("   ON mr.appointment_no = a.appointment_no ")
			   .append(" LEFT JOIN doctor d ")
			   .append("   ON a.doctor_license_no = d.doctor_license_no ")
			   .append(" LEFT JOIN department dept ")
			   .append("   ON d.dept_no = dept.dept_no ")
			   .append(" WHERE mr.patient_no = ? ")
			   .append(" ORDER BY mr.treatment_date DESC ");

			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, patientNo);

			rs = pstmt.executeQuery();

			UserMedicalRecordDTO umrDTO = null;

			while (rs.next()) {
				umrDTO = new UserMedicalRecordDTO();

				umrDTO.setRecordNo(rs.getString("record_no"));
				umrDTO.setAppointmentNo(rs.getString("appointment_no"));
				umrDTO.setPatientNo(rs.getString("patient_no"));
				umrDTO.setTreatmentDate(rs.getDate("treatment_date"));
				// 상태값의 불필요한 공백을 제거하여 화면 표시와 필터 비교에 사용한다.
				String medicalStatus = rs.getString("status");
				umrDTO.setStatus(medicalStatus == null ? null : medicalStatus.trim());
				umrDTO.setDeptName(rs.getString("dept_name"));
				umrDTO.setDoctorName(rs.getString("doctor_name"));

				list.add(umrDTO);
			}//end while

		} finally {
			DBConnection.close(rs, pstmt, con);
		}//end finally

		return list;
	}//selectMedicalRecordList

	/**
	 * 로그인 회원 본인의 예약을 취소 상태로 변경한다.
	 * 이미 취소된 예약은 다시 수정하지 않는다.
	 *
	 * @param appointmentNo 취소할 예약번호
	 * @param patientNo 로그인 회원의 환자번호
	 * @return 수정된 예약 행의 수
	 * @throws SQLException 예약 상태 변경 중 DB 오류가 발생한 경우
	 */
	public int updateAppointmentCancel(String appointmentNo, String patientNo) throws SQLException {

		int cnt = 0;

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DBConnection.getConnection();

			StringBuilder sql = new StringBuilder();

			sql.append(" UPDATE appointment ")
			   .append(" SET status = '예약취소', ")
			   .append("     canceled_at = SYSDATE ")
			   .append(" WHERE appointment_no = ? ")
			   .append(" AND patient_no = ? ")
			   .append(" AND NVL(TRIM(status), ' ') <> '예약취소' ");

			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, appointmentNo);
			pstmt.setString(2, patientNo);

			cnt = pstmt.executeUpdate();

		} finally {
			DBConnection.close(null, pstmt, con);
		}//end finally

		return cnt;
	}//updateAppointmentCancel
}//class
