package com.hospital.user.board.controller;

import com.hospital.common.dto.BoardPostDTO;
import com.hospital.common.util.PaginationUtil;
import com.hospital.user.board.BoardService;
import com.hospital.user.board.dto.BoardSearchDTO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class BoardListServlet extends HttpServlet {
    private final BoardService boardService = new BoardService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BoardSearchDTO searchDTO = createSearchDTO(request);

        try {
            List<BoardPostDTO> boardPostList = boardService.getBoardPostList(searchDTO);
            PaginationUtil.Pagination pagination = boardService.getPagination(searchDTO);

            request.setAttribute("boardPostList", boardPostList);
            request.setAttribute("searchDTO", searchDTO);
            request.setAttribute("pagination", pagination);
            request.setAttribute("baseUrl", getBaseUrl(searchDTO));
            request.setAttribute("activeMenu", "hospital");
            request.setAttribute("depth1", "병원소개");
            request.setAttribute("depth2", searchDTO.getCategoryName());

            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/user/board/list.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("게시글 목록을 조회하지 못했습니다.", e);
        }
    }

    private BoardSearchDTO createSearchDTO(HttpServletRequest request) {
        BoardSearchDTO searchDTO = new BoardSearchDTO();
        searchDTO.setCategory(resolveCategory(request));
        searchDTO.setSearchType(request.getParameter("searchType"));
        searchDTO.setKeyword(request.getParameter("keyword"));
        searchDTO.setCurrentPage(parseInt(request.getParameter("currentPage"), 1));
        return searchDTO;
    }

    private String resolveCategory(HttpServletRequest request) {
        String category = request.getParameter("category");
        if (category != null && !category.isBlank()) {
            return category;
        }

        String uri = request.getRequestURI();
        return uri != null && uri.contains("/faq/") ? BoardSearchDTO.CATEGORY_FAQ : BoardSearchDTO.CATEGORY_NOTICE;
    }

    private String getBaseUrl(BoardSearchDTO searchDTO) {
        return BoardSearchDTO.CATEGORY_FAQ.equals(searchDTO.getCategory())
                ? "/board/faq/list.do"
                : "/board/notice/list.do";
    }

    private int parseInt(String value, int defaultValue) {
        try {
            return value == null ? defaultValue : Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
