package com.hospital.member;

import java.sql.SQLException;

import com.hospital.common.MemberDTO;
import com.hospital.common.MinorMemberDTO;

/**
 * 회원가입 관련 비즈니스 로직 처리 
 */
public class MemberRegisterService {
	
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
			se.printStackTrace();
			
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
			
			// 회원 정보 저장
			int memberRow = mrDAO.insertMember(mDTO);
			
			if(memberRow == 0) {
				return false;
			}//end if
			
			// 미성년자 회원인 경우 보호자 정보 저장
			if (minorDTO != null) {
				
				int minorRow = mrDAO.insertMinorMember(minorDTO);
				
				if(minorRow == 0 ) {
					return false;
				}//end if
				
			}// end if
			return true;
		} catch (SQLException se) {
			
			se.printStackTrace();
			return false;
		}//end catch
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
			se.printStackTrace();
		}//end catch
		return mDTO;
	}//searchRegiSter
}//class
