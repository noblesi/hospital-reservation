package com.hospital.user;

import com.hospital.common.dto.BoardPostDTO;
import com.hospital.user.board.BoardDAO;
import com.hospital.user.board.dto.BoardSearchDTO;

import java.sql.SQLException;
import java.util.List;

public class MainPageService {
    private final BoardDAO boardDAO;

    public MainPageService() {
        this(new BoardDAO());
    }

    public MainPageService(BoardDAO boardDAO) {
        this.boardDAO = boardDAO;
    }

    public List<BoardPostDTO> getRecentNoticeList() throws SQLException {
        return getRecentBoardList(BoardSearchDTO.CATEGORY_NOTICE, 5);
    }

    public List<BoardPostDTO> getRecentFaqList() throws SQLException {
        return getRecentBoardList(BoardSearchDTO.CATEGORY_FAQ, 5);
    }

    private List<BoardPostDTO> getRecentBoardList(String category, int limit) throws SQLException {
        BoardSearchDTO searchDTO = new BoardSearchDTO();
        searchDTO.setCategory(category);
        searchDTO.setPageScale(limit);
        searchDTO.updatePagingRange();
        return boardDAO.selectBoardPostList(searchDTO);
    }
}
