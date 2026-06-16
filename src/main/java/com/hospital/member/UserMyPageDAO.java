package com.hospital.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hospital.common.DBConnection;
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
				uaDTO.setStatus(rs.getString("status"));
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
				umrDTO.setStatus(rs.getString("status"));
				umrDTO.setDeptName(rs.getString("dept_name"));
				umrDTO.setDoctorName(rs.getString("doctor_name"));

				list.add(umrDTO);
			}//end while

		} finally {
			DBConnection.close(rs, pstmt, con);
		}//end finally

		return list;
	}//selectMedicalRecordList

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
			   .append(" AND patient_no = ? ");

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