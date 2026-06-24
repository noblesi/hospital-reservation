package com.hospital.user.board.controller;

import com.hospital.user.MainPageService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class MainPageServlet extends HttpServlet {
    private final MainPageService mainPageService = new MainPageService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("recentNoticeList", mainPageService.getRecentNoticeList());
            request.setAttribute("recentFaqList", mainPageService.getRecentFaqList());
            request.setAttribute("activeMenu", "hospital");

            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/user/main.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("메인 페이지를 조회하지 못했습니다.", e);
        }
    }
}
