package com.hospital.member;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.hospital.common.MemberDTO;
import com.hospital.common.MinorMemberDTO;
import com.hospital.common.util.GetKey;

import kr.co.sist.chipher.DataEncryption;

/**
 * 회원가입 관련 비즈니스 로직 처리 
 */
public class MemberRegisterService {
	private static final Logger LOGGER = Logger.getLogger(MemberRegisterService.class.getName());
	
    private MemberRegisterDAO mrDAO;
    
    public MemberRegisterService() {
    	mrDAO = MemberRegisterDAO.getInstance();
    }//MemberRegisterService
    
	/**
	 * 아이디 중복 확인 
	 * @param loginId 사용자가 입력한 아이디
	 * @return 중복이면 true, 사용 가능하면 false 반환 
	 */
	public boolean checkLoginIdDuplicate(String loginId) {
		
		try {
			return mrDAO.selectLoginIdCount(loginId) > 0;
		} catch (SQLException se) {
			LOGGER.log(Level.SEVERE, "회원가입 처리 실패", se);
			
			//예외 발생시 중복으로 처리
			return true;
		}// catch
	}//checkLoginIdDuplicate
	
	/**
	 * 회원가입 처리 
	 * @param mDTO 회원정보
	 * @param minorDTO 미성년자 회원정보 
	 * @return 가입 성공 여부 
	 */
	public boolean registerMember(MemberDTO mDTO, MinorMemberDTO minorDTO) {
			
		
		try {
			/*
			 * 회원가입 전 암호화 처리 코드 추가  
			 * - 비밀번호   SHA-1 일방향 Hash 처리 
			 * - 이메일    양방향 암호화 처리 
			 * - 전화번호   양방향 암호화 처리 
			 * 	2026.06.29 코드 추가  
			*/
			
			mDTO.setPassword(DataEncryption.messageDigest("SHA-1", mDTO.getPassword()));
			DataEncryption de = new DataEncryption(GetKey.getKey());
				mDTO.setEmail(de.encrypt(mDTO.getEmail()));
				mDTO.setPhoneNumber(de.encrypt(mDTO.getPhoneNumber()));
			// 회원 정보 저장
			int memberRow = mrDAO.insertMember(mDTO);
			
			if(memberRow == 0) {
				return false;
			}//end if
			
			// 미성년자 회원인 경우 보호자 정보 저장
			if (minorDTO != null) {
				String patientNo = mrDAO.selectPatientNoByLoginId(mDTO.getLoginId());
				if(patientNo == null ) {
					return false;
				}//end if
				minorDTO.setPatientNo(patientNo);
				
				int minorRow = mrDAO.insertMinorMember(minorDTO);
				
				if(minorRow == 0) {
					return false;
				}//end if
			}// end if
			return true;
		} catch (SQLException se) {
			LOGGER.log(Level.SEVERE, "회원가입 처리 실패", se);
		} catch (NoSuchAlgorithmException e) {
			LOGGER.log(Level.SEVERE, "회원가입 처리 실패", e);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "회원가입 처리 실패", e);
		}
		return false;
	}//registerMember
	
	/**
	 * 회원가입 완료 화면 조회
	 * @param LoginId 가입한 회원 아이디
	 * @return 회원 정보
	 */
	public MemberDTO searchRegister(String loginId) {
		
		MemberDTO mDTO = null;
		
		try {
			mDTO = mrDAO.selectRegisterResult(loginId);
		} catch (SQLException se) {
			LOGGER.log(Level.SEVERE, "회원가입 처리 실패", se);
		}//end catch
		return mDTO;
	}//searchRegiSter
}//class
