package com.hospital.user.appointment.controller;

import java.io.IOException;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.common.dto.DepartmentDTO;
import com.hospital.common.dto.DoctorDTO;
import com.hospital.common.dto.DoctorScheduleDTO;
import com.hospital.user.appointment.UserAppointmentService;

public class UserAppointmentAjaxServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final UserAppointmentService userAppointmentService = new UserAppointmentService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json; charset=UTF-8");

        String action = request.getParameter("action");

        if ("sort".equals(action)) {
            renderDepartmentList(request, response);
        } else if ("searchDoctor".equals(action)) {
            renderDoctorSearch(request, response);
        } else if ("doctorList".equals(action)) {
            renderDoctorList(request, response);
        } else if ("schedule".equals(action)) {
            renderSchedule(request, response);
        } else if ("timeTable".equals(action)) {
            renderTimeTable(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    /**
     * 진료과 목록에 필요한 데이터를 json으로 출력
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    private void renderDepartmentList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<DepartmentDTO> departmentList = new ArrayList<>(userAppointmentService.searchDepartmentList());

        if ("ascending".equals(request.getParameter("sort"))) {
            departmentList.sort(Comparator.comparing(
                DepartmentDTO::getDeptName, Comparator.nullsLast(String::compareTo)
            ));
        }

        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < departmentList.size(); i++) {
            DepartmentDTO dept = departmentList.get(i);
            if (i > 0) json.append(",");
            json.append("{")
                .append("\"deptNo\":\"").append(escapeJson(dept.getDeptNo())).append("\",")
                .append("\"deptName\":\"").append(escapeJson(dept.getDeptName())).append("\"")
                .append("}");
        }
        json.append("]");
        
        response.getWriter().print(json);
    }

    /**
     * 의료진 검색에 필요한 데이터를 json 으로 출력
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    private void renderDoctorSearch(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String keyword = request.getParameter("keyword");
        List<DoctorDTO> doctorList = userAppointmentService.searchDoctorListByKeyword(keyword);
        renderDoctorJson(response, doctorList, null);
    }

    /**
     * 진료과별 의료진 목록에 필요한 데이터를 json 으로 전송
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    private void renderDoctorList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String deptNo = request.getParameter("deptNo");
        
        if (UserAppointmentSessionUtil.isBlank(deptNo)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        List<DoctorDTO> doctorList = userAppointmentService.searchDoctorList(deptNo);
        renderDoctorJson(response, doctorList, request.getParameter("deptName"));
    }

    /**
     * 키워드 검색과 진료과별 검색에 중복되는 코드 분리
     * 
     * @param response
     * @param doctorList
     * @param deptName
     * @throws IOException
     */
    private void renderDoctorJson(HttpServletResponse response, List<DoctorDTO> doctorList, String deptName)
            throws IOException {
        StringBuilder json = new StringBuilder("{");
        json.append("\"deptName\":\"").append(escapeJson(deptName)).append("\",");
        json.append("\"doctors\":[");

        for (int i = 0; i < doctorList.size(); i++) {
            DoctorDTO doctor = doctorList.get(i);
            if (i > 0) json.append(",");
            json.append("{")
                .append("\"doctorLicenseNo\":").append(doctor.getDoctorLicenseNo()).append(",")
                .append("\"name\":\"").append(escapeJson(doctor.getName())).append("\",")
                .append("\"thumbnailUrl\":\"").append(escapeJson(doctor.getThumbnailUrl())).append("\",")
                .append("\"specialty\":\"").append(escapeJson(doctor.getSpecialty())).append("\"")
                .append("}");
        }
        json.append("]}");
        response.getWriter().print(json);
    }

    /**
     * 달력 출력에 필요한 데이터를 json으로 전송.
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    private void renderSchedule(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer doctorLicenseNo = parseInt(request.getParameter("dln"));
        if (doctorLicenseNo == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        LocalDate calendarDate = LocalDate.now();
        Integer year  = parseInt(request.getParameter("year"));
        Integer month = parseInt(request.getParameter("month"));
        if (year != null) calendarDate = calendarDate.withYear(year);
        if (month != null && month >= 1 && month <= 12) calendarDate = calendarDate.withMonth(month);

        List<DoctorScheduleDTO> scheduleList = userAppointmentService.searchDoctorSchedule(doctorLicenseNo);
        List<Integer> allDay = new ArrayList<>();
        List<Integer> am     = new ArrayList<>();
        List<Integer> pm     = new ArrayList<>();

        for (DoctorScheduleDTO schedule : scheduleList) {
            if ("전일".equals(schedule.getStatus()))       allDay.add(schedule.getDayOfWeek());
            else if ("오전".equals(schedule.getStatus())) am.add(schedule.getDayOfWeek());
            else if ("오후".equals(schedule.getStatus())) pm.add(schedule.getDayOfWeek());
        }

        // 날짜별 상태 배열 생성
        LocalDate today    = LocalDate.now();
        LocalDate firstDay = calendarDate.withDayOfMonth(1);
        int blankCount     = firstDay.getDayOfWeek().getValue() % 7;

        StringBuilder json = new StringBuilder("{");
        json.append("\"year\":").append(calendarDate.getYear()).append(",");
        json.append("\"month\":").append(calendarDate.getMonthValue()).append(",");
        json.append("\"blankCount\":").append(blankCount).append(",");
        json.append("\"days\":[");

        for (int day = 1; day <= calendarDate.lengthOfMonth(); day++) {
            LocalDate current  = calendarDate.withDayOfMonth(day);
            int dayOfWeek      = current.getDayOfWeek().getValue();
            boolean isWeekend  = current.getDayOfWeek() == DayOfWeek.SATURDAY;

            String status = "";
            if (allDay.contains(dayOfWeek) && current.isAfter(today))      status = "allDay";
            else if (am.contains(dayOfWeek) && current.isAfter(today))     status = "am";
            else if (pm.contains(dayOfWeek) && current.isAfter(today))     status = "pm";

            if (day > 1) json.append(",");
            json.append("{")
                .append("\"day\":").append(day).append(",")
                .append("\"date\":\"").append(current).append("\",")
                .append("\"status\":\"").append(status).append("\",")
                .append("\"isSaturday\":").append(isWeekend)
                .append("}");
        }
        json.append("]}");
        response.getWriter().print(json);
    }

    /**
     * 타임 테이블에 필요한 데이터를 json으로 전송.
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    private void renderTimeTable(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer doctorLicenseNo = parseInt(request.getParameter("dln"));
        String date = request.getParameter("date");

        if (doctorLicenseNo == null || UserAppointmentSessionUtil.isBlank(date)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        List<String> availableTimes = userAppointmentService.searchAvailableTime(doctorLicenseNo, Date.valueOf(date));

        StringBuilder json = new StringBuilder("{\"times\":[");
        for (int i = 0; i < availableTimes.size(); i++) {
            if (i > 0) json.append(",");
            json.append("\"").append(escapeJson(availableTimes.get(i))).append("\"");
        }
        json.append("]}");
        response.getWriter().print(json);
    }

    private Integer parseInt(String value) {
        if (UserAppointmentSessionUtil.isBlank(value) || !value.matches("\\d+")) return null;
        return Integer.valueOf(value);
    }

    // JSON 문자열 이스케이프 (HTML과 별도)
    private String escapeJson(String value) {
        if (value == null) return "";
        return value //
            .replace("\\", "\\\\") //
            .replace("\"", "\\\"") //
            .replace("\n", "\\n") //
            .replace("\r", "\\r") //
            .replace("\t", "\\t");
    }
}