<%@page import="com.hospital.admin.department.AdminDepartmentService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%
	request.setCharacterEncoding("UTF-8");
	AdminDepartmentService adminDepartmentService = new AdminDepartmentService();
    
    String deptNo = (String) request.getParameter("deptNo"); 
    String isActiveYn = (String) request.getParameter("isActiveYn");
    boolean flag = adminDepartmentService.changeDepartmentActive(deptNo, isActiveYn);
%>