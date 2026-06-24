<%@page import="com.hospital.admin.department.AdminDepartmentService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%
	request.setCharacterEncoding("UTF-8");
	AdminDepartmentService adminDepartmentService = new AdminDepartmentService();
    
    String deptNo = (String) request.getParameter("deptNo"); 
    String isActiveYn = (String) request.getParameter("isActiveYn");
   //log("왔슈  "+ isActiveYn +"===========================");
    //isActiveYn = (isActiveYn=="Y" ? "N" : "Y");
    //log(deptNo+" / "+isActiveYn);
    // 서비스 호출
    boolean flag = adminDepartmentService.changeDepartmentActive(deptNo, isActiveYn);
    log("끝나고"+ flag +"===========================");
%>