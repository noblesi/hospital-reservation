<%@page import="com.hospital.common.dto.DepartmentDTO"%>
<%@page import="com.hospital.admin.department.AdminDepartmentService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<jsp:useBean id="departmentDTO" class="com.hospital.common.dto.DepartmentDTO" scope="request"/>
<jsp:setProperty property="*" name="departmentDTO"/>
<%
request.setCharacterEncoding("UTF-8");

AdminDepartmentService adminDepartmentService = new AdminDepartmentService();
//og(departmentDTO.toString()); 
if(departmentDTO != null){
	log(departmentDTO.toString());
	adminDepartmentService.modifyDepartment(departmentDTO);
}// end if

%>
<script type="text/javascript">
	//alert();
</script>