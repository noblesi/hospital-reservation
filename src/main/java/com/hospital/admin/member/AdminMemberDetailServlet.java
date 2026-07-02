package com.hospital.admin.member;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.admin.memo.AdminMemoService;
import com.hospital.common.MemberDTO;

public class AdminMemberDetailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(AdminMemberDetailServlet.class.getName());

    private final AdminMemberService adminMemberService = new AdminMemberService();
    private final AdminMemoService adminMemoService = new AdminMemoService();

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

        try {
            MemberDTO member = adminMemberService.getMemberDetail(patientNo.trim());

            if (member == null) {
                LOGGER.fine("member detail not found: " + patientNo);
                response.sendRedirect(request.getContextPath() + "/admin/member/list.do");
                return;
            }

            request.setAttribute("adminMenu", "member");
            request.setAttribute("member", member);
            request.setAttribute("memoList", adminMemoService.getMemoList(member.getPatientNo()));
            request.getRequestDispatcher("/views/admin/member/adminMemberDetail.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("회원 메모 목록을 조회하지 못했습니다.", e);
        }
    }
}

