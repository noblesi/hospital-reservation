<%@page import="com.hospital.admin.doctor.dto.AdminDoctorFormOptionDTO"%>
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

<c:set var="adminMenu" value="doctor" scope="request" />

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>관리자 의료진 등록/수정</title>

<!-- jQuery google API -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<style type="text/css">
#tabDeptInven {
	border: 1px solid #000;
}

#tabDeptInven>tbody>tr>td {
	text-align: left;
	border: 1px solid #333;
}

#tabDeptInven>thead>tr>th, #tabDeptInven>tbody>tr>th {
	border-bottom: 1px solid #000;
	text-align: center;
}

.doctor-warning-message {
	color: #F00;
}

.admin-view-area {
	margin: 20px;
	position: relative;
}

.doctor-register-form {
	--bg: #fff;
	--input-bg: #fff;
	--input-border: #c7d6e8;
	--text: #2f3137;
	--primary: #2763ba;
	--primary-dark: #1f5198;
	--secondary: #69707d;
	--secondary-dark: #555c67;
	--danger: #d45a5a;
	--danger-dark: #b94848;
	--scroll-track: #f5f5f7;
	--scroll-thumb: #c8cbd4;
	background: var(--bg);
	padding: 12px 14px 18px;
	font-family: 'Noto Sans KR', sans-serif;
	color: var(--text);
	box-sizing: border-box;
	overflow-x:auto;
}

.doctor-register-form * {
	box-sizing: border-box;
}

.doctor-layout {
	min-width: 0;
}

.doctor-register-title {
	margin: 0 0 18px;
	font-size: 20px;
	line-height: 1.2;
	font-weight: 800;
	letter-spacing: -0.5px;
	color: #22252b;
}

.doctor-top-area {
	width: 100%;
	display: grid;
	grid-template-columns: minmax(404px, 1fr) minmax(260px, 310px);
	column-gap: 40px;
	align-items: start;
	margin-left: 6px;
}

.doctor-field-row {
	width: 100%;
	display: grid;
	grid-template-columns: 122px minmax(156px, 1fr) 100px;
	align-items: center;
	column-gap: 12px;
	margin-bottom: 6px;
	min-height: 30px;
}

.doctor-label {
	font-size: 16px;
	font-weight: 500;
	letter-spacing: -0.25px;
	color: #2f3137;
	white-space: nowrap;
}

.doctor-textarea {
	width: 100%;
	height: 31px;
	border: 1px solid var(--input-border);
	border-radius: 6px;
	background: var(--input-bg);
	color: var(--text);
	font-size: 13px;
	font-family: 'Noto Sans KR', sans-serif;
	padding: 0 10px;
	outline: none;
	
}

.doctor-select {
	width: 220px;
	cursor: pointer;
}

.doctor-textarea {
	resize: none;
	height: auto;
	padding: 8px 10px;
	line-height: 1.45;
}

.doctor-btn {
	border: none;
	border-radius: 6px;
	height: 31px;
	min-width: 88px;
	padding: 0 12px;
	color: #fff;
	font-size: 13px;
	font-weight: 700;
	font-family: 'Noto Sans KR', sans-serif;
	cursor: pointer;
	white-space: nowrap;
	transition: .15s ease;
}

.doctor-btn-primary {
	background: var(--primary);
}

.doctor-btn-primary:hover {
	background: #333;
	transform: translateY(-1px);
}

.doctor-btn-secondary {
	background: var(--secondary);
}

.doctor-btn-secondary:hover {
	background: var(--secondary-dark);
	transform: translateY(-1px);
}

.doctor-photo-grid {
	display: grid;
	grid-template-columns: 126px 126px;
	gap: 48px;
	align-items: start;
	padding-top: 2px;
}

.doctor-photo-item {
	display: flex;
	flex-direction: column;
	align-items: center;
}

.doctor-photo-preview {
	width: 126px;
	height: 175px;
	background: #f4f4f5;
	border: 1px solid #d8d9de;
	position: relative;
	overflow: hidden;
	margin-bottom: 12px;
	background-size: cover;
	background-position: center;
	background-repeat: no-repeat;
}

.doctor-photo-preview::before {
	content: "";
	position: absolute;
	top: 59px;
	left: 50%;
	transform: translateX(-50%);
	width: 18px;
	height: 18px;
	border-radius: 50%;
	background: #d9d9dc;
	z-index: 0;
}

.doctor-photo-preview::after {
	content: "";
	position: absolute;
	top: 78px;
	left: 50%;
	transform: translateX(-50%);
	width: 0;
	height: 0;
	border-left: 26px solid transparent;
	border-right: 26px solid transparent;
	border-bottom: 36px solid #d9d9dc;
	z-index: 0;
}

.doctor-photo-text {
	position: absolute;
	inset: 0;
	display: flex;
	align-items: center;
	justify-content: center;
	font-size: 14px;
	color: #4f5259;
	z-index: 1;
	padding-top: 16px;
	text-align: center;
}
.doctor-mid-area{
	width: 100%;
}
.doctor-mid-area, .doctor-bottom-area {
	margin-top: 26px;
	margin-left: 6px;
	display: grid;
	grid-template-columns: minmax(404px, 1fr) 354px;
	column-gap: 28px;
	align-items: start;
}

.doctor-section-title {
	margin: 0 0 8px;
	font-size: 16px;
	font-weight: 700;
	letter-spacing: -0.3px;
	color: #2c2f35;
}

.doctor-scroll {
	overflow-y: auto;
	overflow-x: hidden;
	padding-right: 7px;
	scrollbar-width: auto;
	scrollbar-color: var(--scroll-thumb) var(--scroll-track);
}

.doctor-scroll::-webkit-scrollbar {
	width: 12px;
}

.doctor-scroll::-webkit-scrollbar-track {
	background: var(--scroll-track);
	border-radius: 999px;
}

.doctor-scroll::-webkit-scrollbar-thumb {
	background: var(--scroll-thumb);
	border-radius: 999px;
	border: 2px solid var(--scroll-track);
}

.doctor-schedule-scroll {
	height: 130px;
}

.doctor-history-scroll {
	height: 124px;
}

.doctor-schedule-section {
	width: 100%;
	max-width: 450px;
}

.doctor-schedule-section .doctor-scroll {
	width: 100%;
}

.doctor-education-section {
	width: 100%;
	max-width: 320px;
}

.doctor-career-section {
	width: 100%;
}

.doctor-list-title {
	margin: 0;
}

.doctor-intro-content-title {
	margin-bottom: 6px;
}

.doctor-schedule-row {
	display: grid;
	grid-template-columns: 68px 58px 36px 70px 36px 70px;
	column-gap: 6px;
	align-items: center;
	margin-bottom: 5px;
}

.doctor-mini-select {
	width: 100%;
	height: 29px;
	border: 1px solid var(--input-border);
	border-radius: 6px;
	background: var(--input-bg);
	padding: 0 8px;
	font-size: 13px;
	color: var(--text);
	font-family: 'Noto Sans KR', sans-serif;
	outline: none;
	box-shadow: inset 0 1px 1px rgba(0, 0, 0, .05);
}

.doctor-inline-label {
	font-size: 13px;
	color: #30333a;
	text-align: center;
	white-space: nowrap;
}

.doctor-list-head {
	display: flex;
	align-items: center;
	justify-content: space-between;
	margin-bottom: 6px;
}

.doctor-plus-btn, .doctor-minus-btn {
	appearance: none;
	width: 28px;
	height: 20px;
	border: none;
	border-radius: 5px;
	color: #fff !important;
	background: #2763ba !important;
	font-size: 16px;
	font-weight: 700;
	line-height: 1;
	cursor: pointer;
	padding: 0;
	margin-right: 2px;
	position: relative;
	z-index: 1;
	opacity: 1 !important;
	visibility: visible !important;
	transition: .15s ease;
}

.doctor-plus-btn,
.doctor-minus-btn {
	background: #2763ba !important;
}

.doctor-plus-btn:hover,
.doctor-plus-btn:focus,
.doctor-plus-btn:active,
.doctor-minus-btn:hover,
.doctor-minus-btn:focus,
.doctor-minus-btn:active {
	color: #fff !important;
	background: #333 !important;
	opacity: 1 !important;
	visibility: visible !important;
	transform: translateY(-1px);
}

.doctor-minus-btn {
	margin-left: 4px;
}

.doctor-list-actions {
	display: flex;
	gap: 4px;
	align-items: center;
}

.doctor-edu-row {
	display: grid;
	grid-template-columns: 72px minmax(0, 1fr);
	column-gap: 6px;
	margin-bottom: 5px;
}

.doctor-career-row {
	display: grid;
	grid-template-columns: 122px minmax(0, 1fr);
	column-gap: 6px;
	margin-bottom: 5px;
}

.doctor-intro-title {
	margin-bottom: 8px;
}

.doctor-intro-text {
	height: 112px;
}

.doctor-actions {
	width: 100%;
	display: flex;
	justify-content: center;
	gap: 12px;
	margin-top: 14px;
}

.doctor-actions .doctor-btn {
	width: 92px;
	height: 34px;
	font-size: 14px;
}
.doctor-left-form{
	width: 100%;
}

.doctor-intro-section{
	width: 100%;
}

@media ( max-width : 980px) {
	.doctor-layout {
		min-width: 0;
	}
	.doctor-top-area, .doctor-mid-area, .doctor-bottom-area {
		grid-template-columns: 1fr;
		row-gap: 24px;
	}
	.doctor-photo-grid {
		justify-content: start;
	}
}
.doctor-input {
	min-width: 0;
}

@media ( max-width : 800px) {
	.doctor-field-row {
		grid-template-columns: 96px 1fr;
	}
	.doctor-field-row>:nth-child(3) {
		grid-column: 2;
		justify-self: start;
		margin-top: 4px;
	}
	.doctor-photo-grid {
		grid-template-columns: 1fr 1fr;
		gap: 20px;
	}
	.doctor-schedule-row, .doctor-edu-row, .doctor-career-row {
		grid-template-columns: 1fr;
	}
	.doctor-inline-label {
		text-align: left;
	}
	.doctor-career-section {
		width: 100%;
	}
	[name="educationContent[]"] {
		width: 100%;
	}

}
#btnLicenseSearchTop {
	text-align: left;
}


</style>

<script type="text/javascript">
	$(function() {
		
		    // 폼 안의 input 태그에서 엔터키 입력을 감지
		    $("#doctorDetailFrm").keydown(function(e) {
		        if (e.which == 13) { // 13은 엔터키의 keyCode입니다.
		            e.preventDefault(); // 기본 submit 동작을 막음
		            return false;
		        }
		    });
		//파라미터 있을때 정보 넣어주기
	$('#thumbUploadBtn').click(function() {
			$('#thumbnailUrl').trigger('click');
		});

		$('#detailUploadBtn').click(function() {
			$('#detailImageUrl').trigger('click');
		});
		

		$('#thumbnailUrl').change(function() {
			previewImage(this, 'thumbPreview');
		});

		$('#detailImageUrl').change(function() {
			previewImage(this, 'detailPreview');
		});
		
		//파라미터값이 존재하는지 여부
		$.hasParams = function() {
		    return window.location.search.length > 1;
		};
		//education
		$('#addEducationBtn').click(function() {
			$('#educationListScroll')
					.append('<div class="doctor-edu-row">')
					.append('<input type="text" class="doctor-input" name="educationYear[]" placeholder="년도" />')
					.append('<input type="text" class="doctor-input" name="educationContent[]" placeholder="학교와 학위를 입력해주세요..." />')
					.append('<input type="hidden" name="educationNo[]" value=""/>')
					.append('</div>');
		});
		
		$('#removeEducationBtn').click(function() {
			const $divRows = $('#educationListScroll .doctor-edu-row');
			const $eduYearRows = $("[name='educationYear[]']");
			const $eduConRows = $("[name='educationContent[]']");
			if ($divRows.length > 1){
				$divRows.last().remove();
				$eduYearRows.last().remove();
				$eduConRows.last().remove();
			}
		});
		/////career
		$('#addCareerBtn').click(function() {
			$('#careerListScroll')
					.append('<div class="doctor-career-row">')
					.append('<input type="text" class="doctor-input" name="careerYear[]" placeholder="기간" />')
					.append('<input type="text" class="doctor-input" name="careerContent[]" placeholder="경력을 입력해주세요..." />')
					.append('<input type="hidden" name="careerNo[]" value=""/>')
					.append('</div>');
		});

		$('#removeCareerBtn').click(function() {
			const $divRows = $('#careerListScroll .doctor-career-row');
			const $carPerRows = $("[name='careerYear[]']");
			const $carConRows = $("[name='careerContent[]']");
			if ($divRows.length > 1){
				$divRows.last().remove();
				$carPerRows.last().remove();
				$carConRows.last().remove();
			}
		});

		$('#btnLicenseSearchTop').click(chkNull);

		$('#doctorCancelBtn').click(function() {
			history.back();
		});

		//for 과명 만큼 돌려야됨
		//$("#department").append(`<option value=''></option>`);
		
		$("#deptNo").val($("#department option:selected").val());
		
		$("#department").change(function(){
			$("#deptNo").val($("#department option:selected").val());
		});
		
		$("#btnSubmit").click(function(){
			
			$("#doctorDetailFrm")[0].submit();
		});
		
		$("[name='startTime[]']").change(function(){
			var changeSelInd = $("[name='startTime[]']").index(this);
			var changeTimevalue = $("[name='startTime[]']").eq(changeSelInd).val();
			//alert("체인지"+changeTimevalue );
			$("[name='startTimeValue[]']").eq(parseInt(changeSelInd)).val(changeTimevalue);
			//alert("체인지"+$("[name='startTimeValue[]']").eq(changeSelInd).val() +" / " +$("[name='startTimeValue[]']").length);
		});
		
		$("[name='endTime[]']").change(function(){
			var changeSelInd = $("[name='endTime[]']").index(this);
			var changeTimevalue = $("[name='endTime[]']").eq(changeSelInd).val();
			
			$("[name='endTimeValue[]']").eq(changeSelInd).val(changeTimevalue);
			
		});
		
		$("[name='ampm[]']").change(function(){
			
			var ChkVal = $(this).val();
			var changeSelInd = $("[name='ampm[]']").index(this);
			selectTimeSetting(changeSelInd);
		});
		
		selectSetting();
	}); //ready
	
	function chkNull() {
		var licenseNo = $("#doctorLicenseNo").val();
		if (licenseNo.length < 6) {
			alert('숫자 6자를 입력해주세요');
			$('#doctorLicenseNo').focus();
			return;
		}

		if (!licenseNo) {
			alert('면허 번호를 입력해주세요.');
			$('#doctorLicenseNo').focus();
			return;
		}
		
		if(licenseNo != null &&  licenseNo != ""){
			licenseNo = $.trim(licenseNo);
			var queryString ="doctorLicenseNo=" + licenseNo;
			location.href="<c:url value='/admin/doctor/form.do' />?" + queryString;
		}
		//alert('면허번호 조회: ' + licenseNo);
	}
	
	function elementHide(objIndex){
		$("[name='startTime[]']").eq(objIndex).hide();
		$("[name='endTime[]']").eq(objIndex).hide();
		$("[name='spanStartTime[]']").eq(objIndex).hide();
		$("[name='spanEndTime[]']").eq(objIndex).hide();
	}
	
	function elementShow(objIndex){
		$("[name='startTime[]']").eq(objIndex).show();
		$("[name='endTime[]']").eq(objIndex).show();
		$("[name='spanStartTime[]']").eq(objIndex).show();
		$("[name='spanEndTime[]']").eq(objIndex).show();
	}
	
	function generateTimeArray(stime, etime) {
	    let times = [];
	    
	    for (let h = stime; h < etime; h++) {
	        for (let m = 0; m < 60; m += 30) {
	            // padStart(2, '0')를 사용해 1자릿수 시간을 '09'처럼 2자릿수로 만듭니다.
	            let hh = String(h).padStart(2, '0');
	            let mm = String(m).padStart(2, '0');
	            times.push(hh + ':' + mm);
	        }
	    }
	    return times;
	}
	
	function selectTimeSetting(objIndex){
		//alert("셀렉트 셋팅!");
		var selStartNode = $("[name='startTime[]']").eq(objIndex);
		var selEndNode = $("[name='endTime[]']").eq(objIndex);
		var startIsSelected = $("[name='startTimeValue[]']").eq(objIndex).val();
		var endIsSelected = $("[name='endTimeValue[]']").eq(objIndex).val();
		var status = $("[name='ampm[]']").eq(objIndex).val();
		var startTimeIsSelectFlag = false;
		var endTimeIsSelectFlag = false;
		
		if(status == null){
			return;
		}
				
		if(status == "휴진"){
			
			elementHide(objIndex);
			selStartNode.empty();
			selEndNode.empty();
			
		} else if(status == "오전") {
			elementShow(objIndex);
			selStartNode.empty();
			selEndNode.empty();
			startTimeSet = generateTimeArray(9, 12);
			endTimeSet = generateTimeArray(10, 13);
			selStartNode.append(new Option("시간","",startTimeIsSelectFlag,startTimeIsSelectFlag));
			selEndNode.append(new Option("시간","",endTimeIsSelectFlag,endTimeIsSelectFlag));
			$.each(startTimeSet, function(i,time){
				if(time == startIsSelected) {
					startTimeIsSelectFlag = true;
				} else {
					startTimeIsSelectFlag = false;
				}
				selStartNode.append(new Option(time,time,startTimeIsSelectFlag,startTimeIsSelectFlag));
			});
			
			$.each(endTimeSet, function(i,time){
				if(time == endIsSelected) {
					endTimeIsSelectFlag = true;
				} else {
					endTimeIsSelectFlag = false;
				}
				selEndNode.append(new Option(time,time,endTimeIsSelectFlag,endTimeIsSelectFlag));
			});
		} else if(status == "오후"){
			elementShow(objIndex);
			selStartNode.empty();
			selEndNode.empty();
			startTimeSet = generateTimeArray(14, 16);
			endTimeSet = generateTimeArray(15, 17);
			selStartNode.append(new Option("시간","",startTimeIsSelectFlag,startTimeIsSelectFlag));
			selEndNode.append(new Option("시간","",endTimeIsSelectFlag,endTimeIsSelectFlag));
			$.each(startTimeSet, function(i,time){
				if(time == startIsSelected) {
					startTimeIsSelectFlag = true;
				} else {
					startTimeIsSelectFlag = false;
				}
					
				selStartNode.append(new Option(time,time,startTimeIsSelectFlag,startTimeIsSelectFlag));
			});
			
			$.each(endTimeSet, function(i,time){
				if(time == endIsSelected) {
					endTimeIsSelectFlag = true;
				} else {
					endTimeIsSelectFlag = false;
				}
				selEndNode.append(new Option(time,time,endTimeIsSelectFlag,endTimeIsSelectFlag));
			});
		} else if(status == "전일"){
			elementShow(objIndex);
			selStartNode.empty();
			selEndNode.empty();
			startTimeSet = generateTimeArray(9, 16);
			endTimeSet = generateTimeArray(10, 17);
			selStartNode.append(new Option("시간","",startTimeIsSelectFlag,startTimeIsSelectFlag));
			selEndNode.append(new Option("시간","",endTimeIsSelectFlag,endTimeIsSelectFlag));
			$.each(startTimeSet, function(i,time){
				if(time == startIsSelected) {
					startTimeIsSelectFlag = true;
				} else {
					startTimeIsSelectFlag = false;
				}
					
				selStartNode.append(new Option(time,time,startTimeIsSelectFlag,startTimeIsSelectFlag));
			});
			
			$.each(endTimeSet, function(i,time){
				if(time == endIsSelected) {
					endTimeIsSelectFlag = true;
				} else {
					endTimeIsSelectFlag = false;
				}
				selEndNode.append(new Option(time,time,endTimeIsSelectFlag,endTimeIsSelectFlag));
			});
		}
	}
	
	function selectSetting(){
		var ampmArr = $("[name='ampm[]']");
		//alert("셋팅 function"+ ampmArr.length);
		
		if(${ not empty scheduleDTOList }){
			
			for(var i = 0 ; i < ampmArr.length; i++){
				selectTimeSetting(i);
			}

		} else {
			
			for(var i = 0 ; i < ampmArr.length; i++){
				elementHide(i);
			}
			
		}// end else if
	}
	
	function previewImage(input, targetId) {
		const file = input.files && input.files[0];
		if (!file)
			return;

		const reader = new FileReader();
		reader.onload = function(e) {
			const $target = $('#' + targetId);
			$target.css('background-image', 'url(' + e.target.result + ')');
			$target.find('.doctor-photo-text').hide();
		};
		reader.readAsDataURL(file);
	}
	
</script>

<link rel="stylesheet" href="<c:url value='/resources/css/admin-layout.css?v=${initParam.assetVersion}' />">
</head>
<body>
	<jsp:include page="/views/common/adminHeader.jsp" />

	<div class="admin-layout">
		<jsp:include page="/views/common/adminSidebar.jsp" />

		<main class="admin-content">
			<div class="admin-page-title">
				<h2>의료진 등록 / 수정</h2>
			</div>

			<section class="admin-card">
				<form class="admin-search-area" action="<c:url value='/admin/doctor/form.do' />" method="post" name="doctorDetailFrm" id="doctorDetailFrm" onsubmit="return false;">
					<div class="admin-view-area">
						<div class="doctor-register-form">
							<div class="doctor-layout">
								<div class="doctor-register-title">의료진 등록 / 수정</div>
								<div class="doctor-top-area">
									<div class="doctor-left-form">
										<div class="doctor-field-row">
											<label class="doctor-label" for="doctorLicenseNo">의사 면허 번호</label>
											<c:if test="${ empty param.doctorLicenseNo  }">
												<input type="text" class="doctor-input" id="doctorLicenseNo" maxlength="6" name="doctorLicenseNo" value="" />
											</c:if>
											<c:if test="${ not empty param.doctorLicenseNo  }">
												<input type="text" class="doctor-input" id="doctorLicenseNo" maxlength="6" name="doctorLicenseNo" value="${param.doctorLicenseNo}" />
											</c:if>
											<button type="button" class="doctor-btn doctor-btn-primary" id="btnLicenseSearchTop">면허번호 조회</button>
											<c:if test="${ param.flag == 'N' }">
												<div class="form-floating">
												<span class="doctor-warning-message" id="warning">등록되지 않은 면허번호입니다.</span>
												<script type="text/javascript">
													for(var i = 0 ; i < 5; i++){
														$("#warning").fadeOut(500).fadeIn(500);
													}
													$("#warning").fadeOut(1500);
													//location.reload();
												</script>
												</div>
											</c:if>
										</div>
										<div class="doctor-field-row">
											<label class="doctor-label" for="name">이름</label> 
											<input type="text" class="doctor-input" id="name" name="name" value="${doctor.name}" />
											<div></div>
										</div>

										<div class="doctor-field-row">
											<label class="doctor-label" for="department">진료과</label> 
											<input type="hidden" id="deptNo" name="deptNo" value=""/>
											<select class="doctor-select" id="department" name="department">
												<option value="">진료과 선택</option>
												<c:forEach var="dept" items="${ departmentList }">
													<c:if test="${ doctor.deptNo eq dept.deptNo }">
														<option value="${ dept.deptNo }" selected="selected"><c:out value="${ dept.deptName }"/></option>
													</c:if>
													<c:if test="${ doctor.deptNo ne dept.deptNo }">
														<option value="${ dept.deptNo }"><c:out value="${ dept.deptName }"/></option>
													</c:if>
												</c:forEach>
												
											</select>
											<div></div>
										</div>


										<div class="doctor-field-row">
											<label class="doctor-label" for="specialty">전문분야</label> 
											<input 	type="text" class="doctor-input" id="specialty" name="specialty" value="${doctor.specialty}" />
											<div></div>
										</div>

										<div class="doctor-field-row">
											<label class="doctor-label" for="phoneNum">연락처</label> 
											<input type="text" class="doctor-input" id="phoneNum" name="phoneNum" value="${doctor.phoneNum}" />
											<div></div>
										</div>
									</div>

									<div class="doctor-photo-grid">
										<div class="doctor-photo-item">
											<div class="doctor-photo-preview" id="thumbPreview">
												<div class="doctor-photo-text">썸네일 사진</div>
											</div>
											<button type="button" class="doctor-btn doctor-btn-primary" id="thumbUploadBtn">썸네일 사진 등록</button>
											<input type="file" id="thumbnailUrl" name="thumbnailUrl" accept="image/*" hidden />
										</div>

										<div class="doctor-photo-item">
											<div class="doctor-photo-preview" id="detailPreview">
												<div class="doctor-photo-text">상세 사진</div>
											</div>
											<button type="button" class="doctor-btn doctor-btn-primary" id="detailUploadBtn">상세 사진 등록</button>
											<input type="file" id="detailImageUrl" name="detailImageUrl" accept="image/*" hidden />
										</div>
									</div>
								</div>

								<div class="doctor-mid-area">
									<div class="doctor-schedule-section">
										<div class="doctor-section-title">진료 가능 요일</div>
										<div class="doctor-scroll doctor-schedule-scroll" id="scheduleList">
											<c:if test="${not empty scheduleDTOList }">
											<c:forEach var="schedule" items="${ scheduleDTOList }">
												<div class="doctor-schedule-row">
													<c:choose>
														<c:when test="${ schedule.dayOfWeek == 1 }">
															<input type="hidden" name="startTimeValue[]" value="${ schedule.startTime }"/>
															<input type="hidden" name="endTimeValue[]" value="${ schedule.endTime }"/>
															<span class="doctor-inline-label">월</span>
														</c:when>
														<c:when test="${ schedule.dayOfWeek == 2 }">
															<input type="hidden" name="startTimeValue[]" value="${ schedule.startTime }"/>
															<input type="hidden" name="endTimeValue[]" value="${ schedule.endTime }"/>
															<span class="doctor-inline-label">화</span>
														</c:when>
														<c:when test="${ schedule.dayOfWeek == 3 }">
															<input type="hidden" name="startTimeValue[]" value="${ schedule.startTime }"/>
															<input type="hidden" name="endTimeValue[]" value="${ schedule.endTime }"/>
															<span class="doctor-inline-label">수</span>
														</c:when>
														<c:when test="${ schedule.dayOfWeek == 4 }">
															<input type="hidden" name="startTimeValue[]" value="${ schedule.startTime }"/>
															<input type="hidden" name="endTimeValue[]" value="${ schedule.endTime }"/>
															<span class="doctor-inline-label">목</span>
														</c:when>
														<c:when test="${ schedule.dayOfWeek == 5 }">
															<input type="hidden" name="startTimeValue[]" value="${ schedule.startTime }"/>
															<input type="hidden" name="endTimeValue[]" value="${ schedule.endTime }"/>
															<span class="doctor-inline-label">금</span>
														</c:when>
														<c:when test="${ schedule.dayOfWeek == 6 }">
															<input type="hidden" name="startTimeValue[]" value="${ schedule.startTime }"/>
															<input type="hidden" name="endTimeValue[]" value="${ schedule.endTime }"/>
															<span class="doctor-inline-label">토</span>
														</c:when>
														<c:when test="${ schedule.dayOfWeek == 7 }">
															<input type="hidden" name="startTimeValue[]" value="${ schedule.startTime }"/>
															<input type="hidden" name="endTimeValue[]" value="${ schedule.endTime }"/>
															<span class="doctor-inline-label">일</span>
														</c:when>
													</c:choose>
													<select class="doctor-mini-select" name="ampm[]">
														<c:choose>
															<c:when test="${ schedule.status eq '오전' }">
																<option value='오전' selected='selected'>오전</option>
																<option value='오후'>오후</option>
																<option value='전일'>전일</option>
																<option value='휴진'>휴진</option>
															</c:when>
															<c:when test="${ schedule.status eq '오후' }">
																<option value='오전'>오전</option>
																<option value='오후' selected='selected'>오후</option>
																<option value='전일'>전일</option>
																<option value='휴진'>휴진</option>
															</c:when>
															<c:when test="${ schedule.status eq '전일' }">
																<option value='오전'>오전</option>
																<option value='오후'>오후</option>
																<option value='전일' selected='selected'>전일</option>
																<option value='휴진'>휴진</option>
															</c:when>
															<c:when test="${ schedule.status eq '휴진' }">
																<option value='오전'>오전</option>
																<option value='오후'>오후</option>
																<option value='전일'>전일</option>
																<option value='휴진' selected='selected'>휴진</option>
															</c:when>
														</c:choose>
													</select> 
													<span class="doctor-inline-label" name="spanStartTime[]">시작 :</span>
													<select class="doctor-mini-select" name="startTime[]">
													</select> 
													<span class="doctor-inline-label" name="spanEndTime[]">종료 :</span> 
													<select class="doctor-mini-select" name="endTime[]">
													</select>
												</div>
											</c:forEach>
											</c:if>
											<c:if test="${ empty scheduleDTOList }">
														<div class="doctor-schedule-row">
															<span class="doctor-inline-label">월</span>
															<input type="hidden" name="startTimeValue[]" value=""/>
															<input type="hidden" name="endTimeValue[]" value=""/>
															<select class="doctor-mini-select" name="ampm[]">
																<option value='오전'>오전</option>
																<option value='오후'>오후</option>
																<option value='전일'>전일</option>
																<option value='휴진' selected='selected'>휴진</option>
															</select>
															<span class="doctor-inline-label" name="spanStartTime[]">시작 :</span>
															<select class="doctor-mini-select" name="startTime[]">
															</select> 
															<span class="doctor-inline-label" name="spanEndTime[]">종료 :</span> 
															<select class="doctor-mini-select" name="endTime[]">
															</select>
														</div>
														
														<div class="doctor-schedule-row">
															<span class="doctor-inline-label">화</span>
															<input type="hidden" name="startTimeValue[]" value=""/>
															<input type="hidden" name="endTimeValue[]" value=""/>
															<select class="doctor-mini-select" name="ampm[]">
																<option value='오전'>오전</option>
																<option value='오후'>오후</option>
																<option value='전일'>전일</option>
																<option value='휴진' selected='selected'>휴진</option>
															</select>
															<span class="doctor-inline-label" name="spanStartTime[]">시작 :</span>
															<select class="doctor-mini-select" name="startTime[]">
															</select> 
															<span class="doctor-inline-label" name="spanEndTime[]">종료 :</span> 
															<select class="doctor-mini-select" name="endTime[]">
															</select>
														</div>
														<div class="doctor-schedule-row">
															<span class="doctor-inline-label">수</span>
															<input type="hidden" name="startTimeValue[]" value=""/>
															<input type="hidden" name="endTimeValue[]" value=""/>
															<select class="doctor-mini-select" name="ampm[]">
																<option value='오전'>오전</option>
																<option value='오후'>오후</option>
																<option value='전일'>전일</option>
																<option value='휴진' selected='selected'>휴진</option>
															</select>
															<span class="doctor-inline-label" name="spanStartTime[]">시작 :</span>
															<select class="doctor-mini-select" name="startTime[]">
															</select> 
															<span class="doctor-inline-label" name="spanEndTime[]">종료 :</span> 
															<select class="doctor-mini-select" name="endTime[]">
															</select>
														</div>	
														<div class="doctor-schedule-row">
															<span class="doctor-inline-label">목</span>
															<input type="hidden" name="startTimeValue[]" value=""/>
															<input type="hidden" name="endTimeValue[]" value=""/>
															<select class="doctor-mini-select" name="ampm[]">
																<option value='오전'>오전</option>
																<option value='오후'>오후</option>
																<option value='전일'>전일</option>
																<option value='휴진' selected='selected'>휴진</option>
															</select>
															<span class="doctor-inline-label" name="spanStartTime[]">시작 :</span>
															<select class="doctor-mini-select" name="startTime[]">
															</select> 
															<span class="doctor-inline-label" name="spanEndTime[]">종료 :</span> 
															<select class="doctor-mini-select" name="endTime[]">
															</select>
															
														</div>
														<div class="doctor-schedule-row">
															<span class="doctor-inline-label">금</span>
															<input type="hidden" name="startTimeValue[]" value=""/>
															<input type="hidden" name="endTimeValue[]" value=""/>
															<select class="doctor-mini-select" name="ampm[]">
																<option value='오전'>오전</option>
																<option value='오후'>오후</option>
																<option value='전일'>전일</option>
																<option value='휴진' selected='selected'>휴진</option>
															</select>
															<span class="doctor-inline-label" name="spanStartTime[]">시작 :</span>
															<select class="doctor-mini-select" name="startTime[]">
															</select> 
															<span class="doctor-inline-label" name="spanEndTime[]">종료 :</span> 
															<select class="doctor-mini-select" name="endTime[]">
															</select>
														</div>
														<div class="doctor-schedule-row">
															<span class="doctor-inline-label">토</span>
															<input type="hidden" name="startTimeValue[]" value=""/>
															<input type="hidden" name="endTimeValue[]" value=""/>
															<select class="doctor-mini-select" name="ampm[]">
																<option value='오전'>오전</option>
																<option value='오후'>오후</option>
																<option value='전일'>전일</option>
																<option value='휴진' selected='selected'>휴진</option>
															</select>
															<span class="doctor-inline-label" name="spanStartTime[]">시작 :</span>
															<select class="doctor-mini-select" name="startTime[]">
															</select> 
															<span class="doctor-inline-label" name="spanEndTime[]">종료 :</span> 
															<select class="doctor-mini-select" name="endTime[]">
															</select>
														</div>
														<div class="doctor-schedule-row">
															<span class="doctor-inline-label">일</span>
															<input type="hidden" name="startTimeValue[]" value=""/>
															<input type="hidden" name="endTimeValue[]" value=""/>
															<select class="doctor-mini-select" name="ampm[]">
																<option value='오전'>오전</option>
																<option value='오후'>오후</option>
																<option value='전일'>전일</option>
																<option value='휴진' selected='selected'>휴진</option>
															</select>
															<span class="doctor-inline-label" name="spanStartTime[]">시작 :</span>
															<select class="doctor-mini-select" name="startTime[]">
															</select> 
															<span class="doctor-inline-label" name="spanEndTime[]">종료 :</span> 
															<select class="doctor-mini-select" name="endTime[]">
															</select>
														</div>
												</c:if>
											</div>
										</div>

										<div class="doctor-education-section">
											<div class="doctor-list-head" >
												<div class="doctor-section-title doctor-list-title">학력</div>
												<div class="doctor-list-actions">
													<button type="button" class="doctor-plus-btn" id="addEducationBtn">+</button>
													<button type="button" class="doctor-minus-btn" id="removeEducationBtn">-</button>
												</div>
											</div>

											<div class="doctor-scroll doctor-history-scroll" id="educationListScroll">
												<c:forEach var="education" items="${ educationList }">
													<div class="doctor-edu-row">
														<input type="text" class="doctor-input" name="educationYear[]" placeholder="년도" value="${ education.educationYear }" /> 
														<input type="text" class="doctor-input" name="educationContent[]" placeholder="학교와 학위를 입력해주세요..." value="${ education.educationContent }" />
														<input type="hidden" name="educationNo[]" value="${ education.educationNo }"/>
													</div>
												</c:forEach>
												<div class="doctor-edu-row">
													<input type="text" class="doctor-input" name="educationYear[]" placeholder="년도" /> 
													<input type="text" class="doctor-input" name="educationContent[]" placeholder="학교와 학위를 입력해주세요..." />
													<input type="hidden" name="educationNo[]" value=""/>
												</div>
											</div>
										</div>
									</div>

										<div class="doctor-bottom-area">
											<div class="doctor-career-section">
												<div class="doctor-list-head">
													<div class="doctor-section-title doctor-list-title">경력</div>
													<div class="doctor-list-actions">
														<button type="button" class="doctor-plus-btn" id="addCareerBtn">+</button>
														<button type="button" class="doctor-minus-btn" id="removeCareerBtn">-</button>
													</div>
												</div>

												<div class="doctor-scroll doctor-history-scroll" id="careerListScroll">
													<c:forEach var="career" items="${ careerList }">
														<div class="doctor-career-row">
															<input type="text" class="doctor-input" name="careerYear[]" placeholder="기간" value="${ career.careerYear }" /> 
															<input type="text" class="doctor-input" name="careerContent[]" placeholder="경력을 입력해주세요..." value="${ career.careerContent }" />
															<input type="hidden" name="careerNo[]" value="${ career.careerNo }"/>
														</div>
													</c:forEach>
													<div class="doctor-career-row">
														<input type="text" class="doctor-input"  name="careerYear[]" placeholder="기간" /> 
														<input type="text" class="doctor-input" name="careerContent[]" placeholder="경력을 입력해주세요..." />
														<input type="hidden" name="careerNo[]" value=""/>
													</div>
												</div>
											</div>
										</div>
										<div class="doctor-intro-section">
											<div class="doctor-section-title">소개 제목</div>
											<input type="text" class="doctor-input doctor-intro-title" id="introTitle" name="introTitle" value="${doctor.introTitle}" />

											<div class="doctor-section-title doctor-intro-content-title">소개글</div>
											<textarea class="doctor-textarea doctor-intro-text" id="introContent" name="introContent">${doctor.introContent}</textarea>
										</div>


										<div class="doctor-actions">
											<button type="button" id="btnSubmit" name="btnSubmit" class="doctor-btn doctor-btn-primary">저장</button>
											<button type="button" class="doctor-btn doctor-btn-secondary" id="doctorCancelBtn">취소</button>
										</div>
									
								</div>
							</div>
						</div>
				</form>
			</section>
		</main>
	</div>
	<script src="<c:url value='/resources/js/admin-layout.js?v=${initParam.assetVersion}' />"></script>
</body>
</html>
