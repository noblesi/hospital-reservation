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

public class AdminBoardDeleteServlet extends HttpServlet {
    private final AdminBoardService adminBoardService = new AdminBoardService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int postId = parseInt(request.getParameter("postId"), 0);

        try {
            BoardPostDTO boardPost = adminBoardService.getBoardPost(postId);
            String category = boardPost == null ? BoardSearchDTO.CATEGORY_NOTICE : boardPost.getCategory();
            adminBoardService.deleteBoardPost(postId);
            request.getSession().setAttribute("message", "게시글이 삭제되었습니다.");
            response.sendRedirect(request.getContextPath() + getListUrl(category));
        } catch (SQLException e) {
            throw new ServletException("게시글을 삭제하지 못했습니다.", e);
        }
    }

    private String getListUrl(String category) {
        return BoardSearchDTO.CATEGORY_FAQ.equals(category) ? "/admin/faq/list.do" : "/admin/notice/list.do";
    }

    private int parseInt(String value, int defaultValue) {
        try {
            return value == null ? defaultValue : Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
