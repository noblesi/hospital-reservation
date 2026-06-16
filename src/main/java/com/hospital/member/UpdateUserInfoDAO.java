package com.hospital.member;

import com.hospital.common.MemberDTO;

/**
 * UpdateUserInfoDAO
 * 마이페이지내에 회원정보 변경 관련 DB 작업을 처리하는 DAO
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
	
	public String selectPassword(String loginId){
		return loginId;
	}//selectPassword
	
	public MemberDTO selectUserInfo(String loginId){
		return null;
	}//selectUserInfo
	
	public int updateUserInfo(MemberDTO mDTO){
		return 0;
	}//updateUserInfo
	
	public int deleteUserInfo(String loginId){
		return 0;
	}//deleteUserInfo
}//class
