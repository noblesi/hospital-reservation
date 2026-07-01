package com.hospital.admin.auth;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import com.hospital.admin.auth.dto.AdminDTO;

import kr.co.sist.chipher.DataEncryption;

public class AdminLoginService {
	private static final String ACTIVE_STATUS = "사용중";

	private final AdminLoginDAO adminLoginDAO;

	public AdminLoginService() {
		adminLoginDAO = AdminLoginDAO.getInstance();
	}

	public AdminDTO login(String adminId, String password) {
		AdminDTO adminDTO = null;

		try {
			adminDTO = adminLoginDAO.selectAdminById(adminId);

			if (adminDTO == null) {
				return null;
			}

			String hashedPassword = DataEncryption.messageDigest("SHA-1", password);
			if (!hashedPassword.equals(adminDTO.getPassword())) {
				return null;
			}

			if (!ACTIVE_STATUS.equals(adminDTO.getAccountStatus())) {
				return null;
			}

			adminDTO.setPassword(null);
		} catch (SQLException | NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}

		return adminDTO;
	}
}
