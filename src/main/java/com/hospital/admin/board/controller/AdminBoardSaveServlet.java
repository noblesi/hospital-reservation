package com.hospital.admin.board.controller;

import com.hospital.admin.board.AdminBoardService;
import com.hospital.common.dto.BoardPostDTO;
import com.hospital.user.board.dto.BoardSearchDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class AdminBoardSaveServlet extends HttpServlet {
    private final AdminBoardService adminBoardService = new AdminBoardService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BoardPostDTO boardPost = createBoardPost(request);

        try {
            adminBoardService.saveBoardPost(boardPost);
            request.getSession().setAttribute("message", "게시글이 저장되었습니다.");
            response.sendRedirect(request.getContextPath() + getListUrl(boardPost.getCategory()));
        } catch (SQLException e) {
            throw new ServletException("게시글을 저장하지 못했습니다.", e);
        }
    }

    private BoardPostDTO createBoardPost(HttpServletRequest request) {
        BoardPostDTO boardPost = new BoardPostDTO();
        boardPost.setPostId(parseInt(request.getParameter("postId"), 0));
        boardPost.setCategory(request.getParameter("category"));
        boardPost.setTitle(request.getParameter("title"));
        boardPost.setContent(request.getParameter("content"));
        boardPost.setWriterId(defaultValue(request.getParameter("writerId"), "system"));
        return boardPost;
    }

    private String getListUrl(String category) {
        return BoardSearchDTO.CATEGORY_FAQ.equals(category) ? "/admin/faq/list.do" : "/admin/notice/list.do";
    }

    private String defaultValue(String value, String defaultValue) {
        return value == null || value.isBlank() ? defaultValue : value.trim();
    }

    private int parseInt(String value, int defaultValue) {
        try {
            return value == null ? defaultValue : Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
