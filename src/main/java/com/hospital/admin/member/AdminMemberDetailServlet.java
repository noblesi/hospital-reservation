package com.hospital.admin.member;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.common.dto.MemberDTO;

@WebServlet("/admin/member/detail")
public class AdminMemberDetailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final AdminMemberService adminMemberService = new AdminMemberService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("=== AdminMemberDetailServlet doGet 호출 ===");

        String memberNoParam = request.getParameter("memberNo");
        System.out.println("memberNoParam = " + memberNoParam);

        if (memberNoParam == null || memberNoParam.trim().isEmpty()) {
            System.out.println("memberNoParam 없음 -> 목록으로 이동");
            response.sendRedirect(request.getContextPath() + "/views/admin/member/memberList.jsp");
            return;
        }

        try {
            int memberNo = Integer.parseInt(memberNoParam);
            System.out.println("memberNo = " + memberNo);

            MemberDTO member = adminMemberService.getMemberDetail(memberNo);
            System.out.println("member = " + member);

            if (member == null) {
                System.out.println("조회 결과 없음 -> 목록으로 이동");
                response.sendRedirect(request.getContextPath() + "/views/admin/member/memberList.jsp");
                return;
            }

            request.setAttribute("member", member);
            System.out.println("memberDetail.jsp로 forward");
            request.getRequestDispatcher("/views/admin/member/memberDetail.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            System.out.println("memberNo 숫자 변환 실패 -> 목록으로 이동");
            response.sendRedirect(request.getContextPath() + "/views/admin/member/memberList.jsp");
        }
    }
}

