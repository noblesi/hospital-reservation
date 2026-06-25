package com.hospital.user.appointment.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.user.appointment.UserAppointmentService;

@WebServlet("/appointment/cancel")
public class UserAppointmentCancelServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        // 1. 파라미터 데이터 수집
        // String patientNo = (String) request.getSession().getAttribute("patientNo");
        String patientNo = "P00000001"; 
        String appointmentNo = request.getParameter("appointmentNo");

        // 2. 서비스 레이어 호출
        UserAppointmentService uas = new UserAppointmentService();
        boolean cancelFlag = uas.cancelAppointment(appointmentNo, patientNo);

        out.println("<script type='text/javascript'>");
        if (cancelFlag) {
            out.println("    alert('예약이 취소되었습니다.');");
        } else {
            out.println("    alert('예약 취소가 실패했습니다. 잠시 후 다시 시도해 주세요.');");
        }
        out.println("    location.href='appointmentList.jsp';");
        out.println("</script>");
        out.flush();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}