<%@page import="com.hospital.common.dto.DepartmentDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="adminMenu" value="doctor" scope="request" />

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>관리자 의료진 등록/수정</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.min.js" integrity="sha384-G/EV+4j2dNv+tEPo3++6LCgdCROaejBqfUeNjuKAiuXbjrxilcCdDz6ZAVfHWe1Y" crossorigin="anonymous"></script>

<!-- jQuery google API -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<style type="text/css">
    #tabDeptInven {
        border: 1px solid #000;
    }

    #tabDeptInven > tbody > tr > td {
        text-align: left;
        border: 1px solid #333;
    }

    #tabDeptInven > thead > tr > th, #tabDeptInven > tbody > tr > th {
        border-bottom: 1px solid #000;
        text-align: center;
    }

    .admin-view-area {
        margin: 20px;
        position: relative;
    }
 
</style>

<style>
    @import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;500;700;800&display=swap');

    .doctor-register-form{
      --bg:#d6d8de;
      --input-bg:#f7f7f8;
      --input-border:#babdc5;
      --text:#2f3137;
      --primary:#6a70f2;
      --primary-dark:#5961e9;
      --secondary:#9a9eab;
      --secondary-dark:#868b98;
      --danger:#e06b6b;
      --danger-dark:#d45a5a;
      --scroll-track:#f5f5f7;
      --scroll-thumb:#c8cbd4;

      width:100%;
      max-width:100%;
      background:var(--bg);
      padding:12px 14px 18px;
      font-family:'Noto Sans KR', sans-serif;
      color:var(--text);
      border:1px solid rgba(0,0,0,.03);
      box-sizing:border-box;
      overflow-x:auto;
    }

    .doctor-register-form *{ box-sizing:border-box; }

    .doctor-layout{
      min-width:860px;
    }

    .doctor-register-title{
      margin:0 0 18px;
      font-size:20px;
      line-height:1.2;
      font-weight:800;
      letter-spacing:-0.5px;
      color:#22252b;
    }

    .doctor-top-area{
      display:grid;
      grid-template-columns: minmax(404px, 1fr) 310px;
      column-gap:40px;
      align-items:start;
      margin-left:6px;
    }

    .doctor-field-row{
      display:grid;
      grid-template-columns: 122px minmax(156px, 1fr) 100px;
      align-items:center;
      column-gap:12px;
      margin-bottom:6px;
      min-height:30px;
    }

    .doctor-label{
      font-size:16px;
      font-weight:500;
      letter-spacing:-0.25px;
      color:#2f3137;
      white-space:nowrap;
    }

    .doctor-input,
    .doctor-select,
    .doctor-textarea{
      width:100%;
      height:31px;
      border:1px solid var(--input-border);
      border-radius:6px;
      background:var(--input-bg);
      color:var(--text);
      font-size:13px;
      font-family:'Noto Sans KR', sans-serif;
      padding:0 10px;
      outline:none;
      box-shadow:inset 0 1px 1px rgba(0,0,0,.05);
    }

    .doctor-select{
      appearance:auto;
      cursor:pointer;
    }

    .doctor-textarea{
      resize:none;
      height:auto;
      padding:8px 10px;
      line-height:1.45;
    }

    .doctor-btn{
      border:none;
      border-radius:6px;
      height:31px;
      min-width:88px;
      padding:0 12px;
      color:#fff;
      font-size:13px;
      font-weight:700;
      font-family:'Noto Sans KR', sans-serif;
      cursor:pointer;
      white-space:nowrap;
      transition:.15s ease;
    }

    .doctor-btn-primary{ background:var(--primary); }
    .doctor-btn-primary:hover{ background:var(--primary-dark); transform:translateY(-1px); }
    .doctor-btn-secondary{ background:var(--secondary); }
    .doctor-btn-secondary:hover{ background:var(--secondary-dark); transform:translateY(-1px); }

    .doctor-photo-grid{
      display:grid;
      grid-template-columns: 126px 126px;
      gap:48px;
      align-items:start;
      padding-top:2px;
    }

    .doctor-photo-item{
      display:flex;
      flex-direction:column;
      align-items:center;
    }

    .doctor-photo-preview{
      width:126px;
      height:175px;
      background:#f4f4f5;
      border:1px solid #d8d9de;
      position:relative;
      overflow:hidden;
      margin-bottom:12px;
      background-size:cover;
      background-position:center;
      background-repeat:no-repeat;
    }

    .doctor-photo-preview::before{
      content:"";
      position:absolute;
      top:59px;
      left:50%;
      transform:translateX(-50%);
      width:18px;
      height:18px;
      border-radius:50%;
      background:#d9d9dc;
      z-index:0;
    }

    .doctor-photo-preview::after{
      content:"";
      position:absolute;
      top:78px;
      left:50%;
      transform:translateX(-50%);
      width:0;
      height:0;
      border-left:26px solid transparent;
      border-right:26px solid transparent;
      border-bottom:36px solid #d9d9dc;
      z-index:0;
    }

    .doctor-photo-text{
      position:absolute;
      inset:0;
      display:flex;
      align-items:center;
      justify-content:center;
      font-size:14px;
      color:#4f5259;
      z-index:1;
      padding-top:16px;
      text-align:center;
    }

    .doctor-mid-area,
    .doctor-bottom-area{
      margin-top:26px;
      margin-left:6px;
      display:grid;
      grid-template-columns: minmax(404px, 1fr) 354px;
      column-gap:28px;
      align-items:start;
    }

    .doctor-section-title{
      margin:0 0 8px;
      font-size:16px;
      font-weight:700;
      letter-spacing:-0.3px;
      color:#2c2f35;
    }

    .doctor-scroll{
      overflow-y:auto;
      overflow-x:hidden;
      padding-right:7px;
      scrollbar-width:auto;
      scrollbar-color: var(--scroll-thumb) var(--scroll-track);
    }

    .doctor-scroll::-webkit-scrollbar{ width:12px; }
    .doctor-scroll::-webkit-scrollbar-track{
      background:var(--scroll-track);
      border-radius:999px;
    }
    .doctor-scroll::-webkit-scrollbar-thumb{
      background:var(--scroll-thumb);
      border-radius:999px;
      border:2px solid var(--scroll-track);
    }

    .doctor-schedule-scroll{ height:130px; }
    .doctor-history-scroll{ height:124px; }

    .doctor-schedule-row{
      display:grid;
      grid-template-columns: 68px 58px 36px 70px 36px 70px;
      column-gap:6px;
      align-items:center;
      margin-bottom:5px;
    }

    .doctor-mini-select{
      width:100%;
      height:29px;
      border:1px solid var(--input-border);
      border-radius:6px;
      background:var(--input-bg);
      padding:0 8px;
      font-size:13px;
      color:var(--text);
      font-family:'Noto Sans KR', sans-serif;
      outline:none;
      box-shadow:inset 0 1px 1px rgba(0,0,0,.05);
    }

    .doctor-inline-label{
      font-size:13px;
      color:#30333a;
      text-align:center;
      white-space:nowrap;
    }

    .doctor-list-head{
      display:flex;
      align-items:center;
      justify-content:space-between;
      margin-bottom:6px;
    }

    .doctor-plus-btn,
    .doctor-minus-btn{
      width:28px;
      height:20px;
      border:none;
      border-radius:5px;
      color:#fff;
      font-size:16px;
      font-weight:700;
      line-height:1;
      cursor:pointer;
      padding:0;
      margin-right:2px;
    }

    .doctor-plus-btn{ background:var(--primary); }
    .doctor-plus-btn:hover{ background:var(--primary-dark); }

    .doctor-minus-btn{
      background:var(--danger);
      margin-left:4px;
    }
    .doctor-minus-btn:hover{ background:var(--danger-dark); }

    .doctor-list-actions{
      display:flex;
      gap:4px;
      align-items:center;
    }

    .doctor-edu-row{
      display:grid;
      grid-template-columns: 50px 1fr;
      column-gap:6px;
      margin-bottom:5px;
    }

    .doctor-career-row{
      display:grid;
      grid-template-columns: 122px 1fr;
      column-gap:6px;
      margin-bottom:5px;
    }

    .doctor-intro-title{ margin-bottom:8px; }
    .doctor-intro-text{ height:112px; }

    .doctor-actions{
      display:flex;
      justify-content:center;
      gap:12px;
      margin-top:14px;
    }

    .doctor-actions .doctor-btn{
      width:92px;
      height:34px;
      font-size:14px;
    }

    @media (max-width: 980px){
      .doctor-layout{
        min-width:0;
      }

      .doctor-top-area,
      .doctor-mid-area,
      .doctor-bottom-area{
        grid-template-columns:1fr;
        row-gap:24px;
      }

      .doctor-photo-grid{
        justify-content:start;
      }
    }

    @media (max-width: 640px){
      .doctor-field-row{
        grid-template-columns: 96px 1fr;
      }

      .doctor-field-row > :nth-child(3){
        grid-column:2;
        justify-self:start;
        margin-top:4px;
      }

      .doctor-photo-grid{
        grid-template-columns:1fr 1fr;
        gap:20px;
      }

      .doctor-schedule-row,
      .doctor-edu-row,
      .doctor-career-row{
        grid-template-columns:1fr;
      }

      .doctor-inline-label{ text-align:left; }
    }
  </style>

<script type="text/javascript">
    $(function(){
		
		$('#thumbUploadBtn').click( function () {
		  $('#thumbFile').trigger('click');
		});
		
		$('#detailUploadBtn').click( function () {
		  $('#detailFile').trigger('click');
		});
		
		function previewImage(input, targetId){
		  const file = input.files && input.files[0];
		  if(!file) return;
		
		  const reader = new FileReader();
		  reader.onload = function(e){
		    const $target = $('#' + targetId);
		    $target.css('background-image', 'url(' + e.target.result + ')');
		    $target.find('.doctor-photo-text').hide();
		  };
		  reader.readAsDataURL(file);
		}
		
		$('#thumbFile').change( function () {
		  previewImage(this, 'thumbPreview');
		});
		
		$('#detailFile').change( function () {
		  previewImage(this, 'detailPreview');
		});
		
		$('#addEducationBtn').click( function () {
		  $('#educationList').append(
		    '<div class="doctor-edu-row">' +
		      '<input type="text" class="doctor-input" name="educationYear[]" placeholder="년도" />' +
		      '<input type="text" class="doctor-input" name="educationContent[]" placeholder="학교와 학위를 입력해주세요..." />' +
		    '</div>'
		  );
		});
		
		$('#removeEducationBtn').click( function () {
		  const $rows = $('#educationList .doctor-edu-row');
		  if ($rows.length > 1) $rows.last().remove();
		});
		
		$('#addCareerBtn').click( function () {
		  $('#careerList').append(
		    '<div class="doctor-career-row">' +
		      '<input type="text" class="doctor-input" name="careerPeriod[]" placeholder="기간" />' +
		      '<input type="text" class="doctor-input" name="careerContent[]" placeholder="경력을 입력해주세요..." />' +
		    '</div>'
		  );
		});
		
		$('#removeCareerBtn').click( function () {
		  const $rows = $('#careerList .doctor-career-row');
		  if ($rows.length > 1) $rows.last().remove();
		});
		
		$('#btnLicenseSearchTop').click( function () {
		  var licenseNo = $.trim($('#licenseNo').val());
		  if(licenseNo.length < 6) {
		  	alert('숫자 6자를 입력해주세요');
		    	$('#licenseNo').focus();
		    	return;
		  }
		  
		  if (!licenseNo) {
		    alert('면허 번호를 입력해주세요.');
		    $('#licenseNo').focus();
		    return;
		  }
		  alert('면허번호 조회: ' + licenseNo);
		});
		
		$('#doctorCancelBtn').click( function () {
		  history.back();
		});
    	
		//for 과명 만큼 돌려야됨
		$("#department")
			.append(`<option value=''></option>`);
		
    	$("[name='ampm[]']")
    		.append(`<option value='am'>오전</option>`)
    		.append(`<option value='pm'>오후</option>`);
		
		$("[name='startTime[]']")
			.append(`<option value='09:00'>09:00</option>`)
			.append(`<option value='09:30'>09:30</option>`)
			.append(`<option value='10:00'>10:00</option>`)
			.append(`<option value='10:30'>10:30</option>`)
			.append(`<option value='11:00'>11:00</option>`)
			.append(`<option value='11:30'>11:30</option>`)
			.append(`<option value='12:00'>12:00</option>`)
			.append(`<option value='12:30'>12:30</option>`)
			.append(`<option value='13:00'>13:00</option>`);
		
		$("[name='endTime[]']")
			.append(`<option value='14:00'>14:00</option>`)
			.append(`<option value='14:00'>14:30</option>`)
			.append(`<option value='14:00'>15:00</option>`)
			.append(`<option value='14:00'>15:30</option>`)
			.append(`<option value='14:00'>16:00</option>`)
			.append(`<option value='14:00'>16:30</option>`)
			.append(`<option value='14:00'>17:00</option>`);

    }); //ready
</script>

<link rel="stylesheet" href="<c:url value='/resources/css/admin-layout.css' />">
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
            <form class="admin-search-area">
                <div class="admin-view-area">

					<div class="doctor-register-form">
					  <div class="doctor-layout">
					    <div class="doctor-register-title">의료진 등록 / 수정</div>
						    <div class="doctor-top-area">
						      <div class="doctor-left-form">
						        <div class="doctor-field-row">
						          <label class="doctor-label" for="licenseNo">의사 면허 번호</label>
						          <input type="text" class="doctor-input" id="licenseNo" maxlength="6" name="licenseNo" value="${doctor.licenseNo}" />
						          <button type="button" class="doctor-btn doctor-btn-primary" id="btnLicenseSearchTop">면허번호 조회</button>
						        </div>
						
						        <div class="doctor-field-row">
						          <label class="doctor-label" for="doctorName">이름</label>
						          <input type="text" class="doctor-input" id="doctorName" name="doctorName" value="${doctor.name}" />
						          <div></div>
						        </div>
						
						        <div class="doctor-field-row">
						          <label class="doctor-label" for="department">진료과</label>
						          <select class="doctor-select" id="department" name="department">
						            <option value="">진료과 선택</option>
						            <option value="내과">내과</option>
						            <option value="외과">외과</option>
						            <option value="정형외과">정형외과</option>
						            <option value="신경과">신경과</option>
						            <option value="소아청소년과">소아청소년과</option>
						          </select>
						          <div></div>
						        </div>
						
						        <div class="doctor-field-row">
						          <label class="doctor-label" for="position">직급</label>
						          <select class="doctor-select" id="position" name="position">
						            <option value="">직급 선택</option>
						            <option value="교수">교수</option>
						            <option value="과장">과장</option>
						            <option value="전문의">전문의</option>
						            <option value="전임의">전임의</option>
						            <option value="레지던트">레지던트</option>
						          </select>
						          <div></div>
						        </div>
						
						        <div class="doctor-field-row">
						          <label class="doctor-label" for="specialty">전문분야</label>
						          <input type="text" class="doctor-input" id="specialty" name="specialty" value="${doctor.specialty}" />
						          <div></div>
						        </div>
						
						        <div class="doctor-field-row">
						          <label class="doctor-label" for="phone">연락처</label>
						          <input type="text" class="doctor-input" id="phone" name="phone" value="${doctor.phone}" />
						          <div></div>
						        </div>
						      </div>
						
						      <div class="doctor-photo-grid">
						        <div class="doctor-photo-item">
						          <div class="doctor-photo-preview" id="thumbPreview">
						            <div class="doctor-photo-text">썸네일 사진</div>
						          </div>
						          <button type="button" class="doctor-btn doctor-btn-primary" id="thumbUploadBtn">썸네일 사진 등록</button>
						          <input type="file" id="thumbFile" name="thumbFile" accept="image/*" hidden />
						        </div>
						
						        <div class="doctor-photo-item">
						          <div class="doctor-photo-preview" id="detailPreview">
						            <div class="doctor-photo-text">상세 사진</div>
						          </div>
						          <button type="button" class="doctor-btn doctor-btn-primary" id="detailUploadBtn">상세 사진 등록</button>
						          <input type="file" id="detailFile" name="detailFile" accept="image/*" hidden />
						        </div>
						      </div>
						    </div>
					
					    <div class="doctor-mid-area">
					      <div class="doctor-schedule-section">
					        <div class="doctor-section-title">진료 가능 요일</div>
					        <div class="doctor-scroll doctor-schedule-scroll" id="scheduleList">
					          <div class="doctor-schedule-row">
					            <span class="doctor-inline-label">월</span>
					            <select class="doctor-mini-select" name="ampm[]">
					              <option selected>오전</option>
					              <option>오후</option>
					            </select>
					            <span class="doctor-inline-label">시작 :</span>
					            <select class="doctor-mini-select" name="startTime[]">
					              <option>09:00</option>
					              <option>09:30</option>
					              <option>10:00</option>
					              <option>10:30</option>
					              <option>11:00</option>
					              <option>11:30</option>
					              <option>12:00</option>
					              <option>12:30</option>
					              <option>13:00</option>
					            </select>
					            <span class="doctor-inline-label">종료 :</span>
					            <select class="doctor-mini-select" name="endTime[]">
					              <option>14:00</option>
					              <option>14:30</option>
					              <option>15:00</option>
					              <option>15:30</option>
					              <option>16:00</option>
					              <option>16:30</option>
					              <option>17:00</option>
					            </select>
					          </div>
					
					          <div class="doctor-schedule-row">
					            <span class="doctor-inline-label">화</span>
					            <select class="doctor-mini-select" name="ampm[]">
					              <option>오전</option>
					              <option selected>오후</option>
					            </select>
					            <span class="doctor-inline-label">시작 :</span>
					            <select class="doctor-mini-select" name="startTime[]">
					              <option>09:00</option>
					              <option>09:30</option>
					              <option>10:00</option>
					              <option>10:30</option>
					              <option>11:00</option>
					              <option>11:30</option>
					              <option>12:00</option>
					              <option>12:30</option>
					              <option>13:00</option>
					            </select>
					            <span class="doctor-inline-label">종료 :</span>
					            <select class="doctor-mini-select" name="endTime[]">
					              <option>14:00</option>
					              <option>14:30</option>
					              <option>15:00</option>
					              <option>15:30</option>
					              <option>16:00</option>
					              <option>16:30</option>
					              <option>17:00</option>
					            </select>
					          </div>
					
					          <div class="doctor-schedule-row">
					            <span class="doctor-inline-label">수</span>
					            <select class="doctor-mini-select" name="ampm[]">
					              <option selected>오전</option>
					              <option>오후</option>
					            </select>
					            <span class="doctor-inline-label">시작 :</span>
					            <select class="doctor-mini-select" name="startTime[]">
					              <option>09:00</option>
					              <option>09:30</option>
					              <option>10:00</option>
					              <option>10:30</option>
					              <option>11:00</option>
					              <option>11:30</option>
					              <option>12:00</option>
					              <option>12:30</option>
					              <option>13:00</option>
					            </select>
					            <span class="doctor-inline-label">종료 :</span>
					            <select class="doctor-mini-select" name="endTime[]">
					              <option>14:00</option>
					              <option>14:30</option>
					              <option>15:00</option>
					              <option>15:30</option>
					              <option>16:00</option>
					              <option>16:30</option>
					              <option>17:00</option>
					            </select>
					          </div>
					
					          <div class="doctor-schedule-row">
					            <span class="doctor-inline-label">목</span>
					            <select class="doctor-mini-select" name="ampm[]">
					              <option selected>오전</option>
					              <option>오후</option>
					            </select>
					            <span class="doctor-inline-label">시작 :</span>
					            <select class="doctor-mini-select" name="startTime[]">
					              <option>09:00</option>
					              <option>09:30</option>
					              <option>10:00</option>
					              <option>10:30</option>
					              <option>11:00</option>
					              <option>11:30</option>
					              <option>12:00</option>
					              <option>12:30</option>
					              <option>13:00</option>
					            </select>
					            <span class="doctor-inline-label">종료 :</span>
					            <select class="doctor-mini-select" name="endTime[]">
					              <option>14:00</option>
					              <option>14:30</option>
					              <option>15:00</option>
					              <option>15:30</option>
					              <option>16:00</option>
					              <option>16:30</option>
					              <option>17:00</option>
					            </select>
					          </div>
					
					          <div class="doctor-schedule-row">
					            <span class="doctor-inline-label">금</span>
					            <select class="doctor-mini-select" name="ampm[]">
					              <option selected>오전</option>
					              <option>오후</option>
					            </select>
					            <span class="doctor-inline-label">시작 :</span>
					            <select class="doctor-mini-select" name="startTime[]">
					              <option>09:00</option>
					              <option>09:30</option>
					              <option>10:00</option>
					              <option>10:30</option>
					              <option>11:00</option>
					              <option>11:30</option>
					              <option>12:00</option>
					              <option>12:30</option>
					              <option>13:00</option>
					            </select>
					            <span class="doctor-inline-label">종료 :</span>
					            <select class="doctor-mini-select" name="endTime[]">
					              <option>14:00</option>
					              <option>14:30</option>
					              <option>15:00</option>
					              <option>15:30</option>
					              <option>16:00</option>
					              <option>16:30</option>
					              <option>17:00</option>
					            </select>
					          </div>
					
					          <div class="doctor-schedule-row">
					            <span class="doctor-inline-label">토</span>
					            <select class="doctor-mini-select" name="ampm[]">
					              <option selected>오전</option>
					              <option>오후</option>
					            </select>
					            <span class="doctor-inline-label">시작 :</span>
					            <select class="doctor-mini-select" name="startTime[]">
					              <option>09:00</option>
					              <option>09:30</option>
					              <option>10:00</option>
					              <option>10:30</option>
					              <option>11:00</option>
					              <option>11:30</option>
					              <option>12:00</option>
					              <option>12:30</option>
					              <option>13:00</option>
					            </select>
					            <span class="doctor-inline-label">종료 :</span>
					            <select class="doctor-mini-select" name="endTime[]">
					              <option>14:00</option>
					              <option>14:30</option>
					              <option>15:00</option>
					              <option>15:30</option>
					              <option>16:00</option>
					              <option>16:30</option>
					              <option>17:00</option>
					            </select>
					          </div>
					
					          <div class="doctor-schedule-row">
					            <span class="doctor-inline-label">일</span>
					            <select class="doctor-mini-select" name="ampm[]">
					              <option selected>오전</option>
					              <option>오후</option>
					            </select>
					            <span class="doctor-inline-label">시작 :</span>
					            <select class="doctor-mini-select" name="startTime[]">
					              <option>09:00</option>
					              <option>09:30</option>
					              <option>10:00</option>
					              <option>10:30</option>
					              <option>11:00</option>
					              <option>11:30</option>
					              <option>12:00</option>
					              <option>12:30</option>
					              <option>13:00</option>
					            </select>
					            <span class="doctor-inline-label">종료 :</span>
					            <select class="doctor-mini-select" name="endTime[]">
					              <option>14:00</option>
					              <option>14:30</option>
					              <option>15:00</option>
					              <option>15:30</option>
					              <option>16:00</option>
					              <option>16:30</option>
					              <option>17:00</option>
					            </select>
					          </div>
					        </div>
					      </div>
					
					      <div class="doctor-education-section">
					        <div class="doctor-list-head">
					          <div class="doctor-section-title" style="margin-bottom:0;">학력</div>
					          <div class="doctor-list-actions">
					            <button type="button" class="doctor-plus-btn" id="addEducationBtn">+</button>
					            <button type="button" class="doctor-minus-btn" id="removeEducationBtn">-</button>
					          </div>
					        </div>
					
					        <div class="doctor-scroll doctor-history-scroll" id="educationList">
					          <div class="doctor-edu-row">
					            <input type="text" class="doctor-input" name="educationYear[]" placeholder="년도" />
					            <input type="text" class="doctor-input" name="educationContent[]" placeholder="학교와 학위를 입력해주세요..." />
					          </div>
					          <div class="doctor-edu-row">
					            <input type="text" class="doctor-input" name="educationYear[]" value="2009" />
					            <input type="text" class="doctor-input" name="educationContent[]" value="서울대학교 의학박사" />
					          </div>
					          <div class="doctor-edu-row">
					            <input type="text" class="doctor-input" name="educationYear[]" value="2005" />
					            <input type="text" class="doctor-input" name="educationContent[]" value="서울대학교 의학석사" />
					          </div>
					          <div class="doctor-edu-row">
					            <input type="text" class="doctor-input" name="educationYear[]" value="1995" />
					            <input type="text" class="doctor-input" name="educationContent[]" value="서울대학교 의과대학 의학사" />
					          </div>
					        </div>
					      </div>
					    </div>
					
					    <div class="doctor-bottom-area">
					      <div class="doctor-intro-section">
					        <div class="doctor-section-title">소개 제목</div>
					        <input type="text" class="doctor-input doctor-intro-title" id="introTitle" name="introTitle" value="${doctor.introTitle}" />
					
					        <div class="doctor-section-title" style="margin-bottom:6px;">소개글</div>
					        <textarea class="doctor-textarea doctor-intro-text" id="introContent" name="introContent">${doctor.introContent}</textarea>
					      </div>
					
					      <div class="doctor-career-section">
					        <div class="doctor-list-head">
					          <div class="doctor-section-title" style="margin-bottom:0;">경력</div>
					          <div class="doctor-list-actions">
					            <button type="button" class="doctor-plus-btn" id="addCareerBtn">+</button>
					            <button type="button" class="doctor-minus-btn" id="removeCareerBtn">-</button>
					          </div>
					        </div>
					
					        <div class="doctor-scroll doctor-history-scroll" id="careerList">
					          <div class="doctor-career-row">
					            <input type="text" class="doctor-input" name="careerPeriod[]" value="2012. 5. ~ 2014. 4." />
					            <input type="text" class="doctor-input" name="careerContent[]" value="대한중환자의학회 기획위원" />
					          </div>
					          <div class="doctor-career-row">
					            <input type="text" class="doctor-input" name="careerPeriod[]" value="2009. 3. ~ 현재" />
					            <input type="text" class="doctor-input" name="careerContent[]" value="중환자의학 세부전문의" />
					          </div>
					          <div class="doctor-career-row">
					            <input type="text" class="doctor-input" name="careerPeriod[]" value="1996. 3. ~ 2000. 2." />
					            <input type="text" class="doctor-input" name="careerContent[]" value="대한결핵및호흡기학회 의학용어위원" />
					          </div>
					          <div class="doctor-career-row">
					            <input type="text" class="doctor-input" name="careerPeriod[]" value="1995. 3. ~ 1996. 2." />
					            <input type="text" class="doctor-input" name="careerContent[]" value="서울대학병원 인턴" />
					          </div>
					        </div>
					      </div>
					    </div>
					
					    <div class="doctor-actions">
					      <button type="submit" class="doctor-btn doctor-btn-primary">저장</button>
					      <button type="button" class="doctor-btn doctor-btn-secondary" id="doctorCancelBtn">취소</button>
					    </div>
					  </div>
  
					</div>
                </div>
            </form>
        </section>
    </main>
</div>

<script src="<c:url value='/resources/js/admin-layout.js' />"></script>
</body>
</html>
