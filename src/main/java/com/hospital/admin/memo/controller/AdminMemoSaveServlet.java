package com.hospital.admin.memo.controller;

import com.hospital.admin.memo.AdminMemoService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class AdminMemoSaveServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final AdminMemoService adminMemoService = new AdminMemoService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String patientNo = request.getParameter("patientNo");
        String adminId = defaultValue(request.getParameter("adminId"), "system");
        String content = request.getParameter("content");

        try {
            if (adminMemoService.addMemo(patientNo, adminId, content)) {
                request.getSession().setAttribute("message", "회원 메모를 등록했습니다.");
            } else {
                request.getSession().setAttribute("errorMessage", "회원 메모 내용을 확인해 주세요.");
            }
        } catch (SQLException e) {
            throw new ServletException("회원 메모를 등록하지 못했습니다.", e);
        }

        response.sendRedirect(request.getContextPath() + "/admin/member/detail?patientNo=" + defaultValue(patientNo, ""));
    }

    private String defaultValue(String value, String defaultValue) {
        return value == null || value.isBlank() ? defaultValue : value.trim();
    }
}
