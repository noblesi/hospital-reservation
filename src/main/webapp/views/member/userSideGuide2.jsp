<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%-- 마이페이지 공통 사이드바 --%>
<aside class="side-card">
    <div class="side-top">
        <div class="side-icon">
            <img src="<%= request.getContextPath() %>/resources/images/common/userSideBar_myPage2.png"
                 alt="마이페이지 사용자">
        </div>

        <h3>${sessionScope.loginUser.name}</h3>

        <p>
            안녕하세요!<br>
            즐거운 하루 보내세요!
        </p>
    </div>

    <ul class="side-menu">
        <li>
            <a href="<%= request.getContextPath() %>/views/member/myPage.jsp">
                마이페이지
                <span>›</span>
            </a>
        </li>

        <li>
            <a href="#passwordCheckModal"
               id="openPasswordCheckModal"
               role="button"
               aria-controls="passwordCheckModal"
               aria-expanded="false">
                내 정보 관리
                <span>›</span>
            </a>
        </li>
    </ul>

    <div class="side-call">
        <p>대표전화</p>
        <strong>1533-2600</strong>
        <span>
            평일 09:00 - 18:00<br>
            점심시간 12:30 - 13:30
        </span>
    </div>
</aside>

<%-- 개인정보 접근 전 본인 확인용 비밀번호 모달 --%>
<div class="passwordCheckModal"
     id="passwordCheckModal"
     role="dialog"
     aria-modal="true"
     aria-labelledby="passwordCheckTitle"
     aria-hidden="true"
     data-auto-open="<%= "fail".equals(request.getParameter("passwordCheck")) %>">
    <div class="passwordCheckContent">
        <div class="passwordUserIcon">♙</div>
        <h3 id="passwordCheckTitle">비밀번호를 입력해주세요</h3>

        <form action="<%= request.getContextPath() %>/views/member/process/checkUserPasswordProcess.jsp"
              method="post"
              id="passwordCheckForm">
            <div class="passwordInputWrap">
                <label for="infoPassword">비밀번호</label>
                <input type="password"
                       id="infoPassword"
                       name="password"
                       placeholder="현재 비밀번호를 입력해주세요."
                       autocomplete="current-password"
                       required>
                <button type="button" class="passwordToggle" aria-label="비밀번호 표시">보기</button>
            </div>

            <p class="passwordCheckError" id="passwordCheckError">
                <%= "fail".equals(request.getParameter("passwordCheck")) ? "비밀번호가 일치하지 않습니다." : "" %>
            </p>

            <div class="passwordCheckButtons">
                <button type="button" class="passwordCancelBtn" data-password-modal-close>취소</button>
                <button type="submit" class="passwordConfirmBtn">확인</button>
            </div>
        </form>
    </div>
</div>
