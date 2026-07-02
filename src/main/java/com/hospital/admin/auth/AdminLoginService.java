package com.hospital.admin.auth;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.hospital.admin.auth.dto.AdminDTO;

import kr.co.sist.chipher.DataEncryption;

public class AdminLoginService {
	private static final Logger LOGGER = Logger.getLogger(AdminLoginService.class.getName());
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
			LOGGER.log(Level.SEVERE, "관리자 로그인 처리 실패: " + adminId, e);
			return null;
		}

		return adminDTO;
	}
}
