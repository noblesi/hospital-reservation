<%@page import="com.hospital.common.dto.DoctorEducationDTO"%>
<%@page import="com.hospital.common.dto.DoctorScheduleDTO"%>
<%@page import="com.hospital.common.dto.DoctorCareerDTO"%>
<%@page import="com.hospital.common.dto.DoctorStatusDTO"%>
<%@page import="com.hospital.common.dto.DoctorPositionDTO"%>
<%@page import="com.hospital.common.dto.DoctorDTO"%>
<%@page import="com.hospital.admin.doctor.dto.AdminDoctorFormDTO"%>
<%@page import="com.hospital.admin.doctor.dto.AdminDoctorSearchDTO"%>
<%@page import="com.hospital.admin.doctor.AdminDoctorService"%>
<%@page import="com.hospital.common.dto.DepartmentDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:useBean id="doctorDTO" class="com.hospital.common.dto.DoctorDTO" scope="page"/>
<jsp:setProperty name="doctorDTO" property="*" />

<% 
request.setCharacterEncoding("UTF-8");

AdminDoctorService adminDoctorService = new AdminDoctorService();
AdminDoctorFormDTO adminDoctorFormDTO = new AdminDoctorFormDTO();
doctorDTO.setStatusCode("CLS");
adminDoctorFormDTO.setDoctorDTO(doctorDTO);
String[] educationYear = request.getParameterValues("educatgionYear[]");// reqest로 받아서 String[]에 담아서 값 저장  

if(doctorDTO != null){
	adminDoctorService.registerDoctor(adminDoctorFormDTO);
}
//System.out.println("나간다~=================");
%>