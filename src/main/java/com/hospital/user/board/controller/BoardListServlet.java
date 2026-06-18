package com.hospital.user.board.controller;

import com.hospital.user.board.BoardService;
import com.hospital.user.board.dto.BoardSearchDTO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class BoardListServlet extends HttpServlet {
    private final BoardService boardService = new BoardService();

    /**
     * 사용자 게시판 목록 요청을 처리하고 목록, pagination, 검색조건을 JSP로 전달한다.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BoardSearchDTO searchDTO = createSearchDTO(request);

        try {
            BoardService.BoardPostPage boardPostPage = boardService.getBoardPostPage(searchDTO);

            request.setAttribute("boardPostList", boardPostPage.getBoardPostList());
            request.setAttribute("searchDTO", searchDTO);
            request.setAttribute("pagination", boardPostPage.getPagination());
            request.setAttribute("baseUrl", getBaseUrl(searchDTO));
            request.setAttribute("paginationQueryString", buildPaginationQueryString(searchDTO));
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

    /**
     * pagination link에 유지할 검색조건 query string을 생성한다.
     */
    private String buildPaginationQueryString(BoardSearchDTO searchDTO) {
        StringBuilder queryString = new StringBuilder();
        appendQueryParam(queryString, "searchType", searchDTO.getSearchType());
        appendQueryParam(queryString, "keyword", searchDTO.getKeyword());
        return queryString.toString();
    }

    /**
     * 값이 있는 검색조건만 URL encoding해서 query string에 추가한다.
     */
    private void appendQueryParam(StringBuilder queryString, String name, String value) {
        if (value == null || value.isBlank()) {
            return;
        }

        queryString.append('&')
                .append(name)
                .append('=')
                .append(URLEncoder.encode(value, StandardCharsets.UTF_8));
    }

    private int parseInt(String value, int defaultValue) {
        try {
            return value == null ? defaultValue : Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
