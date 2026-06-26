package com.hospital.admin.member;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.common.MemberDTO;

@WebServlet("/admin/member/detail")
public class AdminMemberDetailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(AdminMemberDetailServlet.class.getName());

    private final AdminMemberService adminMemberService = new AdminMemberService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String patientNo = request.getParameter("patientNo");
        if (patientNo == null || patientNo.trim().isEmpty()) {
            patientNo = request.getParameter("memberNo");
        }

        if (patientNo == null || patientNo.trim().isEmpty()) {
            LOGGER.fine("patientNo parameter is missing");
            response.sendRedirect(request.getContextPath() + "/admin/member/list.do");
            return;
        }

        MemberDTO member = adminMemberService.getMemberDetail(patientNo.trim());

        if (member == null) {
            LOGGER.fine("member detail not found: " + patientNo);
            response.sendRedirect(request.getContextPath() + "/admin/member/list.do");
            return;
        }

        request.setAttribute("adminMenu", "member");
        request.setAttribute("member", member);
        request.getRequestDispatcher("/views/admin/member/memberDetail.jsp").forward(request, response);
    }
}

