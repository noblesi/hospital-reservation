package com.hospital.member;

import java.sql.SQLException;

import com.hospital.common.MemberDTO;


/**
 * 로그인 관련 비즈니스 로직 처리 
 */
public class LoginService {
 
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

            if (!password.equals(mDTO.getPassword())) {
                return null;
            }// end if

            if ("Y".equals(mDTO.getIsWithdrawnYn())) {
                return null;
            }// end if

        } catch (SQLException se) {
            se.printStackTrace();
            return null;
        }//end catch

        return mDTO;
    }// login

}// LoginService