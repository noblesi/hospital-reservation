package com.hospital.admin.memo;

import com.hospital.admin.memo.dto.AdminMemoDTO;
import com.hospital.common.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminMemoDAO {

    public List<AdminMemoDTO> selectMemoList(String patientNo) throws SQLException {
        String sql = "SELECT memo_no, admin_id, patient_no, content, created_at "
                + "FROM admin_memo "
                + "WHERE patient_no = ? "
                + "ORDER BY created_at DESC, memo_no DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, patientNo);

            try (ResultSet rs = pstmt.executeQuery()) {
                List<AdminMemoDTO> memoList = new ArrayList<>();
                while (rs.next()) {
                    memoList.add(mapMemo(rs));
                }

                return memoList;
            }
        }
    }

    public int insertMemo(AdminMemoDTO memo) throws SQLException {
        String sql = "INSERT INTO admin_memo (memo_no, admin_id, patient_no, content, created_at) "
                + "VALUES (seq_admin_memo.NEXTVAL, ?, ?, ?, SYSDATE)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, memo.getAdminId());
            pstmt.setString(2, memo.getPatientNo());
            pstmt.setString(3, memo.getContent());
            return pstmt.executeUpdate();
        }
    }

    public int deleteMemo(int memoNo, String patientNo) throws SQLException {
        String sql = "DELETE FROM admin_memo WHERE memo_no = ? AND patient_no = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, memoNo);
            pstmt.setString(2, patientNo);
            return pstmt.executeUpdate();
        }
    }

    private AdminMemoDTO mapMemo(ResultSet rs) throws SQLException {
        AdminMemoDTO memo = new AdminMemoDTO();
        memo.setMemoNo(rs.getInt("memo_no"));
        memo.setAdminId(rs.getString("admin_id"));
        memo.setPatientNo(rs.getString("patient_no"));
        memo.setContent(rs.getString("content"));
        memo.setCreatedAt(rs.getTimestamp("created_at"));
        return memo;
    }
}
