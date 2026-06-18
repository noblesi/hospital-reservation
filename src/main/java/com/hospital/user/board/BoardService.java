package com.hospital.user.board;

import com.hospital.common.dto.BoardPostDTO;
import com.hospital.common.util.PaginationUtil;
import com.hospital.user.board.dto.BoardSearchDTO;

import java.sql.SQLException;
import java.util.List;

public class BoardService {
    private final BoardDAO boardDAO;

    public BoardService() {
        this(new BoardDAO());
    }

    public BoardService(BoardDAO boardDAO) {
        this.boardDAO = boardDAO;
    }

    public List<BoardPostDTO> getBoardPostList(BoardSearchDTO searchDTO) throws SQLException {
        int totalCount = boardDAO.selectBoardPostCount(searchDTO);
        PaginationUtil.Pagination pagination = PaginationUtil.create(
                searchDTO.getCurrentPage(),
                totalCount,
                searchDTO.getPageScale()
        );

        searchDTO.applyPagination(pagination);
        return boardDAO.selectBoardPostList(searchDTO);
    }

    public PaginationUtil.Pagination getPagination(BoardSearchDTO searchDTO) throws SQLException {
        int totalCount = boardDAO.selectBoardPostCount(searchDTO);
        PaginationUtil.Pagination pagination = PaginationUtil.create(
                searchDTO.getCurrentPage(),
                totalCount,
                searchDTO.getPageScale()
        );
        searchDTO.applyPagination(pagination);
        return pagination;
    }

    public BoardPostDTO getBoardPostDetail(int postId) throws SQLException {
        boardDAO.increaseViewCount(postId);
        return boardDAO.selectBoardPostById(postId);
    }
}
