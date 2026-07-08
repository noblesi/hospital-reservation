<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="sideBarWrap">
	<div class="sideBarHeader">
		<img src="${pageContext.request.contextPath}/resources/images/appointment/stethoscope.png" class="stethoscopeIcon">
		<h2 class="sbTitle">진료안내</h2>
		<p class="sbP">
			 정확한 진단과 치료로<br>
			 건강을 지키겠습니다.
		</p>
	</div>
	<div class="sideBarMain">
		<ul class="sbmUl">
			<li class="sbmLi">
				<a href="${pageContext.request.contextPath}/department/departmentList.do">
					<span class="sbmTitle">진료과</span>
					<i class="bi bi-chevron-right crIcon"></i>
				</a>
			</li>
			<li class="sbmLi">
				<a href="${pageContext.request.contextPath}/appointment/reserve.do">
					<span class="sbmTitle">진료 예약</span>
					<i class="bi bi-chevron-right crIcon"></i>
				</a>
			</li>
			<li class="sbmLi">
				<a href="${pageContext.request.contextPath}/appointment/list.do">
					<span class="sbmTitle">예약확인</span>
					<i class="bi bi-chevron-right crIcon"></i>
				</a>
			</li>
		</ul>
	</div>
	<div class="sideBarFooter">
		<h4 class="sbfSubTitle">
			<i class="bi bi-telephone"></i>
			대표전화
		</h4>
		<h1 class="sbfTel">
			1588-0000
		</h1>
		<p class="sbfTelInfo">
			평일 09:00 ~ 18:00<br>
			점심시간 12:30 ~ 13:30
		</p>
	</div>
</div>
