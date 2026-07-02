package com.hospital.member;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.hospital.common.MemberDTO;
import com.hospital.common.MinorMemberDTO;
import com.hospital.common.util.GetKey;

import kr.co.sist.chipher.DataDecryption;
import kr.co.sist.chipher.DataEncryption;

/**
 * UpdateUserInfoService
 * 마이페이지 회원정보 수정 관련 비즈니스 로직 처리
 */
public class UpdateUserInfoService {
	private static final Logger LOGGER = Logger.getLogger(UpdateUserInfoService.class.getName());

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
			/*
        	 *	마이페이지 들어가기전 비밀번호 입력 암호화 코드 추가
        	 *  DB에 비밀번호가 암호화 되어 있으므로 입력된 비밀번호번호도 암호화 하여 
        	 *  DB의 비밀번호와 비교한다.
        	 *  2026.06.29 코드 추가 
        	 */
			String hashedPassword = DataEncryption.messageDigest("SHA-1", inputPassword);
			

			flag = dbPassword != null && dbPassword.equals(hashedPassword);

		} catch(SQLException se) {
			LOGGER.log(Level.SEVERE, "회원 정보 수정 처리 실패", se);
		} catch (NoSuchAlgorithmException e) {
			LOGGER.log(Level.SEVERE, "회원 정보 수정 처리 실패", e);
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
			
			/*
        	 *	마이페이지 회원정보 조회 복호화 코드 추가
        	 *  DB에는 이메일과 전화번호가 암호화 되어 저장되므로,
        	 *  화면에 보여주기 전에 복호화한다.
        	 *  2026.06.29 코드 추가 
        	 */
			if(mDTO != null) {
				DataDecryption dd = new DataDecryption(GetKey.getKey());
				
				mDTO.setEmail(dd.decrypt(mDTO.getEmail()));
				mDTO.setPhoneNumber(dd.decrypt(mDTO.getPhoneNumber()));
			}//end if

		} catch(SQLException se) {
			LOGGER.log(Level.SEVERE, "회원 정보 수정 처리 실패", se);
		} catch(Exception e) {
			LOGGER.log(Level.SEVERE, "회원 정보 수정 처리 실패", e);
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
			LOGGER.log(Level.SEVERE, "회원 정보 수정 처리 실패", se);
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
			/*
        	 *	마이페이지 회원정보 수정 암호화 코드 추가
        	 *  이메일과 전화번호는 개인정보이므로 DB 저장 전에 암호화한다.
        	 *  전화번호는 팀 공통 기준인 010-0000-0000 형식 그대로 암호화한다.
        	 *  2026.06.29 코드 추가 
        	 */
			DataEncryption de = new DataEncryption(GetKey.getKey());
			
			mDTO.setEmail(de.encrypt(mDTO.getEmail()));
			mDTO.setPhoneNumber(de.encrypt(mDTO.getPhoneNumber()));
			
			int cnt = uDAO.updateUserInfo(mDTO);

			flag = cnt == 1;

		} catch(SQLException se) {
			LOGGER.log(Level.SEVERE, "회원 정보 수정 처리 실패", se);
		} catch(Exception e) {
			LOGGER.log(Level.SEVERE, "회원 정보 수정 처리 실패", e);
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
			LOGGER.log(Level.SEVERE, "회원 정보 수정 처리 실패", se);
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
			LOGGER.log(Level.SEVERE, "회원 정보 수정 처리 실패", se);
		}//end catch

		return flag;
	}//removeUserInfo
}//class
