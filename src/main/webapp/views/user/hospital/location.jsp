<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglib.jsp" %>
<c:set var="activeMenu" value="hospital" scope="request" />
<c:set var="depth1" value="병원소개" scope="request" />
<c:set var="depth2" value="오시는 길" scope="request" />
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>오시는 길 | KMCH 한국중앙병원</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/user-layout.css">
</head>
<body>
    <%@ include file="/views/common/userHeader.jsp" %>
    <%@ include file="/views/common/userBreadcrumb.jsp" %>

    <main class="user-container">
        <div class="page-title-area">
            <h2>오시는 길</h2>
            <p>KMCH 한국중앙병원 방문에 필요한 주소, 교통편, 주차 정보를 안내합니다.</p>
        </div>

        <section class="hospital-map-panel" aria-label="병원 위치 안내 지도">
            <div id="hospitalMap"
                 class="hospital-map-placeholder"
                 data-latitude="37.5665"
                 data-longitude="126.9780"
                 data-place-name="KMCH 한국중앙병원"
                 data-address="서울특별시 중앙구 의료로 100">
                <div class="hospital-map-fallback">
                    <strong>KMCH 한국중앙병원</strong>
                    <span>서울특별시 중앙구 의료로 100</span>
                    <small>지도 API 연결 전 임시 위치 안내 영역입니다.</small>
                </div>
            </div>
            <div class="hospital-location-summary">
                <h3>방문 안내</h3>
                <p>초진 및 예약 환자는 방문 전 예약 내역과 신분증을 확인해 주세요. 지도는 학습용 임시 좌표를 기준으로 제공될 예정입니다.</p>
                <a class="main-primary-link" href="<c:url value='/appointment/reserve.do' />">진료 예약하기</a>
            </div>
        </section>

        <section class="hospital-section hospital-direction-grid">
            <article>
                <h3>주소</h3>
                <p>서울특별시 중앙구 의료로 100 KMCH 한국중앙병원</p>
                <p>대표전화 02-1234-5678</p>
            </article>
            <article>
                <h3>대중교통</h3>
                <ul>
                    <li>지하철 중앙역 2번 출구에서 도보 7분</li>
                    <li>간선버스 101, 402번 KMCH 한국중앙병원 정류장 하차</li>
                    <li>마을버스 중앙03번 병원 후문 정류장 하차</li>
                </ul>
            </article>
            <article>
                <h3>자가용 및 주차</h3>
                <ul>
                    <li>내비게이션에서 KMCH 한국중앙병원 또는 의료로 100을 검색해 주세요.</li>
                    <li>외래 진료 당일 접수 확인 시 기본 주차 지원</li>
                    <li>장애인 차량과 응급 방문 차량은 1층 안내데스크에 문의</li>
                </ul>
            </article>
        </section>
    </main>

    <%@ include file="/views/common/userFooter.jsp" %>

    <script src="${pageContext.request.contextPath}/resources/js/user-layout.js"></script>
    <c:if test="${not empty initParam.kakaoMapAppKey}">
        <script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=${initParam.kakaoMapAppKey}&autoload=false"></script>
    </c:if>
    <script src="${pageContext.request.contextPath}/resources/js/hospital-location.js"></script>
</body>
</html>
