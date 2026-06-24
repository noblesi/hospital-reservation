package com.hospital.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hospital.common.util.DBConnection;
import com.hospital.member.dto.FindAccountDTO;

/**
 * 아이디 찾기 및 비밀번호 찾기 관련 DB 작업을 처리하는 DAO 
 */
public class FindAccountDAO {

    private static FindAccountDAO fDAO;

    private FindAccountDAO() {

    }// FindAccountDAO

    public static FindAccountDAO getInstance() {
        if (fDAO == null) {
            fDAO = new FindAccountDAO();
        }// end if

        return fDAO;
    }// getInstance

    /**
     * 이름, 연락처 또는 이메일, 생년월일을 이용하여 회원의 아이디를 조회하는 일
     * @param faDTO 이름, 연락처 또는 이메일, 생년월일 정보
     * @return 조회된 아이디, 없으면 null
     * @throws SQLException 
     */
    public String selectId(FindAccountDTO faDTO) throws SQLException {

        String loginId = null;

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            StringBuilder selectId = new StringBuilder();
            selectId
                .append(" select login_id ")
                .append(" from member ")
                .append(" where name = ? ")
                .append(" and birth_date = ? ");

            if (faDTO.getPhoneNumber() != null && !"".equals(faDTO.getPhoneNumber())) {
                selectId.append(" and phone_number = ? ");
            } else {
                selectId.append(" and email = ? ");
            }//end else

            pstmt = con.prepareStatement(selectId.toString());

            pstmt.setString(1, faDTO.getName());
            pstmt.setDate(2, faDTO.getBirthDate());

            if (faDTO.getPhoneNumber() != null && !"".equals(faDTO.getPhoneNumber())) {
                pstmt.setString(3, faDTO.getPhoneNumber());
            } else {
                pstmt.setString(3, faDTO.getEmail());
            }//else

            rs = pstmt.executeQuery();

            if (rs.next()) {
                loginId = rs.getString("login_id");
            }// end if

        } finally {
            DBConnection.close(rs, pstmt, con);
        }// end finally

        return loginId;
    }// selectId

    /**
     * 비밀번호 재설정을 위한 회원 검증
     * 입력받은 아이디, 이름, 핸드폰 번호 또는 이메일
     * 생년월일 정보가 회원정보와 일치하는지 확인하는 일
     * @param faDTO 아이디.이름,연락처 또는 이메일, 생년월일 정보
     * @return  회원정보가 일치하면 true, 아니면 false
     * @throws SQLException
     */
    public boolean checkPassword(FindAccountDTO faDTO) throws SQLException {

        boolean result = false;

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            StringBuilder checkPassword = new StringBuilder();
            checkPassword
                .append(" select patient_no ")
                .append(" from member ")
                .append(" where login_id = ? ")
                .append(" and name = ? ")
                .append(" and birth_date = ? ");

            if (faDTO.getPhoneNumber() != null && !"".equals(faDTO.getPhoneNumber())) {
                checkPassword.append(" and phone_number = ? ");
            } else {
                checkPassword.append(" and email = ? ");
            }//end else

            pstmt = con.prepareStatement(checkPassword.toString());

            pstmt.setString(1, faDTO.getLoginId());
            pstmt.setString(2, faDTO.getName());
            pstmt.setDate(3, faDTO.getBirthDate());

            if (faDTO.getPhoneNumber() != null && !"".equals(faDTO.getPhoneNumber())) {
                pstmt.setString(4, faDTO.getPhoneNumber());
            } else {
                pstmt.setString(4, faDTO.getEmail());
            }//end else

            rs = pstmt.executeQuery();

            result = rs.next();

        } finally {
            DBConnection.close(rs, pstmt, con);
        }// end finally

        return result;
    }// checkPassword

    /**
     * 회원의 비밀번호를 변경하는 일 
     * 비밀번호 찾기에서 회원 검증이 완료된 후 
     * 새로운 비밀번호를 수정한다.
     * @param loginId 변경할 회원 아이디
     * @param newPassword 새비밀번호
     * @return 변경된 행 수 
     * @throws SQLException 
     */
    public int resetPassword(String loginId, String newPassword) throws SQLException {

        int rowCnt = 0;

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DBConnection.getConnection();

            StringBuilder updatePassword = new StringBuilder();
            updatePassword
                .append(" update member ")
                .append(" set password = ? ")
                .append(" where login_id = ? ");

            pstmt = con.prepareStatement(updatePassword.toString());

            pstmt.setString(1, newPassword);
            pstmt.setString(2, loginId);

            rowCnt = pstmt.executeUpdate();

        } finally {
            DBConnection.close(null, pstmt, con);
        }// end finally

        return rowCnt;
    }// resetPassword

}// class
