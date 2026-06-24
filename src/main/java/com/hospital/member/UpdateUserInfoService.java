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
	 *
	 * @param loginId 로그인 아이디
	 * @param inputPassword 사용자가 입력한 현재 비밀번호
	 * @return DB 비밀번호와 일치하면 true
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
	 *
	 * @param loginId 로그인 아이디
	 * @return 수정 화면에 표시할 회원 정보, 없으면 null
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
	 *
	 * @param patientNo 보호자 회원의 환자번호
	 * @return 연결된 미성년자 회원 정보, 없으면 null
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
	 *
	 * @param mDTO 수정할 생년월일, 이메일, 연락처, 주소 및 로그인 아이디
	 * @return 회원정보가 정상적으로 한 건 수정되면 true
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
	 *
	 * @param minorDTO 수정할 미성년자 이름, 생년월일, 관계 정보
	 * @return 미성년자 정보가 정상적으로 한 건 수정되면 true
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
	 *
	 * @param loginId 탈퇴 처리할 로그인 아이디
	 * @return 탈퇴 상태가 정상적으로 한 건 변경되면 true
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
