package com.hospital.admin.board;

import com.hospital.admin.board.dto.AdminBoardSearchDTO;
import com.hospital.common.DBConnection;
import com.hospital.common.dto.BoardPostDTO;
import com.hospital.user.board.BoardDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminBoardDAO extends BoardDAO {
    private static final String BOARD_COLUMNS =
            "POST_ID, CATEGORY, TITLE, CONTENT, WRITER_ID, WRITER_NAME, "
                    + "NOTICE_YN, DISPLAY_YN, VIEW_COUNT, CREATED_AT, UPDATED_AT";

    public int selectAdminBoardPostCount(AdminBoardSearchDTO searchDTO) throws SQLException {
        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<>();

        sql.append("SELECT COUNT(*) FROM BOARD_POST WHERE 1 = 1");
        appendSearchCondition(sql, params, searchDTO);
        appendDisplayCondition(sql, params, searchDTO);

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql.toString())) {
            bindParams(pstmt, params);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }

    public List<BoardPostDTO> selectAdminBoardPostList(AdminBoardSearchDTO searchDTO) throws SQLException {
        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<>();

        sql.append("SELECT * FROM (");
        sql.append(" SELECT ROW_NUMBER() OVER (ORDER BY CREATED_AT DESC, POST_ID DESC) RN, ");
        sql.append(BOARD_COLUMNS);
        sql.append(" FROM BOARD_POST WHERE 1 = 1");
        appendSearchCondition(sql, params, searchDTO);
        appendDisplayCondition(sql, params, searchDTO);
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

    public BoardPostDTO selectAdminBoardPostById(int postId) throws SQLException {
        String sql = "SELECT " + BOARD_COLUMNS + " FROM BOARD_POST WHERE POST_ID = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, postId);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() ? mapBoardPost(rs) : null;
            }
        }
    }

    public int insertBoardPost(BoardPostDTO boardPost) throws SQLException {
        String sql = "INSERT INTO BOARD_POST ("
                + "POST_ID, CATEGORY, TITLE, CONTENT, WRITER_ID, WRITER_NAME, "
                + "NOTICE_YN, DISPLAY_YN, VIEW_COUNT, CREATED_AT, UPDATED_AT"
                + ") VALUES ("
                + "BOARD_POST_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, 0, SYSDATE, SYSDATE"
                + ")";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            bindBoardPostForm(pstmt, boardPost);
            return pstmt.executeUpdate();
        }
    }

    public int updateBoardPost(BoardPostDTO boardPost) throws SQLException {
        String sql = "UPDATE BOARD_POST "
                + "SET CATEGORY = ?, "
                + "TITLE = ?, "
                + "CONTENT = ?, "
                + "WRITER_ID = ?, "
                + "WRITER_NAME = ?, "
                + "NOTICE_YN = ?, "
                + "DISPLAY_YN = ?, "
                + "UPDATED_AT = SYSDATE "
                + "WHERE POST_ID = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            bindBoardPostForm(pstmt, boardPost);
            pstmt.setInt(8, boardPost.getPostId());
            return pstmt.executeUpdate();
        }
    }

    public int deleteBoardPost(int postId) throws SQLException {
        String sql = "UPDATE BOARD_POST SET DISPLAY_YN = 'N', UPDATED_AT = SYSDATE WHERE POST_ID = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, postId);
            return pstmt.executeUpdate();
        }
    }

    private void appendDisplayCondition(StringBuilder sql, List<Object> params, AdminBoardSearchDTO searchDTO) {
        if (!searchDTO.hasDisplayCondition()) {
            return;
        }

        sql.append(" AND DISPLAY_YN = ?");
        params.add(searchDTO.getDisplayYn());
    }

    private void bindBoardPostForm(PreparedStatement pstmt, BoardPostDTO boardPost) throws SQLException {
        pstmt.setString(1, boardPost.getCategory());
        pstmt.setString(2, boardPost.getTitle());
        pstmt.setString(3, boardPost.getContent());
        pstmt.setString(4, boardPost.getWriterId());
        pstmt.setString(5, boardPost.getWriterName());
        pstmt.setString(6, boardPost.getNoticeYn());
        pstmt.setString(7, boardPost.getDisplayYn());
    }
}
