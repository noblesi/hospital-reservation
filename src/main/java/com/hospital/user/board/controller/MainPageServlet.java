package com.hospital.user.board.controller;

import com.hospital.user.MainPageService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;

public class MainPageServlet extends HttpServlet {
    private final MainPageService mainPageService = new MainPageService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("recentNoticeList", getRecentNoticeList());
        request.setAttribute("recentFaqList", getRecentFaqList());
        request.setAttribute("activeMenu", "hospital");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/user/main.jsp");
        dispatcher.forward(request, response);
    }

    private Object getRecentNoticeList() {
        try {
            return mainPageService.getRecentNoticeList();
        } catch (SQLException e) {
            log("메인 공지사항 조회 실패", e);
            return Collections.emptyList();
        }
    }

    private Object getRecentFaqList() {
        try {
            return mainPageService.getRecentFaqList();
        } catch (SQLException e) {
            log("메인 FAQ 조회 실패", e);
            return Collections.emptyList();
        }
    }
}
