package com.hospital.user.doctor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hospital.common.dto.DoctorCareerDTO;
import com.hospital.common.dto.DoctorEducationDTO;
import com.hospital.common.dto.DoctorScheduleDTO;
import com.hospital.common.util.DBConnection;
import com.hospital.user.doctor.dto.UserDoctorDTO;

public class UserDoctorDAO {
	private static UserDoctorDAO udDAO;

	private UserDoctorDAO() {
	}

	public static UserDoctorDAO getInstance() {
		if (udDAO == null) {
			udDAO = new UserDoctorDAO();
		}

		return udDAO;
	}

	public UserDoctorDTO selectDoctorDetail(int doctorLicenseNo) throws SQLException {
		UserDoctorDTO udDTO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBConnection.getConnection();

			StringBuilder querySb = new StringBuilder();
			querySb //
					.append("	select	DOCTOR_LICENSE_NO, NAME, INTRO_TITLE, INTRO_CONTENT, THUMBNAIL_URL, DETAIL_IMAGE_URL, SPECIALTY, POSITION_NAME ")
					.append("	from	doctor d, doctor_position dp ") //
					.append("	where 	doctor_license_no = ? and d.position_code = dp.position_code ");

			pstmt = con.prepareStatement(querySb.toString());
			pstmt.setInt(1, doctorLicenseNo);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				udDTO = new UserDoctorDTO();

				udDTO.setDoctorLicenseNo(doctorLicenseNo);
				udDTO.setName(rs.getString("name"));
				udDTO.setIntroTitle(rs.getString("INTRO_TITLE"));
				udDTO.setIntroContent(rs.getString("INTRO_CONTENT"));
				udDTO.setThumbnailUrl(rs.getString("THUMBNAIL_URL"));
				udDTO.setDetailImageUrl(rs.getString("DETAIL_IMAGE_URL"));
				udDTO.setSpecialty(rs.getString("SPECIALTY"));
				udDTO.setPosition(rs.getString("POSITION_NAME"));
				udDTO.setDsList(selectDoctorSchedule(doctorLicenseNo, con));
				udDTO.setDeList(selectDoctorEducation(doctorLicenseNo, con));
				udDTO.setDcList(selectDoctorCareer(doctorLicenseNo, con));
			}
		} finally {
			DBConnection.close(rs, pstmt, con);
		}

		return udDTO;
	}

	private List<DoctorScheduleDTO> selectDoctorSchedule(int dln, Connection con) throws SQLException {
		List<DoctorScheduleDTO> dsList = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			StringBuilder querySb = new StringBuilder();
			querySb //
					.append("	select	DOCTOR_LICENSE_NO, DAY_OF_WEEK, START_TIME, END_TIME, STATUS ") //
					.append("	from	DOCTOR_SCHEDULE ") //
					.append("	where	DOCTOR_LICENSE_NO = ? and not status = '휴진' ");

			pstmt = con.prepareStatement(querySb.toString());

			pstmt.setInt(1, dln);

			rs = pstmt.executeQuery();

			if (rs != null) {
				dsList = new ArrayList<>();
				DoctorScheduleDTO dsDTO = null;
				while (rs.next()) {
					dsDTO = new DoctorScheduleDTO();

					dsDTO.setDoctorLicenseNo(rs.getInt("DOCTOR_LICENSE_NO"));
					dsDTO.setDayOfWeek(rs.getInt("DAY_OF_WEEK"));
					dsDTO.setStartTime(rs.getString("START_TIME"));
					dsDTO.setEndTime(rs.getString("END_TIME"));
					dsDTO.setStatus(rs.getString("STATUS"));

					dsList.add(dsDTO);
				}
			}
		} finally {
			DBConnection.close(rs, pstmt, null);
		}

		return dsList;
	}

	private List<DoctorCareerDTO> selectDoctorCareer(int dln, Connection con) throws SQLException {
		List<DoctorCareerDTO> dcList = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			StringBuilder querySb = new StringBuilder();
			querySb.append("	select	DOCTOR_LICENSE_NO, CAREER_YEAR, CAREER_CONTENT ")
					.append("	from	DOCTOR_CAREER ") //
					.append("	where	DOCTOR_LICENSE_NO = ? ");

			pstmt = con.prepareStatement(querySb.toString());

			pstmt.setInt(1, dln);

			rs = pstmt.executeQuery();

			if (rs != null) {
				dcList = new ArrayList<>();

				DoctorCareerDTO dcDTO = null;

				while (rs.next()) {
					dcDTO = new DoctorCareerDTO();

					dcDTO.setDoctorLicenseNo(rs.getInt("DOCTOR_LICENSE_NO"));
					dcDTO.setCareerYear(rs.getString("CAREER_YEAR"));
					dcDTO.setCareerContent(rs.getString("CAREER_CONTENT"));

					dcList.add(dcDTO);
				}

			}
		} finally {
			DBConnection.close(rs, pstmt, null);
		}

		return dcList;
	}

	private List<DoctorEducationDTO> selectDoctorEducation(int dln, Connection con) throws SQLException {
		List<DoctorEducationDTO> deList = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			StringBuilder querySb = new StringBuilder();
			querySb //
					.append("	select	DOCTOR_LICENSE_NO, EDUCATION_YEAR, EDUCATION_CONTENT ") //
					.append("	from	DOCTOR_EDUCATION ") //
					.append("	where	DOCTOR_LICENSE_NO = ? ");

			pstmt = con.prepareStatement(querySb.toString());

			pstmt.setInt(1, dln);

			rs = pstmt.executeQuery();

			if (rs != null) {
				deList = new ArrayList<>();

				DoctorEducationDTO deDTO = null;

				while (rs.next()) {
					deDTO = new DoctorEducationDTO();

					deDTO.setDoctorLicenseNo(rs.getInt("DOCTOR_LICENSE_NO"));
					deDTO.setEducationYear(rs.getString("EDUCATION_YEAR"));
					deDTO.setEducationContent(rs.getString("EDUCATION_CONTENT"));

					deList.add(deDTO);
				}
			}
		} finally {
			DBConnection.close(rs, pstmt, null);
		}

		return deList;
	}

}
