package com.hospital.admin.doctor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hospital.admin.doctor.dto.AdminDoctorFormDTO;
import com.hospital.admin.doctor.dto.AdminDoctorSearchDTO;
import com.hospital.common.dto.DoctorCareerDTO;
import com.hospital.common.dto.DoctorDTO;
import com.hospital.common.dto.DoctorEducationDTO;
import com.hospital.common.dto.DoctorPositionDTO;
import com.hospital.common.dto.DoctorScheduleDTO;
import com.hospital.common.dto.DoctorStatusDTO;
import com.hospital.common.util.DBConnection;

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
	
	public List<DoctorDTO> selectDoctorList() {
		List<DoctorDTO> list = new ArrayList<DoctorDTO>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String andCulmn="";
		boolean firstSearchCulmn=false;
		
		StringBuilder selectSql = new StringBuilder();
		selectSql
			.append("	select * from doctor		");
				
		try {
			
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(selectSql.toString());
			
			rs = pstmt.executeQuery();
			
			DoctorDTO doctorDTO = null;
			
			while(rs.next()) {
				doctorDTO = new DoctorDTO();
				
				doctorDTO.setDoctorLicenseNo(rs.getInt("doctor_license_no"));
				doctorDTO.setDeptNo(rs.getString("dept_no"));
				doctorDTO.setName(rs.getString("name"));
				doctorDTO.setPhoneNum(rs.getString("phone_num"));
				doctorDTO.setPositionCode(rs.getString("position_code"));
				doctorDTO.setIntroTitle(rs.getString("intro_title"));
				doctorDTO.setStatusCode(rs.getString("status_code"));
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
			.append("	select doctor_license_no, dept_no, name, phone_num, position_code, intro_title, intro_content, thumbnail_url, detail_image_url, create_date, specialty, status_code		")
			.append("	from doctor		")
			.append("	where "	);
		
		
		if( adminDoctorSearchDTO.getDeptNo()!=null ) {
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
		
		if( adminDoctorSearchDTO.getName()!=null ) {
			selectSql
				.append(andCulmn)
				.append("	name = '")
				.append(adminDoctorSearchDTO.getName())
				.append("'		");
			
			if(!firstSearchCulmn) { 
				firstSearchCulmn = true;
				andCulmn = "	and	"; 
			}// end if
		}//end if
		
		if(adminDoctorSearchDTO.getPositionCode()!=null ) {
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
		
		if(adminDoctorSearchDTO.getStatusCode()!=null ) {
			selectSql
				.append(andCulmn)
				.append("	status_code = '")
				.append(adminDoctorSearchDTO.getStatusCode())
				.append("'		");
			
			if(!firstSearchCulmn) { 
				firstSearchCulmn = true;
				andCulmn = "	and	"; 
			}// end if
		}// end if
		
		if(adminDoctorSearchDTO.getSpecialty()!=null ) {
			selectSql
				.append(andCulmn)
				.append("	spacialty like '%")
				.append(adminDoctorSearchDTO.getSpecialty())
				.append("%'		");
			
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
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(selectSql.toString());
			
			rs = pstmt.executeQuery();
			
			DoctorDTO doctorDTO = null;
			
			while(rs.next()) {
				doctorDTO = new DoctorDTO();
				
				doctorDTO.setDoctorLicenseNo(rs.getInt("doctor_license_no"));
				doctorDTO.setDeptNo(rs.getString("dept_no"));
				doctorDTO.setName(rs.getString("name"));
				doctorDTO.setPhoneNum(rs.getString("phone_num"));
				doctorDTO.setPositionCode(rs.getString("position_code"));
				doctorDTO.setStatusCode(rs.getString("status_code"));
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
		
		int doctorLicenseNoTemp = doctorLicenseNo;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuilder selectSql=new StringBuilder();
		
		selectSql
			.append("	select * from doctor		")
			.append("	where doctor_license_no = ?		");
			
		
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(selectSql.toString());
			
			pstmt.setInt(1, doctorLicenseNoTemp);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				doctorDTO.setDoctorLicenseNo(rs.getInt("doctor_license_no"));
				doctorDTO.setDeptNo(rs.getString("dept_no"));
				doctorDTO.setName(rs.getString("name"));
				doctorDTO.setPhoneNum(rs.getString("phone_num"));
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
	
	public int insertDoctor(AdminDoctorFormDTO adminDoctorFormDTO) {// DAO딴에서 포문으로 sql을 생성하여 한번에 execute..
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
					.append("	values ( get_sSeq(), ?, ?, ?, ?, ?) ");
			}// end for
			
			conn = DBConnection.getConnection();
			insertSql.append("select * from dual");
			
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
				pstmt.setString(markCnt++, adminDoctorFormDTOTemp.getScheduleList().get(i).getStatus());
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
		.append("	where doctor_License_No=?		");
		
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
		int updateCnt = 0;
		
		int doctorLicenseNoTemp = doctorLicenseNo;
		String statusCodeTemp = statusCode;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		StringBuilder updateSql = new StringBuilder();
		updateSql
		.append("	update doctor		")
		.append("	set status_Code=?		")
		.append("	where doctor_License_No=?		");
		
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(updateSql.toString());
			
			pstmt.setString(1, statusCodeTemp);
			pstmt.setInt(2, doctorLicenseNoTemp);
			
			updateCnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(pstmt,conn);
		}// end try catch
		
		return updateCnt;
	}// updateDoctorStatus
	
	public int selectDoctorLicenseNoCnt(int doctorLicenseNo) {
		int doctorCnt = 0;
		int doctorLicenseNoTemp = doctorLicenseNo;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String selectSql="select count(*) from doctor where doctor_license_no = ?";
		
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(selectSql);
			
			pstmt.setInt(1, doctorLicenseNoTemp);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				doctorCnt = rs.getInt(1);
			}// end if
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs,pstmt,conn);
		}// end try catch
		
		return doctorCnt;
	}//selectDoctorLicenseNoCnt
	
	public List<DoctorScheduleDTO> selectDoctorSchedules(int doctorLicenseNo){
		List<DoctorScheduleDTO> list = new ArrayList<DoctorScheduleDTO>();
		
		int doctorLicenseNoTemp = doctorLicenseNo;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String selectSql="select * from doctor_schedule where doctor_license_no = ?";
		
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(selectSql);
			
			pstmt.setInt(1, doctorLicenseNoTemp);
			
			rs = pstmt.executeQuery();
			
			DoctorScheduleDTO doctorScheduleDTO = null;
			
			while(rs.next()) {
				
				doctorScheduleDTO = new DoctorScheduleDTO();
				doctorScheduleDTO.setDoctorLicenseNo(rs.getInt("doctor_license_no"));
				doctorScheduleDTO.setDayOfWeek(rs.getInt("day_of_week"));
				doctorScheduleDTO.setScheduleNo(rs.getInt("schedule_no"));
				doctorScheduleDTO.setStatus(rs.getString("status"));
				doctorScheduleDTO.setStartTime(rs.getString("start_time"));
				doctorScheduleDTO.setEndTime(rs.getString("end_time"));
				
				list.add(doctorScheduleDTO);
			}// end while
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs,pstmt,conn);
		}// end try catch
		
		return list;
	}//selectDoctorSchedules
	
	public int deleteDoctorSchedules(int doctorLicenseNo) {
		 int deleteCnt = 0;
		 int doctorLicenseNoTemp = doctorLicenseNo;
		    Connection conn = null;
		    PreparedStatement pstmt = null;

		    StringBuilder deleteSql = new StringBuilder();
		    deleteSql
		    	.append("	delete from doctor_schedule		")
		    	.append("	where doctor_license_no=?		");
		   
		    try {

		        conn = DBConnection.getConnection();

		        pstmt = conn.prepareStatement(deleteSql.toString());

		        pstmt.setInt(1, doctorLicenseNoTemp);

		        deleteCnt = pstmt.executeUpdate();

		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        DBConnection.close(pstmt, conn);
		    }

		    return deleteCnt;
	}//selectDoctorSchedules
	
	public int updateDoctorSchedules(int doctorLicenseNo, DoctorScheduleDTO scheduleDTO) {
		DoctorScheduleDTO doctorScheduleDTO = scheduleDTO; 
		int doctorLicenseNoTemp = doctorLicenseNo;
		StringBuilder updateSql = new StringBuilder();
		int successCnt = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBConnection.getConnection();
			
			updateSql
			.append("	update doctor_schedule")
			.append("	set day_of_week=?, 		")
			.append("	start_time=?, 		")
			.append("	end_time=?, 		")
			.append("	status=?  		")
			.append("	where doctor_license_no=? 		")
			.append("	and schedule_no=? 		");
				
			pstmt = conn.prepareStatement(updateSql.toString());

	        pstmt.setInt(1, doctorScheduleDTO.getDayOfWeek());
	        pstmt.setString(2, doctorScheduleDTO.getStartTime());
	        pstmt.setString(3, doctorScheduleDTO.getEndTime());
	        pstmt.setString(4, doctorScheduleDTO.getStatus());
	        pstmt.setInt(5, doctorLicenseNoTemp);
	        pstmt.setInt(6, doctorScheduleDTO.getScheduleNo());

	        successCnt = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(pstmt,conn);
		}// end try catch
		return successCnt; 
	}
	
	public int insertDoctorSchedule(DoctorScheduleDTO scheduleDTO, int dayOfWeek) {
		int insertCnt = 0;
		int dayOfWeekTemp = dayOfWeek;
		DoctorScheduleDTO doctorScheduleDTO = scheduleDTO;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuilder insertSql= new StringBuilder(); 
		
		try {
			conn = DBConnection.getConnection();

			insertSql
				.append(" insert all into doctor_schedule(schedule_no, doctor_license_no, day_of_week, start_time, end_time, status)		")
				.append("	values(get_sSeq(),?,?,?,?,? )		");
			
			pstmt = conn.prepareStatement(insertSql.toString());
		
			pstmt.setInt(1, doctorScheduleDTO.getDoctorLicenseNo());
			pstmt.setInt(2, dayOfWeekTemp);
			pstmt.setString(3, doctorScheduleDTO.getStartTime());
			pstmt.setString(4, doctorScheduleDTO.getEndTime());
			pstmt.setString(5, doctorScheduleDTO.getStatus());
		
			insertCnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs,pstmt,conn);
		}// end try catch
		
		return insertCnt;
	}// insertDoctorSchedule
	
	public List<DoctorCareerDTO> selectDoctorCareerList(int doctorLicenseNo){
		List<DoctorCareerDTO> list = new ArrayList<DoctorCareerDTO>();
		int doctorLicenseNoTemp = doctorLicenseNo;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuilder selectSql = new StringBuilder();
		selectSql
			.append("	select * from doctor_career		")
			.append("	where doctor_license_no = ?		");
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(selectSql.toString());
			pstmt.setInt(1, doctorLicenseNoTemp);
			
			rs = pstmt.executeQuery();
			DoctorCareerDTO doctorCareerDTO = null;
			while(rs.next()) {
				doctorCareerDTO = new DoctorCareerDTO();
				
				doctorCareerDTO.setCareerNo(rs.getInt("career_no"));
				doctorCareerDTO.setDoctorLicenseNo(rs.getInt("doctor_license_no"));
				doctorCareerDTO.setCareerYear(rs.getString("career_year"));
				doctorCareerDTO.setCareerContent(rs.getString("career_content"));
				
				list.add(doctorCareerDTO);
			}// end while
			
		}  catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs,pstmt,conn);
		}// end try catch
		
		
		return list;
	}
	
	public int deleteDoctorCareers(int doctorLicenseNo, int careerNo ) {
		int doctorLicenseNoTemp = doctorLicenseNo;
		int careerNoTemp = careerNo;
		int deleteCnt = 0;
		
		    Connection conn = null;
		    PreparedStatement pstmt = null;
		
		    StringBuilder deleteSql = new StringBuilder();
		    
		try {
		
		    conn = DBConnection.getConnection();
		    
		    deleteSql
			    .append("	delete from doctor_career		")
			    .append("	where doctor_license_no=?		")
			    .append("	and career_no=?		");
		
		    pstmt = conn.prepareStatement(deleteSql.toString());
		
		    pstmt.setInt(1, doctorLicenseNoTemp);
		    pstmt.setInt(2, careerNoTemp);
		
		    deleteCnt = pstmt.executeUpdate();
		
		} catch (SQLException e) {
		    e.printStackTrace();
		} finally {
		    DBConnection.close(pstmt, conn);
		}
		
		return deleteCnt;
	}// deleteDoctorCareers
	
	public int insertDoctorCareer(DoctorCareerDTO careerDTO) {
		int insertCnt = 0;
		DoctorCareerDTO doctorCareerDTO = careerDTO;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuilder insertSql= new StringBuilder(); 
		
		try {
			conn = DBConnection.getConnection();

			insertSql
				.append(" insert into doctor_career(career_no, doctor_license_no, career_year, career_content)		")
				.append("	values(get_cSeq(),?,?,? )		");
			
			pstmt = conn.prepareStatement(insertSql.toString());
		
			pstmt.setInt(1, doctorCareerDTO.getDoctorLicenseNo());
			pstmt.setString(2, doctorCareerDTO.getCareerYear());
			pstmt.setString(3, doctorCareerDTO.getCareerContent());
		
			insertCnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs,pstmt,conn);
		}// end try catch
		
		return insertCnt;
	}//insertDoctorCareer

	public boolean selectDoctorCareerChk(int doctorLicenseNo, int careerNo) {
		
		boolean chkSelect = false;
		
		int doctorLicenseNoTemp = doctorLicenseNo;
		int careerNoTemp = careerNo;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuilder selectSql = new StringBuilder();
		selectSql
			.append("	select * from doctor_career		")
			.append("	where doctor_license_no = ?	and career_no = ?	");
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(selectSql.toString());
			pstmt.setInt(1, doctorLicenseNoTemp);
			pstmt.setInt(2, careerNoTemp);
			
			rs = pstmt.executeQuery();
			
			chkSelect = rs.next();
			
		}  catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs,pstmt,conn);
		}// end try catch
		
		return chkSelect;
	}
	
	public int updateDoctorCareer(int doctorLicenseNo, DoctorCareerDTO careerDTO) {
		DoctorCareerDTO doctorCareerDTO = careerDTO; 
		int doctorLicenseNoTemp = doctorLicenseNo;
		StringBuilder updateSql = new StringBuilder();
		int successCnt = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnection.getConnection();
			updateSql
				.append("	update doctor_career")
				.append("	set career_year = ?, career_content = ?		")
				.append("	where doctor_license_no = ? and career_no = ?		");
				
			pstmt=conn.prepareStatement(updateSql.toString());
			
			pstmt.setString(1, doctorCareerDTO.getCareerYear());
			pstmt.setString(2, doctorCareerDTO.getCareerContent());
			pstmt.setInt(3, doctorLicenseNoTemp);
			pstmt.setInt(4, doctorCareerDTO.getCareerNo());
			
			successCnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs,pstmt,conn);
		}// end try catch
		return successCnt; 
	}
	
	
	public int deleteDoctorEducations(int doctorLicenseNo, int educationNo) {
		int doctorLicenseNoTemp = doctorLicenseNo;
		int educationNoTemp = educationNo;
		int deleteCnt = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuilder deleteSql = new StringBuilder();
		
		deleteSql
			.append("	delete from doctor_education		")
			.append("	where doctor_license_no = ? and education_no = ?		");
		
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(deleteSql.toString());
			
			pstmt.setInt(1, doctorLicenseNoTemp);
			pstmt.setInt(2, educationNoTemp);
			
			deleteCnt = pstmt.executeUpdate();
			
		}  catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs,pstmt,conn);
		}// end try catch
		
		return deleteCnt;
	}// deleteDoctorEducations
	
	public List<DoctorEducationDTO> selectDoctorEducationList(int doctorLicenseNo){
		List<DoctorEducationDTO> list = new ArrayList<DoctorEducationDTO>();
		int doctorLicenseNoTemp = doctorLicenseNo;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuilder selectSql = new StringBuilder();
		selectSql
			.append("	select * from doctor_education		")
			.append("	where doctor_license_no = ?		");
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(selectSql.toString());
			pstmt.setInt(1, doctorLicenseNoTemp);
			
			rs = pstmt.executeQuery();
			DoctorEducationDTO doctorEducationDTO = null;
			while(rs.next()) {
				doctorEducationDTO = new DoctorEducationDTO();
				
				doctorEducationDTO.setEducationNo(rs.getInt("education_no"));
				doctorEducationDTO.setDoctorLicenseNo(rs.getInt("doctor_license_no"));
				doctorEducationDTO.setEducationYear(rs.getString("education_year"));
				doctorEducationDTO.setEducationContent(rs.getString("education_content"));
				
				list.add(doctorEducationDTO);
			}// end while
			
		}  catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs,pstmt,conn);
		}// end try catch
		
		
		return list;
	}
	
	public int updateDoctorEducation(int doctorLicenseNo, DoctorEducationDTO educationDTO) {
		DoctorEducationDTO doctorEducationDTO = educationDTO; 
		int doctorLicenseNoTemp = doctorLicenseNo;
		StringBuilder updateSql = new StringBuilder();
		int successCnt = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnection.getConnection();
			updateSql
				.append("	update doctor_education")
				.append("	set education_year = ?, education_content = ?		")
				.append("	where doctor_license_no = ? and education_no = ?		");
				
			pstmt = conn.prepareStatement(updateSql.toString());
			
			pstmt.setString(1, doctorEducationDTO.getEducationYear());
			pstmt.setString(2, doctorEducationDTO.getEducationContent());
			pstmt.setInt(3, doctorLicenseNoTemp);
			pstmt.setInt(4, doctorEducationDTO.getEducationNo());
			
			successCnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs,pstmt,conn);
		}// end try catch
		return successCnt; 
	}
	
	public int insertDoctorEducation(DoctorEducationDTO educationDTO) {
		int insertCnt = 0;
		DoctorEducationDTO doctorEducationDTO = educationDTO;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuilder insertSql= new StringBuilder(); 
		
		try {
			conn = DBConnection.getConnection();

			insertSql
				.append(" insert into doctor_education(education_no, doctor_license_no, education_year, education_content)		")
				.append("	values(get_eSeq(),?,?,? )		");
			
			pstmt = conn.prepareStatement(insertSql.toString());
		
			pstmt.setInt(1, doctorEducationDTO.getDoctorLicenseNo());
			pstmt.setString(2, doctorEducationDTO.getEducationYear());
			pstmt.setString(3, doctorEducationDTO.getEducationContent());
		
			insertCnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs,pstmt,conn);
		}// end try catch
		
		return insertCnt;
	}// insertDoctorEducation
	
	public List<DoctorPositionDTO> selectDoctorPostionAllList(){
		List<DoctorPositionDTO> list = new ArrayList<DoctorPositionDTO>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuilder selectSql = new StringBuilder();
		selectSql
			.append("	select * from doctor_position		");
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(selectSql.toString());
			
			rs = pstmt.executeQuery();
			DoctorPositionDTO doctorPositionDTO = null;
			while(rs.next()) {
				doctorPositionDTO = new DoctorPositionDTO();
				
				doctorPositionDTO.setPositionCode(rs.getString(1));
				doctorPositionDTO.setPositionName(rs.getString(2));
				
				list.add(doctorPositionDTO);
			}// end while
			
		}  catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs,pstmt,conn);
		}// end try catch
		
		return list;
	}// selectPostionList
	
	public List<DoctorStatusDTO> selectDoctorStatusAllList(){
		List<DoctorStatusDTO> list = new ArrayList<DoctorStatusDTO>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuilder selectSql = new StringBuilder();
		selectSql
			.append("	select * from doctor_status		");
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(selectSql.toString());
			
			rs = pstmt.executeQuery();
			DoctorStatusDTO doctorStatusDTO = null;
			while(rs.next()) {
				doctorStatusDTO = new DoctorStatusDTO();
				
				doctorStatusDTO.setStatusCode(rs.getString(1));
				doctorStatusDTO.setStatusName(rs.getString(2));
				
				list.add(doctorStatusDTO);
			}// end while
			
		}  catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs,pstmt,conn);
		}// end try catch
		
		return list;
	}// selectDoctorStatusList
	
	public List<DoctorEducationDTO> selectDoctorEducationAllList(){
		List<DoctorEducationDTO> list = new ArrayList<DoctorEducationDTO>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuilder selectSql = new StringBuilder();
		selectSql
			.append("	select * from doctor_education		");
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(selectSql.toString());
			
			rs = pstmt.executeQuery();
			DoctorEducationDTO doctorEducationDTO = null;
			while(rs.next()) {
				doctorEducationDTO = new DoctorEducationDTO();
				
				doctorEducationDTO.setEducationNo(rs.getInt("education_no"));
				doctorEducationDTO.setDoctorLicenseNo(rs.getInt("doctor_license_no"));
				doctorEducationDTO.setEducationYear(rs.getString("education_year"));
				doctorEducationDTO.setEducationYear(rs.getString("education_content"));
				
				list.add(doctorEducationDTO);
			}// end while
			
		}  catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs,pstmt,conn);
		}// end try catch
		
		return list;
	}// selectDoctorStatusList
	
}// class
