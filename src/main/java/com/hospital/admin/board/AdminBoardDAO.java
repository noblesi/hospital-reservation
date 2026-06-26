package com.hospital.admin.board;

import com.hospital.admin.board.dto.AdminBoardSearchDTO;
import com.hospital.common.util.DBConnection;
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
            "BP.POST_NO, BP.POST_TYPE, BP.TITLE, BP.CONTENT, BP.ADMIN_ID, "
                    + "NVL(A.NAME, BP.ADMIN_ID) ADMIN_NAME, BP.VIEW_COUNT, BP.CREATED_AT";

    public int selectAdminBoardPostCount(AdminBoardSearchDTO searchDTO) throws SQLException {
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

    public List<BoardPostDTO> selectAdminBoardPostList(AdminBoardSearchDTO searchDTO) throws SQLException {
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

    public BoardPostDTO selectAdminBoardPostById(int postId) throws SQLException {
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

    public int insertBoardPost(BoardPostDTO boardPost) throws SQLException {
        String sql = "INSERT INTO BOARD_POST ("
                + "POST_NO, ADMIN_ID, TITLE, CONTENT, VIEW_COUNT, POST_TYPE"
                + ") VALUES ("
                + "SEQ_BOARD_POST.NEXTVAL, ?, ?, ?, 0, ?"
                + ")";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            bindBoardPostForm(pstmt, boardPost);
            return pstmt.executeUpdate();
        }
    }

    public int updateBoardPost(BoardPostDTO boardPost) throws SQLException {
        String sql = "UPDATE BOARD_POST "
                + "SET ADMIN_ID = ?, "
                + "TITLE = ?, "
                + "CONTENT = ?, "
                + "POST_TYPE = ? "
                + "WHERE POST_NO = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            bindBoardPostForm(pstmt, boardPost);
            pstmt.setInt(5, boardPost.getPostId());
            return pstmt.executeUpdate();
        }
    }

    public int deleteBoardPost(int postId) throws SQLException {
        String sql = "DELETE FROM BOARD_POST WHERE POST_NO = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, postId);
            return pstmt.executeUpdate();
        }
    }

    private void bindBoardPostForm(PreparedStatement pstmt, BoardPostDTO boardPost) throws SQLException {
        pstmt.setString(1, boardPost.getWriterId());
        pstmt.setString(2, boardPost.getTitle());
        pstmt.setString(3, boardPost.getContent());
        pstmt.setString(4, boardPost.getCategory());
    }
}
