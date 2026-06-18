package com.hospital.admin.board.controller;

import com.hospital.admin.board.AdminBoardService;
import com.hospital.common.dto.BoardPostDTO;
import com.hospital.user.board.dto.BoardSearchDTO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class AdminBoardFormServlet extends HttpServlet {
    private final AdminBoardService adminBoardService = new AdminBoardService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int postId = parseInt(request.getParameter("postId"), 0);
        String category = resolveCategory(request);

        try {
            if (postId > 0) {
                BoardPostDTO boardPost = adminBoardService.getBoardPost(postId);
                request.setAttribute("boardPost", boardPost);
                category = boardPost == null ? category : boardPost.getCategory();
            }

            request.setAttribute("category", category);
            request.setAttribute("adminMenu", BoardSearchDTO.CATEGORY_FAQ.equals(category) ? "faq" : "notice");

            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/board/form.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("게시글 입력 화면을 조회하지 못했습니다.", e);
        }
    }

    private String resolveCategory(HttpServletRequest request) {
        String category = request.getParameter("category");
        if (category != null && !category.isBlank()) {
            return BoardSearchDTO.CATEGORY_FAQ.equalsIgnoreCase(category)
                    ? BoardSearchDTO.CATEGORY_FAQ
                    : BoardSearchDTO.CATEGORY_NOTICE;
        }

        String uri = request.getRequestURI();
        return uri != null && uri.contains("/faq/") ? BoardSearchDTO.CATEGORY_FAQ : BoardSearchDTO.CATEGORY_NOTICE;
    }

    private int parseInt(String value, int defaultValue) {
        try {
            return value == null ? defaultValue : Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
