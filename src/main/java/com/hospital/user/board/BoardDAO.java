package com.hospital.user.board;

import com.hospital.common.dto.BoardPostDTO;
import com.hospital.common.util.DBConnection;
import com.hospital.user.board.dto.BoardSearchDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO {
    private static final String BOARD_COLUMNS =
            "BP.POST_NO, BP.POST_TYPE, BP.TITLE, BP.CONTENT, BP.ADMIN_ID, "
                    + "NVL(A.NAME, BP.ADMIN_ID) ADMIN_NAME, BP.VIEW_COUNT, BP.CREATED_AT";

    public int selectBoardPostCount(BoardSearchDTO searchDTO) throws SQLException {
        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<>();

        sql.append("SELECT COUNT(*) FROM BOARD_POST BP WHERE 1 = 1");
        appendSearchCondition(sql, params, searchDTO);

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql.toString())) {
            bindParams(pstmt, params);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }

    public List<BoardPostDTO> selectBoardPostList(BoardSearchDTO searchDTO) throws SQLException {
        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<>();

        sql.append("SELECT * FROM (");
        sql.append(" SELECT ROW_NUMBER() OVER (ORDER BY BP.CREATED_AT DESC, BP.POST_NO DESC) RN, ");
        sql.append(BOARD_COLUMNS);
        sql.append(" FROM BOARD_POST BP LEFT JOIN ADMIN A ON BP.ADMIN_ID = A.ADMIN_ID WHERE 1 = 1");
        appendSearchCondition(sql, params, searchDTO);
        sql.append(") WHERE RN BETWEEN ? AND ?");

        params.add(searchDTO.getStartNum());
        params.add(searchDTO.getEndNum());

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql.toString())) {
            bindParams(pstmt, params);

            try (ResultSet rs = pstmt.executeQuery()) {
                List<BoardPostDTO> boardPostList = new ArrayList<>();
                while (rs.next()) {
                    boardPostList.add(mapBoardPost(rs));
                }

                return boardPostList;
            }
        }
    }

    public BoardPostDTO selectBoardPostById(int postId) throws SQLException {
        String sql = "SELECT " + BOARD_COLUMNS
                + " FROM BOARD_POST BP LEFT JOIN ADMIN A ON BP.ADMIN_ID = A.ADMIN_ID "
                + "WHERE BP.POST_NO = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, postId);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() ? mapBoardPost(rs) : null;
            }
        }
    }

    public void increaseViewCount(int postId) throws SQLException {
        String sql = "UPDATE BOARD_POST SET VIEW_COUNT = NVL(VIEW_COUNT, 0) + 1 WHERE POST_NO = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, postId);
            pstmt.executeUpdate();
        }
    }

    protected BoardPostDTO mapBoardPost(ResultSet rs) throws SQLException {
        BoardPostDTO boardPost = new BoardPostDTO();
        boardPost.setPostId(rs.getInt("POST_NO"));
        boardPost.setCategory(rs.getString("POST_TYPE"));
        boardPost.setTitle(rs.getString("TITLE"));
        boardPost.setContent(rs.getString("CONTENT"));
        boardPost.setWriterId(rs.getString("ADMIN_ID"));
        boardPost.setWriterName(rs.getString("ADMIN_NAME"));
        boardPost.setViewCount(rs.getInt("VIEW_COUNT"));
        boardPost.setCreatedAt(rs.getTimestamp("CREATED_AT"));
        return boardPost;
    }

    protected void appendSearchCondition(StringBuilder sql, List<Object> params, BoardSearchDTO searchDTO) {
        sql.append(" AND BP.POST_TYPE = ?");
        params.add(searchDTO.getCategory());

        if (!searchDTO.hasKeyword()) {
            return;
        }

        String keyword = "%" + searchDTO.getKeyword() + "%";
        String contentKeyword = searchDTO.getKeyword();
        if ("title".equals(searchDTO.getSearchType())) {
            sql.append(" AND BP.TITLE LIKE ?");
            params.add(keyword);
            return;
        }

        if ("content".equals(searchDTO.getSearchType())) {
            sql.append(" AND DBMS_LOB.INSTR(BP.CONTENT, ?) > 0");
            params.add(contentKeyword);
            return;
        }

        sql.append(" AND (BP.TITLE LIKE ? OR DBMS_LOB.INSTR(BP.CONTENT, ?) > 0)");
        params.add(keyword);
        params.add(contentKeyword);
    }

    protected void bindParams(PreparedStatement pstmt, List<Object> params) throws SQLException {
        for (int i = 0; i < params.size(); i++) {
            Object param = params.get(i);
            if (param instanceof Integer) {
                pstmt.setInt(i + 1, (Integer) param);
            } else {
                pstmt.setString(i + 1, String.valueOf(param));
            }
        }
    }
}
