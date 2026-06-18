package com.hospital.admin.board;

import com.hospital.admin.board.dto.AdminBoardSearchDTO;
import com.hospital.common.dto.BoardPostDTO;
import com.hospital.common.util.PaginationUtil;

import java.sql.SQLException;
import java.util.List;

public class AdminBoardService {
    private final AdminBoardDAO adminBoardDAO;

    public AdminBoardService() {
        this(new AdminBoardDAO());
    }

    public AdminBoardService(AdminBoardDAO adminBoardDAO) {
        this.adminBoardDAO = adminBoardDAO;
    }

    public List<BoardPostDTO> getBoardPostList(AdminBoardSearchDTO searchDTO) throws SQLException {
        PaginationUtil.Pagination pagination = getPagination(searchDTO);
        searchDTO.applyPagination(pagination);
        return adminBoardDAO.selectAdminBoardPostList(searchDTO);
    }

    /**
     * 관리자 게시글 목록과 pagination 정보를 한 번의 count 조회 흐름으로 함께 반환한다.
     */
    public AdminBoardPostPage getBoardPostPage(AdminBoardSearchDTO searchDTO) throws SQLException {
        PaginationUtil.Pagination pagination = getPagination(searchDTO);
        searchDTO.applyPagination(pagination);
        return new AdminBoardPostPage(adminBoardDAO.selectAdminBoardPostList(searchDTO), pagination);
    }

    public PaginationUtil.Pagination getPagination(AdminBoardSearchDTO searchDTO) throws SQLException {
        int totalCount = adminBoardDAO.selectAdminBoardPostCount(searchDTO);
        return PaginationUtil.create(searchDTO.getCurrentPage(), totalCount, searchDTO.getPageScale());
    }

    public BoardPostDTO getBoardPost(int postId) throws SQLException {
        return adminBoardDAO.selectAdminBoardPostById(postId);
    }

    public void saveBoardPost(BoardPostDTO boardPost) throws SQLException {
        if (boardPost.getPostId() > 0) {
            adminBoardDAO.updateBoardPost(boardPost);
            return;
        }

        adminBoardDAO.insertBoardPost(boardPost);
    }

    public void deleteBoardPost(int postId) throws SQLException {
        adminBoardDAO.deleteBoardPost(postId);
    }

    public static class AdminBoardPostPage {
        private final List<BoardPostDTO> boardPostList;
        private final PaginationUtil.Pagination pagination;

        /**
         * 관리자 게시글 목록 조회 결과와 pagination 정보를 묶는다.
         */
        public AdminBoardPostPage(List<BoardPostDTO> boardPostList, PaginationUtil.Pagination pagination) {
            this.boardPostList = boardPostList;
            this.pagination = pagination;
        }

        /**
         * 조회된 관리자 게시글 목록을 반환한다.
         */
        public List<BoardPostDTO> getBoardPostList() {
            return boardPostList;
        }

        /**
         * 목록 조회에 사용한 pagination 정보를 반환한다.
         */
        public PaginationUtil.Pagination getPagination() {
            return pagination;
        }
    }
}
