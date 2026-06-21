package com.hospital.admin.doctor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hospital.admin.doctor.dto.AdminDoctorFormDTO;
import com.hospital.admin.doctor.dto.AdminDoctorSearchDTO;
import com.hospital.common.DBConnection;
import com.hospital.common.dto.DoctorCareerDTO;
import com.hospital.common.dto.DoctorDTO;
import com.hospital.common.dto.DoctorEducationDTO;
import com.hospital.common.dto.DoctorScheduleDTO;

public class AdminDoctorDAO {
	private static AdminDoctorDAO adminDoctorDAO; 
	
	private AdminDoctorDAO() {
	}//AdminDoctorDAO
	public static AdminDoctorDAO getInstance() {
		if(adminDoctorDAO == null) {
			adminDoctorDAO = new AdminDoctorDAO();
		}// end if
		return adminDoctorDAO;
	}//getInstance
	
	public int selectDoctorTotalCnt(AdminDoctorSearchDTO searchDTO) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int totalCnt = 0;
		
		String selectSql="select count(*) from doctor";
		
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(selectSql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				totalCnt=rs.getInt(0);
			}// end if
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs,pstmt,conn);
		}// end try catch
		
		return totalCnt;
	}//selectDoctorTotalCnt
	
	public List<DoctorDTO> selectDoctorList(AdminDoctorSearchDTO searchDTO ) {
		List<DoctorDTO> list = new ArrayList<DoctorDTO>();
		AdminDoctorSearchDTO adminDoctorSearchDTO = searchDTO;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String andCulmn="";
		boolean firstSearchCulmn=false;
		
		StringBuilder selectSql = new StringBuilder();
		selectSql
			.append("	select num, doctor_license_no, dept_no, name, phone_num, position_code, intro_title, intro_content, thumbnail_url, detail_image_url, create_date, specialty, status_code		")
			.append("	from (select rownum num, doctor_license_no, dept_no, name, phone_num, position_code, intro_title, intro_content, thumbnail_url, detail_image_url, create_date, specialty, status_code from doctor)		")
			.append("	where "	);
		
		
		if(!adminDoctorSearchDTO.getDeptNo().isEmpty()) {
			selectSql
				.append(andCulmn)
				.append("	dept_no = '")
				.append(adminDoctorSearchDTO.getDeptNo())
				.append("'		");
			
			if(!firstSearchCulmn) { 
				firstSearchCulmn = true;
				andCulmn = "	and	"; 
			}// end if
		}//end if
		
		if(!adminDoctorSearchDTO.getPositionCode().isEmpty()) {
			selectSql
				.append(andCulmn)
				.append("	position_code ='")
				.append(adminDoctorSearchDTO.getPositionCode())
				.append("'		");
			
			if(!firstSearchCulmn) { 
				firstSearchCulmn = true;
				andCulmn = "	and	"; 
			}// end if
		}// end if
		
		if(!adminDoctorSearchDTO.getStatusCode().isEmpty()) {
			selectSql
				.append(andCulmn)
				.append("	status_code = '		")
				.append(adminDoctorSearchDTO.getStatusCode())
				.append("'		");
			
			if(!firstSearchCulmn) { 
				firstSearchCulmn = true;
				andCulmn = "	and	"; 
			}// end if
		}// end if
		
		if(!adminDoctorSearchDTO.getSpecialty().isEmpty()) {
			selectSql
				.append(andCulmn)
				.append("	spacialty like %")
				.append(adminDoctorSearchDTO.getSpecialty())
				.append("%		");
			
			if(!firstSearchCulmn) { 
				firstSearchCulmn = true;
				andCulmn = "	and	"; 
			}// end if
		}// end if
		
		if(adminDoctorSearchDTO.getStartNum() > 0 && adminDoctorSearchDTO.getEndNum() > 0) {
			selectSql
				.append(andCulmn)
				.append("	num between ")
				.append(adminDoctorSearchDTO.getStartNum())
				.append("	and		")
				.append(adminDoctorSearchDTO.getEndNum());
			
			if(!firstSearchCulmn) { 
				firstSearchCulmn = true;
				andCulmn = "	and	"; 
			}// end if
		}// end if
				
		try {
			selectSql.append(";");
			
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(selectSql.toString());
			
			rs = pstmt.executeQuery();
			
			DoctorDTO doctorDTO = null;
			
			while(rs.next()) {
				doctorDTO = new DoctorDTO();
				
				doctorDTO.setDoctorLicenseNo(rs.getInt("doctor_license_no"));
				doctorDTO.setDeptNo(rs.getString("dept_no"));
				doctorDTO.setName(rs.getString("name"));
				doctorDTO.setPhoneNum(rs.getString("pone_num"));
				doctorDTO.setPositionCode(rs.getString("position_code"));
				doctorDTO.setIntroTitle(rs.getString("intro_title"));
				doctorDTO.setIntroContent(rs.getString("intro_content"));
				doctorDTO.setThumbnailUrl(rs.getString("thumbnail_url"));
				doctorDTO.setDetailImageUrl(rs.getString("detail_image_url"));
				doctorDTO.setSpecialty(rs.getString("specialty"));
				
				list.add(doctorDTO);
			}// end while
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs,pstmt,conn);
		}// end try catch
		
		return list;
	}// selectDoctorList
	
	public DoctorDTO selectDoctorDetail(int doctorLicenseNo) {
		DoctorDTO doctorDTO = new DoctorDTO();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String selectSql="select * from doctor";
		
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(selectSql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				doctorDTO.setDoctorLicenseNo(rs.getInt("doctor_license_no"));
				doctorDTO.setDeptNo(rs.getString("dept_no"));
				doctorDTO.setName(rs.getString("name"));
				doctorDTO.setPhoneNum(rs.getString("pone_num"));
				doctorDTO.setPositionCode(rs.getString("position_code"));
				doctorDTO.setIntroTitle(rs.getString("intro_title"));
				doctorDTO.setIntroContent(rs.getString("intro_content"));
				doctorDTO.setThumbnailUrl(rs.getString("thumbnail_url"));
				doctorDTO.setDetailImageUrl(rs.getString("detail_image_url"));
				doctorDTO.setSpecialty(rs.getString("specialty"));
				doctorDTO.setStatusCode(rs.getString("status_code"));
			}// end if
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs,pstmt,conn);
		}// end try catch
		
		return doctorDTO;
	}//selectDoctorDetail
	
	public int insertDoctor(AdminDoctorFormDTO adminDoctorFormDTO) {
		AdminDoctorFormDTO adminDoctorFormDTOTemp = adminDoctorFormDTO;
		int insertCnt=0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuilder insertSql = new StringBuilder();
		
		try {
			insertSql
			.append("	insert all 	")
			.append("	into doctor( doctor_license_no, dept_no, name, phone_num, position_code, intro_title, intro_content, thumbnail_url, detail_image_url, create_date, specialty, status_code)		")
			.append("	values( ?, ?, ?, ?, ?, ?, ?, ?, ?,sysdate, ?, ? )		");
			
			//career
			for(int i =0; i < adminDoctorFormDTOTemp.getCareerList().size(); i++) {
				insertSql
					.append("	into doctor_career (career_no, doctor_license_no, career_year, career_content)		")
					.append("	values( get_cSeq(), ?, ?, ?)		");
			}// end for
			
			//education
			for(int i = 0; i < adminDoctorFormDTOTemp.getEducationList().size(); i++) {
				insertSql
					.append("	into doctor_education (education_no, doctor_license_no, education_year, education_content)		")
					.append("	values( get_eSeq(), ?, ?, ?)");
			}// end for
			
			//schedule
			for(int i = 1; i < 8; i++) {
				insertSql
					.append("	into doctor_schedule (schedule_no, doctor_license_no, day_of_week, start_time, end_time, status)		")
					.append("	values ( get_sSeq(), ?, ")
					.append(i)
					.append("	, ?, ?, ?)			");
			}// end for
			
			conn = DBConnection.getConnection();
			insertSql.append("select * from dual;");
			
			pstmt = conn.prepareStatement(insertSql.toString());
			
			//doctor_license_no, dept_no, name, phone_num, position_code, intro_title, intro_content, thumbnail_url, detail_image_url, specialty, status_code
			pstmt.setInt(1, adminDoctorFormDTOTemp.getDoctorDTO().getDoctorLicenseNo());
			pstmt.setString(2, adminDoctorFormDTOTemp.getDoctorDTO().getDeptNo());
			pstmt.setString(3, adminDoctorFormDTOTemp.getDoctorDTO().getName());
			pstmt.setString(4, adminDoctorFormDTOTemp.getDoctorDTO().getPhoneNum());
			pstmt.setString(5, adminDoctorFormDTOTemp.getDoctorDTO().getPositionCode());
			pstmt.setString(6, adminDoctorFormDTOTemp.getDoctorDTO().getIntroTitle());
			pstmt.setString(7, adminDoctorFormDTOTemp.getDoctorDTO().getIntroContent());
			pstmt.setString(8, adminDoctorFormDTOTemp.getDoctorDTO().getThumbnailUrl());
			pstmt.setString(9, adminDoctorFormDTOTemp.getDoctorDTO().getDetailImageUrl());
			pstmt.setString(10, adminDoctorFormDTOTemp.getDoctorDTO().getSpecialty());
			pstmt.setString(11, adminDoctorFormDTOTemp.getDoctorDTO().getStatusCode());
			
			int markCnt = 12;
			
			//career
			for(int i =0; i < adminDoctorFormDTOTemp.getCareerList().size(); i++) {
				// doctor_license_no, career_year, career_content
				pstmt.setInt(markCnt++,adminDoctorFormDTOTemp.getCareerList().get(i).getDoctorLicenseNo());
				pstmt.setString(markCnt++, adminDoctorFormDTOTemp.getCareerList().get(i).getCareerYear());
				pstmt.setString(markCnt++, adminDoctorFormDTOTemp.getCareerList().get(i).getCareerContent());
			}// end for
			
			//education
			for(int i = 0; i < adminDoctorFormDTOTemp.getEducationList().size(); i++) {
				// doctor_license_no, education_year, education_content
				pstmt.setInt(markCnt++, adminDoctorFormDTOTemp.getEducationList().get(i).getDoctorLicenseNo());
				pstmt.setString(markCnt++, adminDoctorFormDTOTemp.getEducationList().get(i).getEducationYear());
				pstmt.setString(markCnt++, adminDoctorFormDTOTemp.getEducationList().get(i).getEducationContent());
			}// end for
			
			//schedule
			for(int i = 0; i < 7; i++) {
				// doctor_license_no, day_of_week, start_time, end_time, status
				pstmt.setInt(markCnt++, adminDoctorFormDTOTemp.getScheduleList().get(i).getDoctorLicenseNo());
				pstmt.setInt(markCnt++, adminDoctorFormDTOTemp.getScheduleList().get(i).getDayOfWeek());
				pstmt.setString(markCnt++, adminDoctorFormDTOTemp.getScheduleList().get(i).getStartTime());
				pstmt.setString(markCnt++, adminDoctorFormDTOTemp.getScheduleList().get(i).getEndTime());
				pstmt.setString(markCnt, adminDoctorFormDTOTemp.getScheduleList().get(i).getStatus());
			}// end for
			
			insertCnt = pstmt.executeUpdate();
		
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs,pstmt,conn);
		}// end try catch
		
		return insertCnt;
	}// insertDoctor
	
	public int updateDoctor(DoctorDTO doctorDTO) {
		DoctorDTO doctorDTOTemp = doctorDTO;
		int updateCnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		StringBuilder updateSql = new StringBuilder();
		updateSql
		.append("	update doctor		")
		.append("	set dept_No=?, name=?, phone_Num=?, position_Code=?, intro_Title=?,		")
		.append("	intro_Content=?, thumbnail_Url=?, detail_Image_Url=?, specialty=?, status_Code=?		")
		.append("	where doctor_License_No=?;		");
		
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(updateSql.toString());
			
			pstmt.setString(1, doctorDTOTemp.getDeptNo());
			pstmt.setString(2, doctorDTOTemp.getName());
			pstmt.setString(3, doctorDTOTemp.getPhoneNum());
			pstmt.setString(4, doctorDTOTemp.getPositionCode());
			pstmt.setString(5, doctorDTOTemp.getIntroTitle());
			pstmt.setString(6, doctorDTOTemp.getIntroContent());
			pstmt.setString(7, doctorDTOTemp.getThumbnailUrl());
			pstmt.setString(8, doctorDTOTemp.getDetailImageUrl());
			pstmt.setString(9, doctorDTOTemp.getSpecialty());
			pstmt.setString(10, doctorDTOTemp.getStatusCode());
			pstmt.setInt(11, doctorDTOTemp.getDoctorLicenseNo());
			
			updateCnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(pstmt,conn);
		}// end try catch
		
		return updateCnt;
	}//updateDoctor
	
	public int updateDoctorStatus(int doctorLicenseNo, String statusCode) {
		DoctorDTO doctorDTO = new DoctorDTO();
		int updateCnt = 0;
		
		
		return updateCnt;
	}// updateDoctorStatus
	
	public int selectDoctorLicenseNoCnt(int doctorLicenseNo) {
		int doctorCnt = 0;
		
		return doctorCnt;
	}//selectDoctorLicenseNoCnt
	
	public List<DoctorScheduleDTO> selectDoctorSchedules(int doctorLicenseNo){
		List<DoctorScheduleDTO> list = new ArrayList<DoctorScheduleDTO>();
		
		return list;
	}//selectDoctorSchedules
	
	public int deleteDoctorSchedules(int doctorLicenseNo) {
		int deleteCnt = 0;
		
		return deleteCnt;
	}//selectDoctorSchedules
	
	public int insertDoctorSchedule(DoctorScheduleDTO scheduleDTO) {
		int insertCnt = 0;
		
		return insertCnt;
	}// insertDoctorSchedule
	
	public int deleteDoctorEducations(int doctorLicenseNo) {
		int deleteCnt = 0;
		
		return deleteCnt;
	}// deleteDoctorEducations
	
	public int insertDoctorEducation(DoctorEducationDTO educationDTO) {
		int insertCnt = 0;
		
		return insertCnt;
	}// insertDoctorEducation
	
	public int deleteDoctorCareers(int doctorLicenseNo) {
		int deleteCnt = 0;
		
		return deleteCnt;
	}// deleteDoctorCareers
	public int insertDoctorCareer(DoctorCareerDTO careerDTO) {
		int insertCnt = 0;
		
		return insertCnt;
	}//insertDoctorCareer
	
}// class
