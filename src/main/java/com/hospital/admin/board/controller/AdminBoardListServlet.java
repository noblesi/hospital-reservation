package com.hospital.admin.board.controller;

import com.hospital.admin.board.AdminBoardService;
import com.hospital.admin.board.dto.AdminBoardSearchDTO;
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

public class AdminBoardListServlet extends HttpServlet {
    private final AdminBoardService adminBoardService = new AdminBoardService();

    /**
     * 관리자 게시판 목록 요청을 처리하고 목록, pagination, 검색조건을 JSP로 전달한다.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AdminBoardSearchDTO searchDTO = createSearchDTO(request);

        try {
            AdminBoardService.AdminBoardPostPage boardPostPage = adminBoardService.getBoardPostPage(searchDTO);

            request.setAttribute("boardPostList", boardPostPage.getBoardPostList());
            request.setAttribute("searchDTO", searchDTO);
            request.setAttribute("pagination", boardPostPage.getPagination());
            request.setAttribute("baseUrl", getBaseUrl(searchDTO));
            request.setAttribute("paginationQueryString", buildPaginationQueryString(searchDTO));
            request.setAttribute("adminMenu", BoardSearchDTO.CATEGORY_FAQ.equals(searchDTO.getCategory()) ? "faq" : "notice");

            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/board/adminBoardList.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("관리자 게시글 목록을 조회하지 못했습니다.", e);
        }
    }

    private AdminBoardSearchDTO createSearchDTO(HttpServletRequest request) {
        AdminBoardSearchDTO searchDTO = new AdminBoardSearchDTO();
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

    private String getBaseUrl(AdminBoardSearchDTO searchDTO) {
        return BoardSearchDTO.CATEGORY_FAQ.equals(searchDTO.getCategory())
                ? "/admin/faq/list.do"
                : "/admin/notice/list.do";
    }

    /**
     * pagination link에 유지할 관리자 검색조건 query string을 생성한다.
     */
    private String buildPaginationQueryString(AdminBoardSearchDTO searchDTO) {
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
