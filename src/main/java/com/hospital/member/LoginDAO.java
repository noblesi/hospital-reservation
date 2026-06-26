package com.hospital.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hospital.common.DBConnection;
import com.hospital.common.MemberDTO;

/**
 * LoginDAO
 * 로그인 관련 DB 작업을 처리하는 DAO
 */
public class LoginDAO {

    private static LoginDAO lDAO;

    private LoginDAO() {

    }//LoginDAO

    public static LoginDAO getInstance() {
        if (lDAO == null) {
            lDAO = new LoginDAO();
        }//end if

        return lDAO;
    }//getInstance

    /**
     * 로그인 아이디로 회원 정보 조회
     * 
     * @param loginId 로그인 아이디
     * @return 회원정보, 없으면 null
     * @throws SQLException
     */
    public MemberDTO selectMemberByLoginId(String loginId) throws SQLException {

        MemberDTO mDTO = null;

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            StringBuilder selectMember = new StringBuilder();
            selectMember
                .append(" select patient_no, login_id, password, ")
                .append("        name, is_withdrawn_yn ")
                .append(" from member ")
                .append(" where login_id = ? ");

            pstmt = con.prepareStatement(selectMember.toString());
            pstmt.setString(1, loginId);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                mDTO = new MemberDTO();

                mDTO.setPatientNo(rs.getString("patient_no"));
                mDTO.setLoginId(rs.getString("login_id"));
                mDTO.setPassword(rs.getString("password"));
                mDTO.setName(rs.getString("name"));
                mDTO.setIsWithdrawnYn(rs.getString("is_withdrawn_yn"));
            }//end if

        } finally {
            DBConnection.close(rs, pstmt, con);
        }//end finally

        return mDTO;
    }//selectMemberByLoginId

}//class