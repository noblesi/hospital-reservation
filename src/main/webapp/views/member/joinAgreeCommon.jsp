<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="activeMenu" value="login" scope="request" />
<c:set var="depth1" value="로그인" scope="request" />

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>회원가입 약관동의</title>

<link rel="stylesheet" href="<c:url value='/resources/css/user-layout.css?v=${initParam.assetVersion}' />">
<link rel="stylesheet" href="<c:url value='/resources/css/join.css' />">
</head>

<body>

<jsp:include page="/views/common/userHeader.jsp" />
<jsp:include page="/views/common/userBreadcrumb.jsp" />

<main id="content" class="memJoinContent">

    <div class="contHeadingWrap">
        <h2>만 <span class="title">14세 이상</span> 회원가입</h2>
    </div>

    <ul class="stepWrap">
        <li>
            <b>STEP 01</b>
            회원유형
        </li>
        <li class="current">
            <b>STEP 02</b>
            약관동의/본인인증
        </li>
        <li>
            <b>STEP 03</b>
            회원정보
        </li>
        <li>
            <b>STEP 04</b>
            가입완료
        </li>
    </ul>

    <div class="joinStepDesc">
        <div class="contTextWrap">
            <em>한국중앙병원 홈페이지는 이용을 원하는 모든 분들께 무료로 제공되고 있습니다.</em>
        </div>
        <p>단, 게시판 글 게재, 민원서비스, 진료예약 등 일부 컨텐츠는 한국중앙병원 온라인 회원에게만 제공하고 있습니다.</p>
        <p>한국중앙병원의 온라인 회원정책은 <em class="colorPoint">일반회원과 진료회원</em>으로 나뉘어 서비스 되고 있습니다.<br>
        웹회원으로 가입하는 경우, 차후 소정의 절차를 거쳐 진료회원으로 가입할 수 있습니다.</p>
        <p>진료회원으로 가입하면 인터넷 진료예약 등 보다 다양한 서비스를 이용할 수 있습니다.</p>
        <p>회원 가입을 하시려면 <em class="colorPoint">아래의 약관 및 개인정보 수집이용에 동의해주세요.</em><br>
        회원님의 개인정보보호와 더욱 안정된 서비스를 위해 최선을 다하겠습니다. 감사합니다.</p>
    </div>

    <form>
        <fieldset>
            <legend>약관동의 및 본인인증</legend>


            <div class="persInforWrap">
                <div class="contTextWrap">
                    <h4>서비스 이용약관 <span class="colorPoint requiredTxt">(필수)</span></h4>
                </div>

                <div class="scrollBox">
                    <div class="innerScroll">
                        <div class="terms">
                            <h3>제1장 총칙</h3>
                            <strong>제1조 목적</strong>
                            <p>이 약관은 한국중앙병원에서 운영하는 인터넷 홈페이지의 서비스를 이용함에 있어 사이트와 이용자의 권리 의무 및 책임사항을 규정함을 목적으로 합니다.</p>

                            <strong>제2조 용어정의</strong>
                            <p>이 약관에서 사용하는 용어의 정의는 다음과 같습니다.</p>
                            <ul class="paddingList">
                                <li>사이트라 함은 병원이 컴퓨터 등 정보통신 설비를 이용하여 제공할 수 있도록 설정한 가상의 공간을 말합니다.</li>
                                <li>회원이라 함은 본 약관에 동의하고 병원이 제공하는 서비스를 받는 자를 말합니다.</li>
                                <li>아이디라 함은 회원 식별과 서비스 이용을 위하여 회원이 정하고 병원이 승인하는 문자와 숫자의 조합을 말합니다.</li>
                                <li>비밀번호라 함은 회원 본인 확인 및 개인정보 보호를 위하여 회원 자신이 설정한 암호문자를 말합니다.</li>
                            </ul>

                            <strong>제3조 약관의 게시 및 변경</strong>
                            <p>병원은 필요한 경우 관계 법령을 위배하지 않는 범위에서 약관을 변경할 수 있습니다.</p>

                            <strong>제4조 서비스의 내용</strong>
                            <p>병원은 홈페이지를 통해 병원 안내, 진료예약, 게시판, 기타 병원이 정하는 서비스를 제공합니다.</p>
                        </div>
                    </div>
                </div>

                <div class="checkWrap">
                    <input id="checkbox01" type="checkbox" title="이용약관 동의">
                    <label for="checkbox01">이용약관에 동의합니다.</label>
                </div>
            </div>

            <div class="persInforWrap">
                <div class="contTextWrap">
                    <h4>개인정보 수집 이용 동의 <span class="colorPoint requiredTxt">(필수)</span></h4>
                </div>

                <div class="scrollBox">
                    <div class="innerScroll">
                        <h3>이용목적</h3>
                        <p>홈페이지 회원관리, 진료예약 등 각종 서비스 제공</p>

                        <h3>수집 항목</h3>
                        <ul>
                            <li>성명, 생년월일, 주소, 아이디, 비밀번호, 이메일, 휴대전화번호</li>
                        </ul>

                        <h3>보유 및 이용기간</h3>
                        <p class="colorPoint">
                            <u>홈페이지 회원가입 탈퇴 시까지 또는 회원가입 시 선택한 보유기간까지 보유합니다.</u>
                        </p>
                    </div>
                </div>

                <div class="checkWrap">
                    <input id="checkbox02" type="checkbox" title="개인정보 수집 동의">
                    <label for="checkbox02">개인정보 수집 이용하는 것에 동의합니다.</label>
                </div>
            </div>

            <div class="persInforWrap">
                <div class="contTextWrap">
                    <h4>민감정보 수집 및 이용 동의 <span class="colorPoint requiredTxt">(필수)</span></h4>
                </div>

                <div class="scrollBox">
                    <div class="innerScroll">
                        <h3>수집목적</h3>
                        <p>진료예약, 진료내역 조회, 의료서비스 제공 및 본인 확인을 위하여 민감정보를 수집·이용합니다.</p>

                        <h3>수집 항목</h3>
                        <ul>
                            <li>진료예약 정보</li>
                            <li>진료과목 정보</li>
                            <li>진료내역 정보</li>
                        </ul>

                        <h3>보유 및 이용기간</h3>
                        <p class="colorPoint">
                            <u>홈페이지 회원가입 탈퇴 시까지 또는 관련 법령에 따른 보존 기간까지 보유합니다.</u>
                        </p>
                    </div>
                </div>

                <div class="checkWrap">
                    <input id="checkbox03" type="checkbox" title="개인정보 선택 동의">
                    <label for="checkbox03">민감정보 수집 및 이용하는 것에 동의합니다.</label>
                </div>
            </div>

            <div class="boxTypeGray checkAllBox">
                <input type="checkbox" id="checkboxAll" title="이용약관, 개인정보 민감정보 수집,처리 방침 모두 동의">
                <label for="checkboxAll">이용약관, 개인정보, 민감정보 수집 <b>모두</b> 동의합니다.</label>
            </div>
        </fieldset>
    </form>

    <form id="gForm"
          name="gForm"
          action="<c:url value='/views/member/joinFormCommon.jsp' />"
          method="post">
        <input type="hidden" name="join_type" value="TG">

        <div class="btnWrap">
            <button type="button" class="btnType02 btnBig" id="gBeforeBtn">이전단계</button>
            <button type="button" class="btnType03 btnBig" id="gNextBtn">다음단계</button>
        </div>
    </form>

</main>

<jsp:include page="/views/common/userFooter.jsp" />

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="<c:url value='/resources/js/join.js' />"></script>
<script src="<c:url value='/resources/js/user-layout.js?v=${initParam.assetVersion}' />"></script>

</body>
</html>
