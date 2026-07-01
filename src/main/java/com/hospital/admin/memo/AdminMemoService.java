package com.hospital.admin.memo;

import com.hospital.admin.memo.dto.AdminMemoDTO;

import java.sql.SQLException;
import java.util.List;

public class AdminMemoService {
    private static final int ADMIN_ID_MAX_LENGTH = 20;
    private static final int PATIENT_NO_MAX_LENGTH = 20;
    private static final int CONTENT_MAX_LENGTH = 500;

    private final AdminMemoDAO adminMemoDAO = new AdminMemoDAO();

    public List<AdminMemoDTO> getMemoList(String patientNo) throws SQLException {
        return adminMemoDAO.selectMemoList(patientNo);
    }

    public boolean addMemo(String patientNo, String adminId, String content) throws SQLException {
        if (isBlank(patientNo) || isBlank(adminId) || isBlank(content)) {
            return false;
        }

        patientNo = patientNo.trim();
        adminId = adminId.trim();
        content = content.trim();

        if (patientNo.length() > PATIENT_NO_MAX_LENGTH
                || adminId.length() > ADMIN_ID_MAX_LENGTH
                || content.length() > CONTENT_MAX_LENGTH) {
            return false;
        }

        AdminMemoDTO memo = new AdminMemoDTO();
        memo.setPatientNo(patientNo);
        memo.setAdminId(adminId);
        memo.setContent(content);
        return adminMemoDAO.insertMemo(memo) > 0;
    }

    public boolean deleteMemo(int memoNo, String patientNo) throws SQLException {
        if (memoNo <= 0 || isBlank(patientNo)) {
            return false;
        }

        return adminMemoDAO.deleteMemo(memoNo, patientNo.trim()) > 0;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
