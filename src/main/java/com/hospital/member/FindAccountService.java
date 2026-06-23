package com.hospital.member;

import java.sql.SQLException;

import com.hospital.member.dto.FindAccountDTO;

/**
 * 아이디 찾기 , 비밀번호 찾기 관련 서비스 처리 로직 
 */
public class FindAccountService {

    private FindAccountDAO findAccountDAO;

    public FindAccountService() {
        findAccountDAO = FindAccountDAO.getInstance();
    }//FindAccountService

    /**
     * 회원의 아이디를 조회하는 일 
     * @param faDTO 이름, 연락처 또는 이메일, 생년월일 정보
     * @return 조회된 아이디, 없으면 null
     */
    public String findId(FindAccountDTO faDTO) {

        String loginId = null;

        try {
            loginId = findAccountDAO.selectId(faDTO);
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return loginId;

    }//findId

    /**
     * 회원의 비밀번호를 재설정하기 위한 회원 검증 
     * @param faDTO 아이디, 이름 연락처 또는 이메일, 생년월일 정보
     * @return 회원정보가 일치하면 true, 아니면 false 
     */
    public boolean findPassword(FindAccountDTO faDTO) {

        boolean flag = false;

        try {
            flag = findAccountDAO.checkPassword(faDTO);
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return flag;

    }//findPassword

    /**
     * 비밀번호를 변경하는 일 
     * @param loginId 변경할 회원의 아이디
     * @param newPassword 새 비밀번호 
     * @return 변경성공 여부 
     */
    public boolean resetPassword(String loginId, String newPassword) {

        boolean flag = false;

        try {
            flag = findAccountDAO.resetPassword(loginId, newPassword) == 1;
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return flag;

    }//resetPassword

}//class