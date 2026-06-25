package com.hospital.admin.member;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.common.dto.MemberDTO;

@WebServlet("/admin/member/detail")
public class AdminMemberDetailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(AdminMemberDetailServlet.class.getName());

    private final AdminMemberService adminMemberService = new AdminMemberService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String memberNoParam = request.getParameter("memberNo");

        if (memberNoParam == null || memberNoParam.trim().isEmpty()) {
            LOGGER.fine("memberNo parameter is missing");
            response.sendRedirect(request.getContextPath() + "/admin/member/list.do");
            return;
        }

        try {
            int memberNo = Integer.parseInt(memberNoParam);
            MemberDTO member = adminMemberService.getMemberDetail(memberNo);

            if (member == null) {
                LOGGER.fine("member detail not found: " + memberNo);
                response.sendRedirect(request.getContextPath() + "/admin/member/list.do");
                return;
            }

            request.setAttribute("member", member);
            request.getRequestDispatcher("/views/admin/member/memberDetail.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            LOGGER.fine("invalid memberNo parameter: " + memberNoParam);
            response.sendRedirect(request.getContextPath() + "/admin/member/list.do");
        }
    }
}

