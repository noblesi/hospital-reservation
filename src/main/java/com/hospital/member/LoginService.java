package com.hospital.member;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.hospital.common.MemberDTO;

import kr.co.sist.chipher.DataEncryption;


/**
 * 로그인 관련 비즈니스 로직 처리 
 */
public class LoginService {
	private static final Logger LOGGER = Logger.getLogger(LoginService.class.getName());

    private LoginDAO loginDAO;

    public LoginService() {
        loginDAO = LoginDAO.getInstance();
    }//LoginService

    public MemberDTO login(String loginId, String password) {

        MemberDTO mDTO = null;

        try {
            mDTO = loginDAO.selectMemberByLoginId(loginId);

            if (mDTO == null) {
                return null;
            }// end if
            /*
             * 로그인 전 비밀번호 비교를 위한 암호화 처리 코드 추가 
             * 
             * 회원 가입시 DB에는 Hash 값으로 저장되므로 
             * 사용자가 입력한 비밀번호도 같은 방식으로 Hash 처리한 다음 비교한다.  
             * 
             * 2026.06.29 코드 추가  
             */
				String hashedPassword = DataEncryption.messageDigest("SHA-1", password);
            if (!hashedPassword.equals(mDTO.getPassword())) {
                return null;
            }// end if

            if ("Y".equals(mDTO.getIsWithdrawnYn())) {
                return null;
            }// end if

        } catch (SQLException se) {
            LOGGER.log(Level.SEVERE, "회원 로그인 DB 조회 실패: " + loginId, se);
            return null;
        } catch (NoSuchAlgorithmException e) {
            LOGGER.log(Level.SEVERE, "회원 로그인 비밀번호 해시 처리 실패", e);
            return null;
        }//end catch

        return mDTO;
    }// login

}// class
