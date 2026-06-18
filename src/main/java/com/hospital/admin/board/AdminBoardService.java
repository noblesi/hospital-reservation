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
}
