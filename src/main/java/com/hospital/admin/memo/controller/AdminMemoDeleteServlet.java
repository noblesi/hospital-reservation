package com.hospital.admin.memo.controller;

import com.hospital.admin.memo.AdminMemoService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class AdminMemoDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final AdminMemoService adminMemoService = new AdminMemoService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession(false) == null || request.getSession(false).getAttribute("loginAdmin") == null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "관리자 로그인 후 이용해 주세요.");
            return;
        }

        int memoNo = parseInt(request.getParameter("memoNo"), 0);
        String patientNo = request.getParameter("patientNo");

        try {
            if (adminMemoService.deleteMemo(memoNo, patientNo)) {
                request.getSession().setAttribute("message", "회원 메모를 삭제했습니다.");
            } else {
                request.getSession().setAttribute("errorMessage", "삭제할 회원 메모를 찾지 못했습니다.");
            }
        } catch (SQLException e) {
            throw new ServletException("회원 메모를 삭제하지 못했습니다.", e);
        }

        response.sendRedirect(request.getContextPath() + "/admin/member/detail?patientNo=" + encode(defaultValue(patientNo, "")));
    }

    private int parseInt(String value, int defaultValue) {
        try {
            return value == null ? defaultValue : Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private String defaultValue(String value, String defaultValue) {
        return value == null || value.isBlank() ? defaultValue : value.trim();
    }

    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
