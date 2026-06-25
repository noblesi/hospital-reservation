package com.hospital.user.appointment.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.user.appointment.UserAppointmentService;
import com.hospital.user.appointment.dto.UserAppointmentConfirmDTO;
import com.hospital.user.appointment.dto.UserAppointmentRequestDTO;

@WebServlet("/appointment/process")
public class UserAppointmentProcessServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        // 1. 파라미터 데이터 수집 및 가공
        // String patientNo = (String) request.getSession().getAttribute("patientNo");
        String patientNo = "P00000001";
        
        int doctorLicenseNo = Integer.parseInt(request.getParameter("doctorLicenseNo"));
        Date appointmentDate = Date.valueOf(request.getParameter("appointmentDate"));
        String appointmentTime = request.getParameter("appointmentTime");
        String requirement = request.getParameter("requirement");
        String status = "승인 대기";

        UserAppointmentRequestDTO uarDTO = new UserAppointmentRequestDTO(
            patientNo, doctorLicenseNo, appointmentDate, appointmentTime, requirement, status
        );
        
        String modifyApptNo = request.getParameter("appointmentNo");

        // 2. 서비스 레이어 호출 및 비즈니스 로직 처리
        UserAppointmentService uas = new UserAppointmentService();
        
        try {
            if (modifyApptNo == null || modifyApptNo.trim().isEmpty()) {
                if (uas.checkReservable(uarDTO)) {
                    UserAppointmentConfirmDTO uacDTO = uas.reserveAppointment(uarDTO);
                    String apptNo = uacDTO.getAppointmentNo();
                    
                    response.sendRedirect("http://localhost/hospital-reservation/views/user/appointment/appointmentSuccess.jsp?apptNo=" + apptNo);
                    return;
                } else {
                    printAlert(out, "이미 예약된 시간입니다. 다시 시도해주세요.", "appointment.jsp");
                    return;
                }
            }

            if (modifyApptNo != null && !modifyApptNo.trim().isEmpty()) {
                if (uas.checkReservable(uarDTO)) {
                    UserAppointmentConfirmDTO uacDTO = uas.reserveAppointment(modifyApptNo, patientNo, uarDTO);
                    String apptNo = uacDTO.getAppointmentNo();
                    
                    response.sendRedirect("http://localhost/hospital-reservation/views/user/appointment/appointmentSuccess.jsp?apptNo=" + apptNo);
                    return;
                } else {
                    printAlert(out, "이미 예약된 시간입니다. 다시 시도해주세요.", "http://localhost/hospital-reservation/views/user/appointment/appointment.jsp");
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            printAlert(out, "서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.", "http://localhost/hospital-reservation/views/user/appointment/appointment.jsp");
        }
    }

    // 자바 스크립트 알림창 출력을 위한 헬퍼 메서드
    private void printAlert(PrintWriter out, String msg, String loc) {
        out.println("<script type='text/javascript'>");
        out.println("    alert('" + msg + "');");
        out.println("    location.href='" + loc + "';");
        out.println("</script>");
        out.flush();
    }
}