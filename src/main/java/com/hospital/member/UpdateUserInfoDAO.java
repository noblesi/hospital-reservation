package com.hospital.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hospital.common.DBConnection;
import com.hospital.common.MemberDTO;
import com.hospital.common.MinorMemberDTO;

/**
 * UpdateUserInfoDAO
 * 마이페이지 내 회원정보 수정, 비밀번호 재확인, 미성년자 정보 조회,
 * 회원 탈퇴 관련 DB 작업을 처리하는 DAO
 */
public class UpdateUserInfoDAO {
	
	private static UpdateUserInfoDAO uDAO;
	
	private UpdateUserInfoDAO() {
	}//UpdateUserInfoDAO
	
	public static UpdateUserInfoDAO getInstance() {
		if(uDAO == null) {
			uDAO = new UpdateUserInfoDAO();
		}//end if
		return uDAO;
	}//getInstance
	
	/**
	 * 회원정보 수정 페이지 진입 전 비밀번호 재확인용 비밀번호 조회
	 *
	 * 로그인한 사용자의 비밀번호를 조회하여
	 * 사용자가 입력한 비밀번호와 비교할 때 사용한다.
	 *
	 * @param loginId 로그인 아이디
	 * @return DB에 저장된 회원 비밀번호
	 * @throws SQLException DB 처리 중 오류 발생 시
	 */
	public String selectPassword(String loginId) throws SQLException {
		
		String password = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBConnection.getConnection();
			
			StringBuilder sql = new StringBuilder();
			
			sql.append(" select password ")
			   .append(" from member ")
			   .append(" where login_id = ? ")
			   .append(" and is_withdrawn_yn = 'N' ");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, loginId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				password = rs.getString("password");
			}//end if
			
		} finally {
			DBConnection.close(rs, pstmt, con);
		}//end finally
		
		return password;
	}//selectPassword
	
	/**
	 * 회원정보 수정 화면에 출력할 로그인 회원 정보 조회
	 *
	 * 탈퇴하지 않은 회원의 기본 정보와
	 * 미성년자 회원 보유 여부를 조회한다.
	 *
	 * @param loginId 로그인 아이디
	 * @return 회원 정보 DTO
	 * @throws SQLException DB 처리 중 오류 발생 시
	 */
	public MemberDTO selectUserInfo(String loginId) throws SQLException {
		
		MemberDTO mDTO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBConnection.getConnection();
			
			StringBuilder sql = new StringBuilder();
			
			sql.append(" select patient_no, login_id, name, email, tel, ")
			   .append("        birthday, zipcode, address, detail_address, ")
			   .append("        has_minor_member_yn ")
			   .append(" from member ")
			   .append(" where login_id = ? ")
			   .append(" and is_withdrawn_yn = 'N' ");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, loginId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				mDTO = new MemberDTO();
				
				mDTO.setPatientNo(rs.getString("patient_no"));
				mDTO.setLoginId(rs.getString("login_id"));
				mDTO.setName(rs.getString("name"));
				mDTO.setEmail(rs.getString("email"));
				mDTO.setPhoneNumber(rs.getString("tel"));
				mDTO.setBirthDate(rs.getDate("birthday"));
				mDTO.setZipCode(rs.getString("zipcode"));
				mDTO.setAddress(rs.getString("address"));
				mDTO.setAddressDetail(rs.getString("detail_address"));
				mDTO.setHasMinorMemberYn(rs.getString("has_minor_member_yn"));
			}//end if
			
		} finally {
			DBConnection.close(rs, pstmt, con);
		}//end finally
		
		return mDTO;
	}//selectUserInfo
	
	/**
	 * 로그인 회원과 연결된 미성년자 회원 정보 조회
	 *
	 * minor_member 테이블은 member 테이블과 같은 patient_no를 사용한다.
	 * member.has_minor_member_yn 값이 'Y'인 경우 호출하여
	 * 미성년자 이름, 생년월일, 보호자 관계 정보를 조회한다.
	 *
	 * @param patientNo 로그인 회원의 환자번호
	 * @return 미성년자 정보 DTO
	 * @throws SQLException DB 처리 중 오류 발생 시
	 */
	public MinorMemberDTO selectMinorUserInfo(String patientNo) throws SQLException {
		
		MinorMemberDTO minorDTO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBConnection.getConnection();
			
			StringBuilder sql = new StringBuilder();
			
			sql.append(" select patient_no, relationship, minor_name, minor_birth_date ")
			   .append(" from minor_member ")
			   .append(" where patient_no = ? ");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, patientNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				minorDTO = new MinorMemberDTO();
				
				minorDTO.setPatientNo(rs.getString("patient_no"));
				minorDTO.setRelationship(rs.getString("relationship"));
				minorDTO.setMinorName(rs.getString("minor_name"));
				minorDTO.setMinorBirthDate(rs.getDate("minor_birth_date"));
			}//end if
			
		} finally {
			DBConnection.close(rs, pstmt, con);
		}//end finally
		
		return minorDTO;
	}//selectMinorUserInfo
	
	/**
	 * 회원정보 수정
	 *
	 * 회원의 이메일, 연락처, 주소 정보를 수정한다.
	 * 탈퇴하지 않은 회원만 수정 가능하다.
	 *
	 * @param mDTO 수정할 회원 정보
	 * @return 수정된 행의 수
	 * @throws SQLException DB 처리 중 오류 발생 시
	 */
	public int updateUserInfo(MemberDTO mDTO) throws SQLException {
		
		int cnt = 0;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBConnection.getConnection();
			
			StringBuilder sql = new StringBuilder();
			
			sql.append(" update member ")
			   .append(" set email = ?, ")
			   .append("     tel = ?, ")
			   .append("     zipcode = ?, ")
			   .append("     address = ?, ")
			   .append("     detail_address = ? ")
			   .append(" where login_id = ? ")
			   .append(" and is_withdrawn_yn = 'N' ");
			
			pstmt = con.prepareStatement(sql.toString());
			
			pstmt.setString(1, mDTO.getEmail());
			pstmt.setString(2, mDTO.getPhoneNumber());
			pstmt.setString(3, mDTO.getZipCode());
			pstmt.setString(4, mDTO.getAddress());
			pstmt.setString(5, mDTO.getAddressDetail());
			pstmt.setString(6, mDTO.getLoginId());
			
			cnt = pstmt.executeUpdate();
			
		} finally {
			DBConnection.close(null, pstmt, con);
		}//end finally
		
		return cnt;
	}//updateUserInfo
	
	/**
	 * 미성년자 회원 정보 수정
	 *
	 * 로그인 회원과 같은 patient_no를 가진 minor_member 정보를 수정한다.
	 *
	 * @param mDTO 수정할 미성년자 정보
	 * @return 수정된 행의 수
	 * @throws SQLException DB 처리 중 오류 발생 시
	 */
	public int updateMinorUserInfo(MinorMemberDTO minorDTO) throws SQLException {
		
		int cnt = 0;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBConnection.getConnection();
			
			StringBuilder sql = new StringBuilder();
			
			sql.append(" update minor_member ")
			   .append(" set relationship = ?, ")
			   .append("     minor_name = ?, ")
			   .append("     minor_birth_date = ? ")
			   .append(" where patient_no = ? ");
			
			pstmt = con.prepareStatement(sql.toString());
			
			pstmt.setString(1, minorDTO.getRelationship());
			pstmt.setString(2, minorDTO.getMinorName());
			pstmt.setDate(3, minorDTO.getMinorBirthDate());
			pstmt.setString(4, minorDTO.getPatientNo());
			
			cnt = pstmt.executeUpdate();
			
		} finally {
			DBConnection.close(null, pstmt, con);
		}//end finally
		
		return cnt;
	}//updateMinorUserInfo
	
	/**
	 * 회원 탈퇴 처리
	 *
	 * 회원 데이터를 실제로 삭제하지 않고
	 * 탈퇴 여부와 탈퇴 일시를 수정한다.
	 *
	 * IS_WITHDRAWN_YN : N -> Y
	 * WITHDRAWN_AT    : 현재 날짜
	 *
	 * @param loginId 로그인 아이디
	 * @return 수정된 행의 수
	 * @throws SQLException DB 처리 중 오류 발생 시
	 */
	public int deleteUserInfo(String loginId) throws SQLException {
		
		int cnt = 0;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBConnection.getConnection();
			
			StringBuilder sql = new StringBuilder();
			
			sql.append(" update member ")
			   .append(" set is_withdrawn_yn = 'Y', ")
			   .append("     withdrawn_at = sysdate ")
			   .append(" where login_id = ? ")
			   .append(" and is_withdrawn_yn = 'N' ");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, loginId);
			
			cnt = pstmt.executeUpdate();
			
		} finally {
			DBConnection.close(null, pstmt, con);
		}//end finally
		
		return cnt;
	}//deleteUserInfo
}//class