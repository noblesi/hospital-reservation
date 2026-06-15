package com.hospital.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.hospital.common.DBConnection;
import com.hospital.common.MemberDTO;

/**
 * loginDAO 
 */
public class LoginDAO {
	
	private static LoginDAO lDAO;
	
	private LoginDAO() {
		
	}//LoginDAO
	
	public static LoginDAO getInstance() {
		if(lDAO == null) {
			lDAO = new LoginDAO();
		}//end if
		return lDAO;
	}//LoginDAO
	
	/**
	 * 아이디로 멤버 정보 
	 * @param loginId 아이디를 받아서 
	 * @return 멤버DTO를 반환 
	 */
	public MemberDTO selectMemberByLoginId(String loginId) {
		
		DBConnection dbcon = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		return null;
	}//selectMemberByLoginId
}//loginDAO
