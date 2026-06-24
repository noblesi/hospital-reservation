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

    /**
     * 게시글 목록과 pagination 정보를 한 번의 count 조회 흐름으로 함께 반환한다.
     */
    public BoardPostPage getBoardPostPage(BoardSearchDTO searchDTO) throws SQLException {
        int totalCount = boardDAO.selectBoardPostCount(searchDTO);
        PaginationUtil.Pagination pagination = PaginationUtil.create(
                searchDTO.getCurrentPage(),
                totalCount,
                searchDTO.getPageScale()
        );

        searchDTO.applyPagination(pagination);
        return new BoardPostPage(boardDAO.selectBoardPostList(searchDTO), pagination);
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

    public static class BoardPostPage {
        private final List<BoardPostDTO> boardPostList;
        private final PaginationUtil.Pagination pagination;

        /**
         * 게시글 목록 조회 결과와 pagination 정보를 묶는다.
         */
        public BoardPostPage(List<BoardPostDTO> boardPostList, PaginationUtil.Pagination pagination) {
            this.boardPostList = boardPostList;
            this.pagination = pagination;
        }

        /**
         * 조회된 게시글 목록을 반환한다.
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
