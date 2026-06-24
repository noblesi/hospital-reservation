<%@page import="com.hospital.common.dto.DepartmentDTO"%>
<%@page import="com.hospital.admin.department.AdminDepartmentService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<jsp:useBean id="departmentDTO" class="com.hospital.common.dto.DepartmentDTO" scope="request"/>
<jsp:setProperty property="*" name="departmentDTO"/>

<script type="text/javascript">

$(function(){
	<%
	request.setCharacterEncoding("UTF-8");
	AdminDepartmentService adminDepartmentService = new AdminDepartmentService();
	if(departmentDTO != null){
		log(departmentDTO.toString());
		
		if(departmentDTO.getDeptNo()==null){
			adminDepartmentService.registerDepartment(departmentDTO);
		}else{
			adminDepartmentService.modifyDepartment(departmentDTO);
		}// end if
	}// end if
	%>
	window.opener.location.reload();
	self.close();
});

</script>