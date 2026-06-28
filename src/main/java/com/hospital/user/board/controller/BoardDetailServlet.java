package com.hospital.user.board.controller;

import com.hospital.common.dto.BoardPostDTO;
import com.hospital.user.board.BoardService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class BoardDetailServlet extends HttpServlet {
    private final BoardService boardService = new BoardService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int postId = parseInt(request.getParameter("postId"), 0);
        if (postId <= 0) {
            response.sendRedirect(request.getContextPath() + "/board/notice/list.do");
            return;
        }

        try {
            BoardPostDTO boardPost = boardService.getBoardPostDetail(postId);
            if (boardPost == null) {
                response.sendRedirect(request.getContextPath() + "/board/notice/list.do");
                return;
            }

            request.setAttribute("boardPost", boardPost);
            request.setAttribute("activeMenu", "hospital");
            request.setAttribute("depth1", "병원소개");
            request.setAttribute("depth2", boardPost.getCategoryName());

            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/user/board/userBoardDetail.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("게시글 상세 정보를 조회하지 못했습니다.", e);
        }
    }

    private int parseInt(String value, int defaultValue) {
        try {
            return value == null ? defaultValue : Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
