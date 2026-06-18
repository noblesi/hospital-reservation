package com.hospital.admin.member;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.common.dto.MemberDTO;

// 관리자 회원 상세 조회 서블릿
@WebServlet("/admin/member/detail")
public class AdminMemberDetailServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;

    // AdminMemberService 객체 생성
    private final AdminMemberService adminMemberService = new AdminMemberService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. memberList.jsp 에서 전달된 memberNo 받기
        String memberNoParam = request.getParameter("memberNo");

        // 2. memberNo 가 없으면 회원 목록으로 이동
        if (memberNoParam == null || memberNoParam.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/views/admin/member/memberList.jsp");
            return;
        }

        try {
            // 3. 문자열 memberNo 를 숫자로 변환
            int memberNo = Integer.parseInt(memberNoParam);

            // 4. Service 호출해서 회원 상세 조회
            MemberDTO member = adminMemberService.getMemberDetail(memberNo);

            // 5. 조회된 회원 정보를 request 영역에 저장
            request.setAttribute("member", member);

            // 6. 상세 JSP 로 이동
            RequestDispatcher dispatcher =
                    request.getRequestDispatcher("/views/admin/member/memberDetail.jsp");
            dispatcher.forward(request, response);

        } catch (NumberFormatException e) {
            // 7. memberNo 가 숫자가 아니면 목록으로 이동
            response.sendRedirect(request.getContextPath() + "/views/admin/member/memberList.jsp");
        }
    }
}
