package com.hospital.admin.memo;

import com.hospital.admin.memo.dto.AdminMemoDTO;

import java.sql.SQLException;
import java.util.List;

public class AdminMemoService {
    private final AdminMemoDAO adminMemoDAO = new AdminMemoDAO();

    public List<AdminMemoDTO> getMemoList(String patientNo) throws SQLException {
        return adminMemoDAO.selectMemoList(patientNo);
    }

    public boolean addMemo(String patientNo, String adminId, String content) throws SQLException {
        if (isBlank(patientNo) || isBlank(adminId) || isBlank(content)) {
            return false;
        }

        AdminMemoDTO memo = new AdminMemoDTO();
        memo.setPatientNo(patientNo.trim());
        memo.setAdminId(adminId.trim());
        memo.setContent(content.trim());
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
