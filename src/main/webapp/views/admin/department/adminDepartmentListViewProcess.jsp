<%@page import="com.hospital.admin.department.AdminDepartmentService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%
	request.setCharacterEncoding("UTF-8");
	AdminDepartmentService adminDepartmentService = new AdminDepartmentService();
    
	// 자바스크립트가 보낸 chkVal 값이 여기에 들어옵니다.
    String deptNo = request.getParameter("deptNo"); 
    String isActiveYn = request.getParameter("isActiveYn");
	
    isActiveYn = (isActiveYn=="Y" ? "N" : "Y");
    log(deptNo+" / "+isActiveYn);
    // 서비스 호출
    boolean flag = adminDepartmentService.changeDepartmentActive(deptNo, isActiveYn);
    log("왔슈"+ flag +"===========================");
%>