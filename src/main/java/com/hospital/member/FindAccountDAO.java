package com.hospital.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hospital.common.DBConnection;
import com.hospital.member.dto.FindAccountDTO;

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
            }

            pstmt = con.prepareStatement(selectId.toString());

            pstmt.setString(1, faDTO.getName());
            pstmt.setDate(2, faDTO.getBirthDate());

            if (faDTO.getPhoneNumber() != null && !"".equals(faDTO.getPhoneNumber())) {
                pstmt.setString(3, faDTO.getPhoneNumber());
            } else {
                pstmt.setString(3, faDTO.getEmail());
            }

            rs = pstmt.executeQuery();

            if (rs.next()) {
                loginId = rs.getString("login_id");
            }// end if

        } finally {
            DBConnection.close(rs, pstmt, con);
        }// end finally

        return loginId;
    }// selectId

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
            }

            pstmt = con.prepareStatement(checkPassword.toString());

            pstmt.setString(1, faDTO.getLoginId());
            pstmt.setString(2, faDTO.getName());
            pstmt.setDate(3, faDTO.getBirthDate());

            if (faDTO.getPhoneNumber() != null && !"".equals(faDTO.getPhoneNumber())) {
                pstmt.setString(4, faDTO.getPhoneNumber());
            } else {
                pstmt.setString(4, faDTO.getEmail());
            }

            rs = pstmt.executeQuery();

            result = rs.next();

        } finally {
            DBConnection.close(rs, pstmt, con);
        }// end finally

        return result;
    }// checkPassword

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