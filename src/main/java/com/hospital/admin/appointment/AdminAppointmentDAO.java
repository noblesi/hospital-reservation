package com.hospital.admin.appointment;

import com.hospital.admin.appointment.dto.AdminAppointmentSearchDTO;
import com.hospital.common.dto.AppointmentDTO;
import com.hospital.common.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// 관리자 예약 관리 DAO
public class AdminAppointmentDAO {

    // 예약 목록 총 건수 조회
    public int selectAppointmentCount(AdminAppointmentSearchDTO searchDTO) {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) ");
        sql.append("FROM appointment ");
        sql.append("WHERE 1 = 1 ");

        List<Object> params = new ArrayList<>();

        // 예약 상태 조건
        if (searchDTO.getStatus() != null
                && !searchDTO.getStatus().trim().isEmpty()) {
            sql.append(" AND status = ? ");
            params.add(searchDTO.getStatus().trim());
        }

        // 시작일 조건
        if (searchDTO.getStartDate() != null
                && !searchDTO.getStartDate().trim().isEmpty()) {
            sql.append(" AND appointment_date >= TO_DATE(?, 'YYYY-MM-DD') ");
            params.add(searchDTO.getStartDate().trim());
        }

        // 종료일 조건
        if (searchDTO.getEndDate() != null
                && !searchDTO.getEndDate().trim().isEmpty()) {
            sql.append(" AND appointment_date <= TO_DATE(?, 'YYYY-MM-DD') ");
            params.add(searchDTO.getEndDate().trim());
        }

        // 검색 키워드 조건
        // 현재는 예약번호 기준 예시
        // 나중에 환자명/의사명 조인 구조에 맞게 수정 가능
        if (searchDTO.getSearchKeyword() != null
                && !searchDTO.getSearchKeyword().trim().isEmpty()) {
            sql.append(" AND TO_CHAR(appointment_no) LIKE ? ");
            params.add("%" + searchDTO.getSearchKeyword().trim() + "%");
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    // 예약 목록 조회
    public List<AppointmentDTO> selectAppointmentList(AdminAppointmentSearchDTO searchDTO) {

        List<AppointmentDTO> appointmentList = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * ");
        sql.append("FROM ( ");
        sql.append("    SELECT ROWNUM rnum, inner_query.* ");
        sql.append("    FROM ( ");
        sql.append("        SELECT appointment_no, patient_no, doctor_license_no, ");
        sql.append("               appointment_date, appointment_time, status, created_at ");
        sql.append("        FROM appointment ");
        sql.append("        WHERE 1 = 1 ");

        List<Object> params = new ArrayList<>();

        // 예약 상태 조건
        if (searchDTO.getStatus() != null
                && !searchDTO.getStatus().trim().isEmpty()) {
            sql.append(" AND status = ? ");
            params.add(searchDTO.getStatus().trim());
        }

        // 시작일 조건
        if (searchDTO.getStartDate() != null
                && !searchDTO.getStartDate().trim().isEmpty()) {
            sql.append(" AND appointment_date >= TO_DATE(?, 'YYYY-MM-DD') ");
            params.add(searchDTO.getStartDate().trim());
        }

        // 종료일 조건
        if (searchDTO.getEndDate() != null
                && !searchDTO.getEndDate().trim().isEmpty()) {
            sql.append(" AND appointment_date <= TO_DATE(?, 'YYYY-MM-DD') ");
            params.add(searchDTO.getEndDate().trim());
        }

        // 검색 키워드 조건
        if (searchDTO.getSearchKeyword() != null
                && !searchDTO.getSearchKeyword().trim().isEmpty()) {
            sql.append(" AND TO_CHAR(appointment_no) LIKE ? ");
            params.add("%" + searchDTO.getSearchKeyword().trim() + "%");
        }

        // 정렬 + 페이징
        sql.append("        ORDER BY appointment_no DESC ");
        sql.append("    ) inner_query ");
        sql.append("    WHERE ROWNUM <= ? ");
        sql.append(") ");
        sql.append("WHERE rnum >= ? ");

        int startRow = Math.max(searchDTO.getStartNum(), 0) + 1;
        int endRow = Math.max(searchDTO.getEndNum(), searchDTO.getStartNum() + searchDTO.getPageScale());
        params.add(endRow);
        params.add(startRow);

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    AppointmentDTO dto = new AppointmentDTO();
                    dto.setAppointmentNo(rs.getInt("appointment_no"));
                    dto.setPatientNo(rs.getString("patient_no"));
                    dto.setDoctorLicenseNo(rs.getInt("doctor_license_no"));
                    dto.setAppointmentDate(rs.getDate("appointment_date"));
                    dto.setAppointmentTime(rs.getString("appointment_time"));
                    dto.setStatus(rs.getString("status"));
                    dto.setCreateDate(rs.getTimestamp("created_at"));

                    appointmentList.add(dto);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointmentList;
    }

    // 예약 상세 조회
    public AppointmentDTO selectAppointmentDetail(int appointmentNo) {

        String sql = "SELECT appointment_no, patient_no, doctor_license_no, "
                   + "       appointment_date, appointment_time, status, created_at "
                   + "FROM appointment "
                   + "WHERE appointment_no = ? ";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, appointmentNo);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    AppointmentDTO dto = new AppointmentDTO();
                    dto.setAppointmentNo(rs.getInt("appointment_no"));
                    dto.setPatientNo(rs.getString("patient_no"));
                    dto.setDoctorLicenseNo(rs.getInt("doctor_license_no"));
                    dto.setAppointmentDate(rs.getDate("appointment_date"));
                    dto.setAppointmentTime(rs.getString("appointment_time"));
                    dto.setStatus(rs.getString("status"));
                    dto.setCreateDate(rs.getTimestamp("created_at"));
                    return dto;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 예약 상태 변경
    public int updateAppointmentStatus(int appointmentNo, String status) {

        String sql = "UPDATE appointment "
                   + "SET status = ? "
                   + "WHERE appointment_no = ? ";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status);
            pstmt.setInt(2, appointmentNo);

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
