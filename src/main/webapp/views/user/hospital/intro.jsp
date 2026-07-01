<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglib.jsp" %>
<c:set var="activeMenu" value="hospital" scope="request" />
<c:set var="depth1" value="병원소개" scope="request" />
<c:set var="depth2" value="병원 소개" scope="request" />
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>병원 소개 | KMCH 한국중앙병원</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/user-layout.css?v=${initParam.assetVersion}">
</head>
<body>
    <%@ include file="/views/common/userHeader.jsp" %>
    <%@ include file="/views/common/userBreadcrumb.jsp" %>

    <main class="user-container">
        <div class="page-title-area">
            <h2>병원 소개</h2>
            <p>환자 중심의 진료와 편리한 예약 서비스를 제공하는 KMCH 한국중앙병원을 소개합니다.</p>
        </div>

        <section class="hospital-hero">
            <div>
                <span>KMCH Korea Medical Center Hospital</span>
                <h3>신뢰할 수 있는 진료, 더 가까운 병원</h3>
                <p>
                    KMCH 한국중앙병원은 정확한 진단과 세심한 진료를 바탕으로 지역사회와 함께 성장하는
                    종합 의료기관을 지향합니다. 환자가 필요한 진료를 쉽게 찾고 빠르게 예약할 수 있도록
                    디지털 진료 예약 환경을 함께 제공합니다.
                </p>
            </div>
        </section>

        <section class="hospital-section">
            <h3>진료 철학</h3>
            <div class="hospital-value-grid">
                <article>
                    <strong>환자 중심</strong>
                    <p>진료 과정에서 환자의 상태와 생활 여건을 함께 고려하여 이해하기 쉬운 안내를 제공합니다.</p>
                </article>
                <article>
                    <strong>전문 진료</strong>
                    <p>진료과와 의료진 정보를 체계적으로 관리하여 필요한 의료 서비스를 빠르게 연결합니다.</p>
                </article>
                <article>
                    <strong>편리한 이용</strong>
                    <p>온라인 예약, 예약 내역 확인, 공지사항 안내 등 병원 이용 흐름을 간결하게 제공합니다.</p>
                </article>
            </div>
        </section>

        <section class="hospital-section hospital-info-band">
            <h3>주요 안내</h3>
            <dl>
                <div>
                    <dt>대표 진료</dt>
                    <dd>내과, 외과, 소아청소년과, 정형외과 등 주요 진료과 운영</dd>
                </div>
                <div>
                    <dt>예약 지원</dt>
                    <dd>온라인 진료 예약과 마이페이지 예약 내역 확인 지원</dd>
                </div>
                <div>
                    <dt>고객 안내</dt>
                    <dd>공지사항과 FAQ를 통한 병원 이용 정보 제공</dd>
                </div>
            </dl>
        </section>
    </main>

    <%@ include file="/views/common/userFooter.jsp" %>

    <script src="${pageContext.request.contextPath}/resources/js/user-layout.js?v=${initParam.assetVersion}"></script>
</body>
</html>
