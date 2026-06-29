<%@page import="com.hospital.admin.doctor.dto.AdminDoctorSearchDTO"%>
<%@page import="com.hospital.common.dto.DoctorDTO"%>
<%@page import="com.hospital.common.dto.DoctorStatusDTO"%>
<%@page import="com.hospital.admin.doctor.dto.AdminDoctorFormOptionDTO"%>
<%@page import="com.hospital.common.dto.DoctorPositionDTO"%>
<%@page import="com.hospital.admin.department.AdminDepartmentService"%>
<%@page import="com.hospital.admin.doctor.AdminDoctorService"%>
<%@page import="com.hospital.admin.doctor.controller.AdminDoctorListServlet"%>
<%@page import="com.hospital.admin.doctor.controller.AdminDoctorListViewServlet"%>
<%@page import="com.hospital.common.dto.DepartmentDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="adminMenu" value="reservation" scope="request" />

<% 
String statusCode = (String)request.getParameter("statusCode");
String doctorLicenseNo = (String)request.getParameter("doctorLicenseNo");
AdminDoctorService adminDoctorService = new AdminDoctorService();
adminDoctorService.changeDoctorStatus(Integer.parseInt(doctorLicenseNo), statusCode);
response.sendRedirect("http://localhost/hospital-reservation/views/admin/doctor/adminDoctorListView.jsp");
%>