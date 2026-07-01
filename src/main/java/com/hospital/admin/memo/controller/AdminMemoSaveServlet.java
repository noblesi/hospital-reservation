package com.hospital.admin.memo.controller;

import com.hospital.admin.memo.AdminMemoService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Map;

public class AdminMemoSaveServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final AdminMemoService adminMemoService = new AdminMemoService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String adminId = getLoginAdminId(request);
        if (isBlank(adminId)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "관리자 로그인 후 이용해 주세요.");
            return;
        }

        String patientNo = request.getParameter("patientNo");
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

        response.sendRedirect(request.getContextPath() + "/admin/member/detail?patientNo=" + encode(defaultValue(patientNo, "")));
    }

    private String defaultValue(String value, String defaultValue) {
        return value == null || value.isBlank() ? defaultValue : value.trim();
    }

    private String getLoginAdminId(HttpServletRequest request) throws ServletException {
        Object loginAdmin = request.getSession(false) == null ? null : request.getSession(false).getAttribute("loginAdmin");
        if (loginAdmin == null) {
            return null;
        }

        if (loginAdmin instanceof Map<?, ?>) {
            Object adminId = ((Map<?, ?>) loginAdmin).get("adminId");
            return adminId == null ? null : adminId.toString().trim();
        }

        try {
            for (PropertyDescriptor descriptor : Introspector.getBeanInfo(loginAdmin.getClass()).getPropertyDescriptors()) {
                if ("adminId".equals(descriptor.getName()) && descriptor.getReadMethod() != null) {
                    Object adminId = descriptor.getReadMethod().invoke(loginAdmin);
                    return adminId == null ? null : adminId.toString().trim();
                }
            }
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            throw new ServletException("관리자 세션 정보를 확인하지 못했습니다.", e);
        }

        return null;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
