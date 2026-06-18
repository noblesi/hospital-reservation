package com.hospital.admin.member;

import com.hospital.admin.member.dto.AdminMemberSearchDTO;
import com.hospital.common.DBConnection;
import com.hospital.common.dto.MemberDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// 관리자 회원 관리 DAO
public class AdminMemberDAO {

    // 회원 목록 총 건수 조회
    public int selectMemberCount(AdminMemberSearchDTO searchDTO) {

        if (searchDTO == null) {
            searchDTO = new AdminMemberSearchDTO();
        }

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) ");
        sql.append("FROM member ");
        sql.append("WHERE 1 = 1 ");

        List<Object> params = new ArrayList<>();

        // 검색 키워드가 있을 때만 조건 추가
        if (searchDTO.getSearchKeyword() != null
                && !searchDTO.getSearchKeyword().trim().isEmpty()) {

            String keyword = "%" + searchDTO.getSearchKeyword().trim() + "%";

            if ("loginId".equals(searchDTO.getSearchType())) {
                sql.append(" AND login_id LIKE ? ");
                params.add(keyword);
            } else if ("memberName".equals(searchDTO.getSearchType())) {
                sql.append(" AND member_name LIKE ? ");
                params.add(keyword);
            } else if ("email".equals(searchDTO.getSearchType())) {
                sql.append(" AND email LIKE ? ");
                params.add(keyword);
            } else {
                sql.append(" AND (login_id LIKE ? OR member_name LIKE ? OR email LIKE ?) ");
                params.add(keyword);
                params.add(keyword);
                params.add(keyword);
            }
        }

        // 상태 조건이 있을 때만 추가
        if (searchDTO.getStatus() != null
                && !searchDTO.getStatus().trim().isEmpty()) {
            sql.append(" AND status = ? ");
            params.add(searchDTO.getStatus().trim());
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            // 파라미터 바인딩
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

    // 회원 목록 조회
    public List<MemberDTO> selectMemberList(AdminMemberSearchDTO searchDTO) {

        List<MemberDTO> memberList = new ArrayList<>();

        if (searchDTO == null) {
            searchDTO = new AdminMemberSearchDTO();
        }

        if (searchDTO.getPageScale() <= 0) {
            searchDTO.setPageScale(10);
        }

        if (searchDTO.getStartNum() < 0) {
            searchDTO.setStartNum(0);
        }

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * ");
        sql.append("FROM ( ");
        sql.append("    SELECT ROWNUM rnum, inner_query.* ");
        sql.append("    FROM ( ");
        sql.append("        SELECT member_no, login_id, member_name, ");
        sql.append("               email, tel, status, create_date ");
        sql.append("        FROM member ");
        sql.append("        WHERE 1 = 1 ");

        List<Object> params = new ArrayList<>();

        // 검색 키워드 조건
        if (searchDTO.getSearchKeyword() != null
                && !searchDTO.getSearchKeyword().trim().isEmpty()) {

            String keyword = "%" + searchDTO.getSearchKeyword().trim() + "%";

            if ("loginId".equals(searchDTO.getSearchType())) {
                sql.append(" AND login_id LIKE ? ");
                params.add(keyword);
            } else if ("memberName".equals(searchDTO.getSearchType())) {
                sql.append(" AND member_name LIKE ? ");
                params.add(keyword);
            } else if ("email".equals(searchDTO.getSearchType())) {
                sql.append(" AND email LIKE ? ");
                params.add(keyword);
            } else {
                sql.append(" AND (login_id LIKE ? OR member_name LIKE ? OR email LIKE ?) ");
                params.add(keyword);
                params.add(keyword);
                params.add(keyword);
            }
        }

        // 상태 조건
        if (searchDTO.getStatus() != null
                && !searchDTO.getStatus().trim().isEmpty()) {
            sql.append(" AND status = ? ");
            params.add(searchDTO.getStatus().trim());
        }

        sql.append("        ORDER BY member_no DESC ");
        sql.append("    ) inner_query ");
        sql.append("    WHERE ROWNUM <= ? ");
        sql.append(") ");
        sql.append("WHERE rnum >= ? ");

        // Oracle 페이징용 행 번호 계산
        int startRow = searchDTO.getStartNum() + 1;
        int endRow = searchDTO.getStartNum() + searchDTO.getPageScale();

        params.add(endRow);
        params.add(startRow);

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    MemberDTO dto = new MemberDTO();
                    dto.setMemberNo(rs.getInt("member_no"));
                    dto.setLoginId(rs.getString("login_id"));
                    dto.setMemberName(rs.getString("member_name"));
                    dto.setEmail(rs.getString("email"));
                    dto.setTel(rs.getString("tel"));
                    dto.setStatus(rs.getString("status"));
                    dto.setCreateDate(rs.getString("create_date"));
                    memberList.add(dto);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return memberList;
    }

    // 회원 상세 조회
    public MemberDTO selectMemberDetail(int memberNo) {

        String sql = "SELECT member_no, login_id, member_name, "
                   + "       email, tel, status, create_date "
                   + "FROM member "
                   + "WHERE member_no = ? ";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, memberNo);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    MemberDTO dto = new MemberDTO();
                    dto.setMemberNo(rs.getInt("member_no"));
                    dto.setLoginId(rs.getString("login_id"));
                    dto.setMemberName(rs.getString("member_name"));
                    dto.setEmail(rs.getString("email"));
                    dto.setTel(rs.getString("tel"));
                    dto.setStatus(rs.getString("status"));
                    dto.setCreateDate(rs.getString("create_date"));
                    return dto;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
