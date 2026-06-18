package com.hospital.user.board;

import com.hospital.common.DBConnection;
import com.hospital.common.dto.BoardPostDTO;
import com.hospital.user.board.dto.BoardSearchDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO {
    private static final String BOARD_COLUMNS =
            "POST_ID, CATEGORY, TITLE, CONTENT, WRITER_ID, WRITER_NAME, "
                    + "NOTICE_YN, DISPLAY_YN, VIEW_COUNT, CREATED_AT, UPDATED_AT";

    public int selectBoardPostCount(BoardSearchDTO searchDTO) throws SQLException {
        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<>();

        sql.append("SELECT COUNT(*) FROM BOARD_POST WHERE DISPLAY_YN = 'Y'");
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
        sql.append(" SELECT ROW_NUMBER() OVER (ORDER BY NOTICE_YN DESC, CREATED_AT DESC, POST_ID DESC) RN, ");
        sql.append(BOARD_COLUMNS);
        sql.append(" FROM BOARD_POST WHERE DISPLAY_YN = 'Y'");
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
        String sql = "SELECT " + BOARD_COLUMNS + " FROM BOARD_POST WHERE POST_ID = ? AND DISPLAY_YN = 'Y'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, postId);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() ? mapBoardPost(rs) : null;
            }
        }
    }

    public void increaseViewCount(int postId) throws SQLException {
        String sql = "UPDATE BOARD_POST SET VIEW_COUNT = NVL(VIEW_COUNT, 0) + 1 WHERE POST_ID = ? AND DISPLAY_YN = 'Y'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, postId);
            pstmt.executeUpdate();
        }
    }

    protected BoardPostDTO mapBoardPost(ResultSet rs) throws SQLException {
        BoardPostDTO boardPost = new BoardPostDTO();
        boardPost.setPostId(rs.getInt("POST_ID"));
        boardPost.setCategory(rs.getString("CATEGORY"));
        boardPost.setTitle(rs.getString("TITLE"));
        boardPost.setContent(rs.getString("CONTENT"));
        boardPost.setWriterId(rs.getString("WRITER_ID"));
        boardPost.setWriterName(rs.getString("WRITER_NAME"));
        boardPost.setNoticeYn(rs.getString("NOTICE_YN"));
        boardPost.setDisplayYn(rs.getString("DISPLAY_YN"));
        boardPost.setViewCount(rs.getInt("VIEW_COUNT"));
        boardPost.setCreatedAt(rs.getTimestamp("CREATED_AT"));
        boardPost.setUpdatedAt(rs.getTimestamp("UPDATED_AT"));
        return boardPost;
    }

    protected void appendSearchCondition(StringBuilder sql, List<Object> params, BoardSearchDTO searchDTO) {
        sql.append(" AND CATEGORY = ?");
        params.add(searchDTO.getCategory());

        if (!searchDTO.hasKeyword()) {
            return;
        }

        String keyword = "%" + searchDTO.getKeyword() + "%";
        if ("title".equals(searchDTO.getSearchType())) {
            sql.append(" AND TITLE LIKE ?");
            params.add(keyword);
            return;
        }

        if ("content".equals(searchDTO.getSearchType())) {
            sql.append(" AND CONTENT LIKE ?");
            params.add(keyword);
            return;
        }

        sql.append(" AND (TITLE LIKE ? OR CONTENT LIKE ?)");
        params.add(keyword);
        params.add(keyword);
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
