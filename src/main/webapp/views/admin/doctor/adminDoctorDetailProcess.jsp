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
List<DoctorCareerDTO> careerList = new ArrayList<DoctorCareerDTO>();
List<DoctorEducationDTO>educationList = new ArrayList<DoctorEducationDTO>();
List<DoctorScheduleDTO> scheduleList = new ArrayList<DoctorScheduleDTO>();
doctorDTO.setStatusCode("CLS");
if(doctorDTO.getThumbnailUrl() == null){
	doctorDTO.setThumbnailUrl("");
}
if(doctorDTO.getDetailImageUrl() == null){
	doctorDTO.setDetailImageUrl("");
}

String[] educationYear = request.getParameterValues("educationYear[]");// reqest로 받아서 String[]에 담아서 값 저장  
String[] educationContent = request.getParameterValues("educationContent[]");
String[] educationNo = request.getParameterValues("educationNo[]");
String[] careerYear = request.getParameterValues("careerYear[]");
String[] careerContent = request.getParameterValues("careerContent[]");
String[] careerNo = request.getParameterValues("careerNo[]");
String[] scheduleAmPm = request.getParameterValues("ampm[]");
String[] scheduleStartTime = request.getParameterValues("startTime[]");
String[] scheduleEndTime = request.getParameterValues("endTime[]");




if(educationYear!=null && educationYear.length>0){
	
	DoctorEducationDTO doctorEducationDTO = null;
	
	for(int i=0; i < educationYear.length; i++){
		if(!"".equals(educationYear[i].trim()) && educationYear[i]!=null){
			doctorEducationDTO = new DoctorEducationDTO();
			doctorEducationDTO.setDoctorLicenseNo(doctorDTO.getDoctorLicenseNo());
			doctorEducationDTO.setEducationYear(educationYear[i].trim());
			if(!"".equals(educationNo[i]) && educationNo!=null){
				doctorEducationDTO.setEducationNo(Integer.parseInt(educationNo[i]));
			}
			educationList.add(doctorEducationDTO);
		}// end if
	}// end for
	
	for(int i =0; i < educationList.size(); i++){
		educationList.get(i).setEducationContent(educationContent[i]);
	}//end for
	
}//end if

if(careerYear!=null && careerYear.length>0){
	
	DoctorCareerDTO doctorCareerDTO = null;
	//System.out.println(careerYear.length+" / "+careerContent.length);
	for(int i=0; i < careerYear.length; i++){
		if(!"".equals(careerYear[i].trim()) && careerYear[i]!=null){
			doctorCareerDTO = new DoctorCareerDTO();
			doctorCareerDTO.setDoctorLicenseNo(doctorDTO.getDoctorLicenseNo());
			doctorCareerDTO.setCareerYear(careerYear[i].trim());
			if(!"".equals(careerNo[i]) && careerNo!=null){
				doctorCareerDTO.setCareerNo(Integer.parseInt(careerNo[i]));
			}
			careerList.add(doctorCareerDTO);
		}// end if
	}// end for
	
	for(int i=0; i < careerContent.length; i++){
		if(!"".equals(careerYear[i].trim()) && careerYear[i]!=null){
			careerList.get(i).setCareerContent(careerContent[i]);
		}// end if
	}//end for
	
}// end if

DoctorScheduleDTO doctorScheduleDTO = null;
int  timeCnt = 0;
		
for(int i=0; i < 7; i++){
	doctorScheduleDTO = new DoctorScheduleDTO();
	doctorScheduleDTO.setDoctorLicenseNo(doctorDTO.getDoctorLicenseNo());
	doctorScheduleDTO.setDayOfWeek(i+1);
	doctorScheduleDTO.setStatus(scheduleAmPm[i]);
	
	if(!"휴진".equals(scheduleAmPm[i])){
		if(timeCnt < scheduleStartTime.length){
			doctorScheduleDTO.setStartTime(scheduleStartTime[timeCnt]);
			doctorScheduleDTO.setEndTime(scheduleEndTime[timeCnt]);
			timeCnt++;
		}
	} else {
		doctorScheduleDTO.setStartTime("");
		doctorScheduleDTO.setEndTime("");
	}
	scheduleList.add(doctorScheduleDTO);
}// end for

adminDoctorFormDTO.setDoctorDTO(doctorDTO);
adminDoctorFormDTO.setEducationList(educationList);
adminDoctorFormDTO.setCareerList(careerList);
adminDoctorFormDTO.setScheduleList(scheduleList);

//System.out.println(adminDoctorService.checkDoctorLicenseNo(doctorDTO.getDoctorLicenseNo())+": true여야하는데?");
if(adminDoctorService.checkDoctorLicenseNo(doctorDTO.getDoctorLicenseNo())){
	// update로 실행
	//System.out.println("업데이트 탐");
	adminDoctorService.modifyDoctor(adminDoctorFormDTO);
} else {	
	adminDoctorService.registerDoctor(adminDoctorFormDTO);
}

response.sendRedirect("adminDoctorListView.jsp");

%>









