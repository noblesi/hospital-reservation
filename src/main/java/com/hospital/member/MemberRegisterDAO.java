package com.hospital.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hospital.common.DBConnection;
import com.hospital.common.MemberDTO;
import com.hospital.common.MinorMemberDTO;

/**
 * 회원가입 관련 DB 작업을 처리하는 DAO
 */
public class MemberRegisterDAO {
	
	private static MemberRegisterDAO mrDAO;
	
	private MemberRegisterDAO() {
		
	}//MemberRegisterDAO
	
	public static MemberRegisterDAO getInstance() {
		if(mrDAO == null) {
			mrDAO = new MemberRegisterDAO();
		}// end if 
		
		return mrDAO;
	}// getInstance
	
	/**
	 * 아이디 중복 확인 
	 * @param loginId 사용자가 입력한 아이디
	 * @return 중복된 아이디 개수
	 */
	public int selectLoginIdCount(String loginId) throws SQLException{
		int cnt = 0;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBConnection.getConnection();
			
			String selectLoginIdCnt = "select count(*) cnt" +
						 " from member " +
						 " where login_id=?";
			
			pstmt = con.prepareStatement(selectLoginIdCnt);
			pstmt.setString(1, loginId);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				cnt = rs.getInt("cnt");
			}// end if 
		}finally {
			DBConnection.close(rs, pstmt, con);
		}// end finally
		return cnt;
	}//selectLoginIdCount
	
	/**
	 * 일반 회원 가입
	 * @param mDTO 회원 정보
	 * @return 추가된 열수 
	 */
	public int insertMember(MemberDTO mDTO) throws SQLException {
		int rowCnt = 0;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBConnection.getConnection();
			
			StringBuilder insertMember = new StringBuilder();
			
			 insertMember
             .append(" insert into member( ")
             .append(" login_id, password, name, birth_date, gender_fm, ")
             .append(" phone_number, email, zip_code, address, address_detail, ")
             .append(" has_minor_member_yn, ip ")
             .append(" ) values( ")
             .append(" ?, ?, ?, ?, ?, ")
             .append(" ?, ?, ?, ?, ?, ")
             .append(" ?, ? ")
             .append(" ) ");
			 
			 pstmt = con.prepareStatement(insertMember.toString());

	            pstmt.setString(1, mDTO.getLoginId());
	            pstmt.setString(2, mDTO.getPassword());
	            pstmt.setString(3, mDTO.getName());
	            pstmt.setDate(4, mDTO.getBirthDate());
	            pstmt.setString(5, mDTO.getGenderFM());
	            pstmt.setString(6, mDTO.getPhoneNumber());
	            pstmt.setString(7, mDTO.getEmail());
	            pstmt.setString(8, mDTO.getZipCode());
	            pstmt.setString(9, mDTO.getAddress());
	            pstmt.setString(10, mDTO.getAddressDetail());
	            pstmt.setString(11, mDTO.getHasMinorMemberYn());
	            pstmt.setString(12, mDTO.getIp());

	            rowCnt = pstmt.executeUpdate();
		} finally {
			DBConnection.close(pstmt, con);
		}// end finally
		
		return rowCnt;
	}//insertMember
	
	public String selectPatientNoByLoginId(String loginId) throws SQLException {
		String patientNo = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBConnection.getConnection();
			StringBuilder selectPatientNo = new StringBuilder();
			
			selectPatientNo
				.append("	select patient_no ")
				.append("	from member		  ")
				.append("	where login_id = ? ");
			
			pstmt = con.prepareStatement(selectPatientNo.toString());
			pstmt.setString(1, loginId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				patientNo = rs.getString("patient_no");
			}//end if
		}finally {
			DBConnection.close(rs, pstmt, con);
			
		}//end finally
		return patientNo;
	}//selectPatientNoByLoginId
	
	/**
	 * 미성년자 정보 추가
	 * @param MinorDTO 미성년자 이름, 생년월일, 성별, 보호자 관계 정보 
	 * @return 추가될 행 수 
	 */
	public int insertMinorMember(MinorMemberDTO minorDTO) throws SQLException{
		int rowCnt = 0;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBConnection.getConnection();
			
			StringBuilder insertMinorMember = new StringBuilder();
			
			 insertMinorMember
             .append(" insert into minor_member( ")
             .append(" patient_no, relationship, minor_name, minor_birth_date, minor_gender_fm ")
             .append(" ) values( ")
             .append(" ?, ?, ?, ?, ? ")
             .append(" ) ");
			 
			 pstmt = con.prepareStatement(insertMinorMember.toString());
			 
			 pstmt.setString(1, minorDTO.getPatientNo());
			 pstmt.setString(2, minorDTO.getRelationship());
			 pstmt.setString(3, minorDTO.getMinorName());
			 pstmt.setDate(4, minorDTO.getMinorBirthDate());
			 pstmt.setString(5, minorDTO.getMinorGenderFM());
			 
			 rowCnt = pstmt.executeUpdate();
		} finally {
			DBConnection.close(pstmt, con);
		}// end finally
		
		return rowCnt;
	}//insertMinorMember
	
	/**
	 * 회원가입 완료 화면에서 보여줄 회원정보 조회
	 * @param loginId 가입한 회원 아이디
	 * @return 가입 결과 회원 정보
	 */
	public MemberDTO selectRegisterResult(String loginId) throws SQLException {
		MemberDTO mDTO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBConnection.getConnection();
			
			StringBuilder selectResult = new StringBuilder();
			selectResult
			.append(" select login_id, name, registered_at ")
			.append(" from member ")
			.append(" where login_id = ? ");
			
			pstmt = con.prepareStatement(selectResult.toString());
			pstmt.setString(1, loginId);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				mDTO = new MemberDTO();
				
				mDTO.setLoginId(rs.getString("login_id"));
				mDTO.setName(rs.getString("name"));
				mDTO.setRegisteredAt(rs.getDate("registered_at"));
			}//end if
			
		} finally {
			DBConnection.close(rs, pstmt, con);
		}// end finally
		
		return mDTO;
	}//selectRegisterResult
}//class
