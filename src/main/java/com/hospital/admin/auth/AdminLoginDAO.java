package com.hospital.admin.auth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hospital.admin.auth.dto.AdminDTO;
import com.hospital.common.util.DBConnection;

public class AdminLoginDAO {
	private static AdminLoginDAO adminLoginDAO;

	private AdminLoginDAO() {
	}

	public static AdminLoginDAO getInstance() {
		if (adminLoginDAO == null) {
			adminLoginDAO = new AdminLoginDAO();
		}
		return adminLoginDAO;
	}

	public AdminDTO selectAdminById(String adminId) throws SQLException {
		AdminDTO adminDTO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBConnection.getConnection();

			StringBuilder selectAdmin = new StringBuilder();
			selectAdmin
				.append(" select admin_id, password, name, account_status ")
				.append(" from admin ")
				.append(" where admin_id = ? ");

			pstmt = con.prepareStatement(selectAdmin.toString());
			pstmt.setString(1, adminId);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				adminDTO = new AdminDTO();
				adminDTO.setAdminId(rs.getString("admin_id"));
				adminDTO.setPassword(rs.getString("password"));
				adminDTO.setAdminName(rs.getString("name"));
				adminDTO.setAccountStatus(rs.getString("account_status"));
			}
		} finally {
			DBConnection.close(rs, pstmt, con);
		}

		return adminDTO;
	}
}
