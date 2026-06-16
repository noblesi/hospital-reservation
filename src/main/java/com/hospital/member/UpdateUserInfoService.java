package com.hospital.member;

import java.sql.SQLException;

import com.hospital.common.MemberDTO;
import com.hospital.common.MinorMemberDTO;

/**
 * UpdateUserInfoService
 * 마이페이지 회원정보 수정 관련 비즈니스 로직 처리
 */
public class UpdateUserInfoService {

	private UpdateUserInfoDAO uDAO;

	public UpdateUserInfoService() {
		uDAO = UpdateUserInfoDAO.getInstance();
	}//UpdateUserInfoService

	/**
	 * 회원정보 수정 페이지 진입 전 비밀번호 확인
	 */
	public boolean checkPassword(String loginId, String inputPassword) {

		boolean flag = false;

		try {
			String dbPassword = uDAO.selectPassword(loginId);

			flag = dbPassword != null && dbPassword.equals(inputPassword);

		} catch(SQLException se) {
			se.printStackTrace();
		}//end catch

		return flag;
	}//checkPassword

	/**
	 * 회원정보 조회
	 */
	public MemberDTO searchUserInfo(String loginId) {

		MemberDTO mDTO = null;

		try {
			mDTO = uDAO.selectUserInfo(loginId);

		} catch(SQLException se) {
			se.printStackTrace();
		}//end catch

		return mDTO;
	}//searchUserInfo

	/**
	 * 미성년자 회원정보 조회
	 */
	public MinorMemberDTO searchMinorUserInfo(String patientNo) {

		MinorMemberDTO minorDTO = null;

		try {
			minorDTO = uDAO.selectMinorUserInfo(patientNo);

		} catch(SQLException se) {
			se.printStackTrace();
		}//end catch

		return minorDTO;
	}//searchMinorUserInfo

	/**
	 * 회원정보 수정
	 */
	public boolean modifyUserInfo(MemberDTO mDTO) {

		boolean flag = false;

		try {
			int cnt = uDAO.updateUserInfo(mDTO);

			flag = cnt == 1;

		} catch(SQLException se) {
			se.printStackTrace();
		}//end catch

		return flag;
	}//modifyUserInfo

	/**
	 * 미성년자 회원정보 수정
	 */
	public boolean modifyMinorUserInfo(MinorMemberDTO minorDTO) {

		boolean flag = false;

		try {
			int cnt = uDAO.updateMinorUserInfo(minorDTO);

			flag = cnt == 1;

		} catch(SQLException se) {
			se.printStackTrace();
		}//end catch

		return flag;
	}//modifyMinorUserInfo

	/**
	 * 회원 탈퇴 처리
	 */
	public boolean removeUserInfo(String loginId) {

		boolean flag = false;

		try {
			int cnt = uDAO.deleteUserInfo(loginId);

			flag = cnt == 1;

		} catch(SQLException se) {
			se.printStackTrace();
		}//end catch

		return flag;
	}//removeUserInfo
}//class