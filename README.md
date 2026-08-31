# Hospital Reservation

환자가 웹에서 직접 진료를 예약하고 예약 내역을 확인하며, 관리자는 회원·예약·의료진·진료과와 운영 통계를 관리할 수 있도록 구현한 **팀 기반 대학병원 진료 예약 관리 시스템**입니다.

사용자 화면은 JSP/Servlet과 JavaScript·jQuery를 기반으로 구성했고, JDBC와 Oracle Database를 이용해 데이터를 처리했습니다. 요청 처리, 업무 흐름, DB 접근, 화면 출력을 분리하기 위해 `Servlet → Service → DAO → JSP` 구조를 기준으로 개발했습니다.

> 프로젝트에 표시되는 병원명, 주소, 대표전화, 오시는 길 안내, 지도 좌표는 학습용 가상 정보이며 실제 병원 또는 의료기관의 공식 정보가 아닙니다.

---

## 프로젝트 개요

| 구분 | 내용 |
| --- | --- |
| 개발 기간 | 2026.06.02 ~ 2026.07.10 (6주) |
| 개발 인원 | 5명 시작 → 4명 완료 |
| 프로젝트 형태 | 팀 프로젝트 |
| Backend | Java 8, JSP/Servlet, JDBC |
| Frontend | HTML5, CSS3, JavaScript, jQuery |
| Database | Oracle Database 21c |
| Server | Apache Tomcat 9 |
| Collaboration | Git, GitHub |

---

## 주요 기능

### 사용자

- 회원가입·로그인
- 아이디·비밀번호 찾기
- 마이페이지
- 진료과·의료진 기반 진료 예약
- AJAX 기반 예약 가능 날짜·시간 조회
- 예약 내역 조회 및 취소
- 공지사항 / FAQ 조회
- 병원 소개 / 오시는 길

### 관리자

- 회원 목록·상세 조회 및 관리
- 예약 목록·상세 조회
- 예약 다중 조건 검색 및 상태 변경
- 회원 메모 관리
- 의료진 / 진료과 관리
- 공지사항 / FAQ 관리
- 운영 통계 대시보드

---

## 시스템 구조

```text
Browser
   ↓
Servlet
   ↓
Service
   ↓
DAO
   ↓
Oracle Database

Servlet
   ↓
JSP
   ↓
Browser
```

| 구분 | 역할 |
| --- | --- |
| `JSP` | 화면 출력, EL/JSTL 기반 데이터 표현 |
| `Servlet` | URL 매핑, 요청값 수집, 기본 검증, 화면 이동 제어 |
| `Service` | 업무 규칙과 처리 순서 조율 |
| `DAO` | SQL 실행과 DB 접근 |
| `DTO` | 계층 간 데이터 전달 |

---

## 예약 화면 AJAX 흐름

예약 화면에서는 진료과, 의료진, 진료 날짜를 선택할 때 전체 페이지를 다시 로드하지 않고 필요한 영역만 갱신합니다.

```text
진료과 선택
→ /appointment/ajax.do
→ UserAppointmentAjaxServlet
→ UserAppointmentService
→ UserAppointmentDAO
→ 의료진 목록 갱신

의료진 선택
→ AJAX 요청
→ 진료 가능 날짜 갱신

진료 날짜 선택
→ AJAX 요청
→ 예약 가능 시간 갱신
```

AJAX는 화면 일부 갱신에만 사용하며 실제 업무 처리와 DB 조회는 `Servlet → Service → DAO` 흐름을 따릅니다.

---

## 팀 역할 분담

| 담당 | 업무 영역 | 주요 담당 |
| --- | --- | --- |
| 박정욱 | 회원 / 인증 / 마이페이지 | 로그인, 회원가입, 계정 찾기, 마이페이지, 개인정보 수정 |
| 조창완 | 사용자 예약 | 진료과·의료진·날짜·시간 선택, 예약 확인·완료·취소 |
| 채대경 | 의료진 / 진료과 관리 | 관리자 의료진·진료과 등록·수정, 관련 화면 |
| 김민성 | 관리자 / 게시판 / 대시보드 / 공통 | 관리자 회원·예약·메모, 게시판·메인, 통계 대시보드, 공통 Layout·Pagination·DB 연결 |

---

## 주요 공통 구성

| 파일 | 역할 |
| --- | --- |
| `DBConnection` | JNDI DataSource 기반 DB 연결과 JDBC 자원 정리 |
| `BaseSearchDTO` | 목록 검색에서 사용하는 공통 페이지 관련 필드 |
| `PaginationUtil` | 조회 범위와 페이지 번호 계산 |
| `pagination.jsp` | 목록 화면의 공통 Pagination UI |
| `userHeader.jsp` / `userFooter.jsp` | 사용자 공통 Layout |
| `adminHeader.jsp` / `adminSidebar.jsp` | 관리자 공통 Layout |
| `message.jsp` | 공통 성공·오류 메시지 출력 |

---

## 프로젝트 구조

```text
src/main/java/com/hospital
├── common                  # 공통 DTO / Util
├── admin
│   ├── dashboard           # 관리자 통계 대시보드
│   ├── member              # 회원 관리
│   ├── appointment         # 예약 관리
│   ├── memo                # 회원 메모
│   ├── doctor              # 의료진 관리
│   ├── department          # 진료과 관리
│   └── board               # 관리자 게시판
├── member                  # 회원 / 인증 / 마이페이지
└── user
    ├── board               # 사용자 공지사항 / FAQ
    └── appointment         # 사용자 진료 예약

src/main/webapp
├── resources
│   ├── css
│   ├── js
│   ├── images
│   └── uploads
└── views
    ├── common
    ├── admin
    ├── member
    └── user
```

- DAO와 Service는 기능별 패키지에 배치합니다.
- 여러 기능에서 공유하는 DTO와 Util은 `common`에 둡니다.
- 관리자 JSP와 사용자 JSP를 별도의 View 영역으로 구분합니다.
- 공통 Header·Footer·Sidebar·Pagination·Message는 공통 JSP로 분리합니다.

---

## 개발 구조 기준

### JSP

- DB 연결이나 SQL을 직접 실행하지 않습니다.
- JSP에서 Service·DAO를 직접 생성하는 방식은 지양합니다.
- Servlet이 전달한 데이터를 EL/JSTL로 출력합니다.

### Servlet

- 요청 Parameter와 Session을 확인합니다.
- 기본 Validation을 수행합니다.
- Service를 호출하고 처리 결과에 따라 Forward / Redirect를 결정합니다.

### Service

- 업무 흐름과 규칙을 담당합니다.
- 여러 DAO 호출이 필요한 경우 처리 순서를 조율합니다.
- DB 접근 자체는 DAO에 위임합니다.

### DAO

- `PreparedStatement`를 이용해 Parameter를 Binding합니다.
- SQL 실행 결과를 DTO로 변환해 반환합니다.
- Connection·PreparedStatement·ResultSet 등의 JDBC 자원을 정리합니다.

---

## 지도 API 연결 방법

오시는 길 화면은 Kakao Map JavaScript API 연동을 기준으로 구성했습니다. 실제 API Key는 저장소에 커밋하지 않습니다.

1. Kakao Developers에서 Application과 JavaScript Key를 발급합니다.
2. Web Platform에 로컬 실행 주소를 등록합니다.
3. 로컬 테스트 시 `src/main/webapp/WEB-INF/web.xml`의 `kakaoMapAppKey`에 개인 Key를 설정합니다.

```xml
<context-param>
  <param-name>kakaoMapAppKey</param-name>
  <param-value>발급받은_JAVASCRIPT_KEY</param-value>
</context-param>
```

Key가 비어 있으면 지도 API를 로드하지 않고 임시 위치 안내 영역을 표시합니다.

---

## 개발 당시 확인한 개선 과제

프로젝트 종료 시점에 다음과 같은 개선 필요 항목을 확인했습니다.

- 관리자 영역의 인증·권한 검증 강화
- Password 저장·비교 방식의 Hash 기반 전환
- 동시 요청 상황의 예약 중복 방지 강화
- JSP 직접 출력과 남아 있는 Scriptlet의 XSS 대응 보완
- 일부 Process JSP를 Servlet Controller 구조로 추가 정리
- DAO / Service Error Handling과 Logging 개선

이 항목들은 당시 프로젝트에서 확인한 기술적 한계와 후속 개선 포인트를 기록한 것입니다.

---

## 개인 기여 정리

팀 전체 프로젝트와 별도로 김민성의 담당 기능, 팀장 경험, 관리자 예약·대시보드 구현 및 파일 업로드 Troubleshooting은 [`KMS` 브랜치 README](https://github.com/noblesi/hospital-reservation/tree/KMS)에 정리되어 있습니다.
