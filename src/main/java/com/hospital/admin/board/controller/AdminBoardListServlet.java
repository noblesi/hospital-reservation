package com.hospital.admin.board.controller;

import com.hospital.admin.board.AdminBoardService;
import com.hospital.admin.board.dto.AdminBoardSearchDTO;
import com.hospital.common.dto.BoardPostDTO;
import com.hospital.common.util.PaginationUtil;
import com.hospital.user.board.dto.BoardSearchDTO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AdminBoardListServlet extends HttpServlet {
    private final AdminBoardService adminBoardService = new AdminBoardService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AdminBoardSearchDTO searchDTO = createSearchDTO(request);

        try {
            List<BoardPostDTO> boardPostList = adminBoardService.getBoardPostList(searchDTO);
            PaginationUtil.Pagination pagination = adminBoardService.getPagination(searchDTO);

            request.setAttribute("boardPostList", boardPostList);
            request.setAttribute("searchDTO", searchDTO);
            request.setAttribute("pagination", pagination);
            request.setAttribute("baseUrl", getBaseUrl(searchDTO));
            request.setAttribute("adminMenu", BoardSearchDTO.CATEGORY_FAQ.equals(searchDTO.getCategory()) ? "faq" : "notice");

            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/board/list.jsp");
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
        searchDTO.setDisplayYn(request.getParameter("displayYn"));
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

    private int parseInt(String value, int defaultValue) {
        try {
            return value == null ? defaultValue : Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
