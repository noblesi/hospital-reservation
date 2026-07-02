package com.hospital.member;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.hospital.common.util.GetKey;
import com.hospital.member.dto.FindAccountDTO;

import kr.co.sist.chipher.DataEncryption;

/**
 * 아이디 찾기 , 비밀번호 찾기 관련 서비스 처리 로직 
 */
public class FindAccountService {
	private static final Logger LOGGER = Logger.getLogger(FindAccountService.class.getName());

    private FindAccountDAO findAccountDAO;

    public FindAccountService() {
        findAccountDAO = FindAccountDAO.getInstance();
    }//FindAccountService
    
    /**
     * 암호화 코드 추가 
     * 2026.06.29
     * 아이디/비밀번호 찾기 조회시 사용하는 이메일, 전화번호만 암호화한다.
     * DB에는 이메일과 전화번호가 암호화 되어 저장되므로,
     * 입력값도 똑같이 암호화 하여 DAO 조회 조건으로 사용한다. 
     * @param faDTO
     * @throws Exception
     */
    private void encryptionEmailOrPhone(FindAccountDTO faDTO) throws Exception {
    	
    	DataEncryption de = new DataEncryption(GetKey.getKey());	
    	
    	if(faDTO.getPhoneNumber() != null && !"".equals(faDTO.getPhoneNumber())){
    		faDTO.setPhoneNumber(de.encrypt(faDTO.getPhoneNumber()));
    	} else if(faDTO.getEmail() != null && !"".equals(faDTO.getEmail().trim())){
    		faDTO.setEmail(de.encrypt(faDTO.getEmail()));
    	}//end if
    }//encryptionEmailOrPhone

    /**
     * 회원의 아이디를 조회하는 일 
     * @param faDTO 이름, 연락처 또는 이메일, 생년월일 정보
     * @return 조회된 아이디, 없으면 null
     */
    public String findId(FindAccountDTO faDTO) {

        String loginId = null;

        try {
        	/*
        	 *	아이디 찾기 조회 조건 암호화 코드 추가
        	 *  DB에는 이메일, 연락처가 암호화 되어 저장되므로 
        	 *  사용자가 입력한 이메일 또는 연락처로 암호화한뒤에 비교한다.
        	 *  2026.06.29 코드 추가 
        	 */
			encryptionEmailOrPhone(faDTO);
        	
            loginId = findAccountDAO.selectId(faDTO);
        } catch (SQLException se) {
            LOGGER.log(Level.SEVERE, "계정 찾기 처리 실패", se);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "계정 찾기 처리 실패", e);
        }//end catch 

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
        	/*
        	 *	비밀번호 찾기 조회 조건 암호화 코드 추가
        	 *  DB에는 이메일, 연락처가 암호화 되어 저장되므로 
        	 *  사용자가 입력한 이메일 또는 연락처로 암호화한 뒤에 비교한다.
        	 *  2026.06.29 코드 추가 
        	 */
			encryptionEmailOrPhone(faDTO);
            flag = findAccountDAO.checkPassword(faDTO);
        } catch (SQLException se) {
            LOGGER.log(Level.SEVERE, "계정 찾기 처리 실패", se);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "계정 찾기 처리 실패", e);
        }//end catch 
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
        	/*
        	 * 비밀번호 재설정 암호화 처리 코드 추가   
        	 * DB에는 평문 비밀번호를 저장하지 않고,
        	 * 회원가입과 동인한 암호화 된 코드를 저장한다.
        	 * 
        	 * 2026.06.29 코드 추가
        	 */
				String hashedPassword = DataEncryption.messageDigest("SHA-1", newPassword);
        	
            flag = findAccountDAO.resetPassword(loginId, hashedPassword) == 1;
        } catch (SQLException se) {
            LOGGER.log(Level.SEVERE, "계정 찾기 처리 실패", se);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.log(Level.SEVERE, "계정 찾기 처리 실패", e);
        }//end catch 

        return flag;

    }//resetPassword
    

}//class
