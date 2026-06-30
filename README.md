# Hospital Reservation

## 주의사항

이 프로젝트에 표시되는 병원명, 주소, 대표전화, 오시는 길 안내, 지도 좌표는 학습용 가상 정보이다.
실제 병원 또는 의료기관의 공식 정보가 아니며, 특정 기관의 위치와 연락처를 안내할 목적으로 사용하지 않는다.

## 지도 API 연결 방법

오시는 길 화면은 카카오 지도 JavaScript API 연동을 기준으로 준비되어 있다. 실제 API 키는 저장소에 커밋하지 않는다.

1. 카카오 개발자 사이트에서 애플리케이션을 생성하고 JavaScript 키를 발급한다.
2. 플랫폼 설정의 Web 도메인에 로컬 실행 주소를 등록한다.
   - 예: `http://localhost:8080`
   - 예: `http://localhost:8080/hospital-reservation`
3. 로컬 테스트 시 `src/main/webapp/WEB-INF/web.xml`의 `kakaoMapAppKey` `param-value`에 개인 JavaScript 키를 넣는다.

```xml
<context-param>
  <param-name>kakaoMapAppKey</param-name>
  <param-value>발급받은_JAVASCRIPT_KEY</param-value>
</context-param>
```

4. 실제 키를 넣은 상태로 커밋하지 않는다. 키가 비어 있으면 지도 API는 로드되지 않고 임시 위치 안내 영역이 표시된다.
5. 지도 좌표는 `location.jsp`의 `#hospitalMap` `data-latitude`, `data-longitude` 값으로 관리한다.

현재 좌표는 학습용 임시 좌표이며 실제 병원 위치와 무관하다.

## 폴더 구조 기준

```text
src/main/java/com/hospital
├── common
│   ├── DBConnection.java
│   ├── dto
│   │   ├── BaseSearchDTO.java
│   │   ├── MemberDTO.java
│   │   ├── DoctorDTO.java
│   │   ├── DepartmentDTO.java
│   │   ├── AppointmentDTO.java
│   │   ├── BoardPostDTO.java
│   │   └── DoctorScheduleDTO.java
│   └── util
│       └── PaginationUtil.java
├── admin
│   ├── dashboard
│   │   ├── AdminDashboardService.java
│   │   └── AdminDashboardDAO.java
│   ├── member
│   │   ├── AdminMemberService.java
│   │   ├── AdminMemberDAO.java
│   │   └── dto
│   │       └── AdminMemberSearchDTO.java
│   ├── appointment
│   │   ├── AdminAppointmentService.java
│   │   ├── AdminAppointmentDAO.java
│   │   └── dto
│   │       └── AdminAppointmentSearchDTO.java
│   ├── memo
│   │   ├── AdminMemoService.java
│   │   └── AdminMemoDAO.java
│   ├── doctor
│   │   ├── AdminDoctorService.java
│   │   ├── AdminDoctorDAO.java
│   │   └── dto
│   │       └── AdminDoctorFormDTO.java
│   ├── department
│   │   ├── AdminDepartmentService.java
│   │   └── AdminDepartmentDAO.java
│   └── board
│       ├── AdminBoardService.java
│       ├── AdminBoardDAO.java
│       └── dto
│           └── AdminBoardSearchDTO.java
└── user
    ├── member
    │   ├── LoginService.java
    │   ├── LoginDAO.java
    │   ├── MemberRegisterService.java
    │   ├── MemberRegisterDAO.java
    │   ├── FindAccountService.java
    │   ├── FindAccountDAO.java
    │   ├── UserMyPageService.java
    │   ├── UserMyPageDAO.java
    │   ├── UpdateUserInfoService.java
    │   ├── UpdateUserInfoDAO.java
    │   └── dto
    │       ├── LoginRequestDTO.java
    │       ├── MemberRegisterRequestDTO.java
    │       └── UpdateUserInfoRequestDTO.java
    ├── appointment
    │   ├── UserAppointmentService.java
    │   ├── UserAppointmentDAO.java
    │   └── dto
    │       ├── UserAppointmentRequestDTO.java
    │       ├── UserAppointmentOptionDTO.java
    │       └── UserAppointmentConfirmDTO.java
    ├── doctor
    │   ├── DoctorService.java
    │   └── DoctorDAO.java
    ├── department
    │   ├── DepartmentService.java
    │   └── DepartmentDAO.java
    └── board
        ├── BoardService.java
        ├── BoardDAO.java
        └── dto
            └── BoardSearchDTO.java

src/main/webapp
├── resources
│   ├── css
│   │   ├── admin-layout.css
│   │   └── user-layout.css
│   ├── js
│   │   ├── admin-layout.js
│   │   └── user-layout.js
│   ├── images
│   │   ├── common
│   │   └── doctors
│   └── uploads
└── views
    ├── common
    │   ├── taglib.jsp
    │   ├── message.jsp
    │   ├── pagination.jsp
    │   ├── adminHeader.jsp
    │   ├── adminSidebar.jsp
    │   ├── userHeader.jsp
    │   ├── userBreadcrumb.jsp
    │   └── userFooter.jsp
    ├── admin
    │   ├── dashboard
    │   ├── member
    │   ├── appointment
    │   ├── memo
    │   ├── doctor
    │   ├── department
    │   └── board
    └── user
        ├── member
        ├── appointment
        ├── doctor
        ├── department
        └── board
```

- DAO와 Service는 `admin` 또는 `user` 아래의 기능별 폴더에 작성한다.
- 여러 화면에서 함께 사용하는 기본 데이터 DTO는 `common/dto`에 둔다.
- 검색 조건, 요청값, 화면 전용 DTO처럼 특정 기능에서만 쓰는 DTO는 해당 기능 폴더 안의 `dto`에 둔다.
- 관리자 화면 JSP는 `views/admin`, 사용자 화면 JSP는 `views/user` 아래에서 기능별로 나눈다.
- 공통 include JSP, 공통 CSS/JS, 공통 util은 `common` 또는 `resources`의 공통 위치에 둔다.

## JSP, Servlet, Service, DAO, DTO 역할 기준

이 프로젝트는 기존 Model1 방식의 JSP 중심 처리를 줄이고, 요청 처리는 `Servlet`, 업무 흐름은 `Service`, DB 접근은 `DAO`, 화면 출력은 `JSP`가 맡는 구조로 정리한다.

기본 흐름은 다음 기준을 따른다.

```text
브라우저 요청
-> Servlet
-> Service
-> DAO
-> DB
-> DAO
-> Service
-> Servlet
-> JSP
-> 브라우저 응답
```

| 구분 | 역할 | 작성해야 하는 내용 | 작성하지 않는 내용 |
|---|---|---|---|
| `JSP` | 화면 출력 담당 | HTML 구조, CSS/JS 연결, form, table, button, EL/JSTL 기반 데이터 출력, 공통 header/footer/sidebar include | DB 연결, SQL 실행, Service 직접 생성, 복잡한 조건 처리, 회원가입/예약/수정 같은 업무 처리 |
| `Servlet` | 요청과 응답 흐름 제어 | URL 매핑, request parameter 수집, 기본 validation, session 확인, Service 호출, request/session attribute 설정, forward/redirect 결정 | SQL 실행, ResultSet 처리, 화면 HTML 생성, 복잡한 business rule 직접 처리 |
| `Service` | 업무 규칙과 처리 순서 담당 | 중복 확인, 권한/상태 판단, 여러 DAO 호출 조합, transaction이 필요한 업무 흐름 판단, Controller에 반환할 결과 정리 | request/response 직접 사용, JSP forward, SQL 문자열 작성, ResultSet 처리 |
| `DAO` | DB 접근 담당 | SQL 작성, `PreparedStatement` parameter binding, query/update 실행, `ResultSet`을 DTO로 변환, JDBC 자원 정리 | 화면 이동 결정, session 처리, HTML 생성, 업무 정책 판단 |
| `DTO` | 데이터 전달 객체 | 화면 입력값, 검색 조건, DB 조회 결과, 계층 간 전달할 필드와 getter/setter | DB 연결, 업무 처리 method, 화면 이동 logic |

### JSP 작성 기준

- JSP에서는 Java scriptlet(`<% %>`)과 표현식(`<%= %>`) 사용을 줄인다.
- 단순 출력은 EL 또는 JSTL을 사용한다.
- 사용자 입력값이나 DB 조회값을 출력할 때는 가능하면 `<c:out>`을 사용한다.
- JSP에서 `new Service()`, `new DAO()`, `DBConnection.getConnection()`을 직접 호출하지 않는다.
- JSP가 필요한 데이터는 Servlet에서 `request.setAttribute()`로 전달한다.

예시:

```jsp
<c:out value="${member.name}" />
```

### Servlet 작성 기준

- Servlet은 화면과 Service 사이의 controller 역할을 한다.
- `request.getParameter()`로 요청값을 받고, 필요한 기본 검증을 수행한다.
- 업무 처리는 Service에 맡긴다.
- 처리 결과에 따라 `forward` 또는 `redirect`를 선택한다.
- JSP에서 사용할 데이터는 `request.setAttribute()`에 담는다.
- 로그인 사용자 정보, 일회성 메시지처럼 요청 이후에도 잠시 필요한 값은 `session.setAttribute()`를 사용할 수 있다.

### Service 작성 기준

- Service는 한 기능의 업무 흐름을 표현한다.
- 예를 들어 회원가입에서는 아이디 중복 확인, DTO 값 검증, 회원 insert, 미성년자 정보 insert 같은 순서를 조율한다.
- 예약에서는 예약 가능 여부 확인, 예약 등록, 예약 중복 방지 같은 업무 규칙을 담당한다.
- DB 접근은 DAO에 맡기고, Service는 DAO 결과를 바탕으로 성공/실패 또는 화면에 필요한 결과를 반환한다.

### DAO 작성 기준

- DAO는 DB와 직접 대화하는 계층이다.
- SQL은 DAO 안에 작성하고, 외부 입력값은 문자열 결합이 아니라 `PreparedStatement`의 `?` parameter로 바인딩한다.
- `ResultSet`에서 값을 꺼내 DTO에 담아 Service로 반환한다.
- `Connection`, `PreparedStatement`, `ResultSet` 같은 JDBC 자원은 `finally`에서 정리한다.

### DTO 작성 기준

- DTO는 데이터를 담아서 계층 사이에 전달하는 객체다.
- 예를 들어 `MemberDTO`는 회원 데이터, `AdminDoctorSearchDTO`는 의료진 검색 조건, `UserAppointmentConfirmDTO`는 예약 완료 화면에 필요한 데이터를 담는다.
- DTO에는 DB 처리, 화면 이동, 업무 판단 logic을 넣지 않는다.

## Ajax 역할

`Ajax`는 `JSP`, `Servlet`, `Service`, `DAO`, `DTO`처럼 별도의 계층이 아니라, 브라우저가 화면 전체를 새로고침하지 않고 서버와 통신하는 방식이다.

이 프로젝트에서는 주로 다음 상황에서 사용한다.

- 진료과 선택 후 의료진 목록만 다시 조회
- 의사 선택 후 예약 가능 날짜/시간만 다시 조회
- 아이디 중복 확인처럼 작은 결과만 즉시 확인
- 검색 조건 변경 후 일부 화면 영역만 갱신

Ajax 흐름은 일반 요청과 거의 같지만, 응답이 전체 JSP 화면이 아니라 필요한 데이터나 HTML 조각이라는 점이 다르다.

```text
브라우저 JavaScript
-> Ajax 요청
-> Servlet
-> Service
-> DAO
-> DB
-> Servlet 응답
-> JavaScript가 현재 화면 일부 갱신
```

Ajax를 사용할 때도 DB 처리나 업무 로직을 JavaScript에 넣지 않는다. JavaScript는 사용자 이벤트를 받아 Ajax 요청을 보내고, 서버 응답을 화면에 반영하는 역할만 담당한다.

| 구분 | 역할 |
|---|---|
| JavaScript | 클릭, 선택 변경 같은 이벤트 처리, Ajax 요청 전송, 응답을 받아 화면 일부 갱신 |
| Ajax Servlet | Ajax 요청 parameter 검증, Service 호출, 필요한 응답 생성 |
| Service | Ajax 요청에서도 동일하게 업무 규칙 처리 |
| DAO | Ajax 요청에서도 동일하게 DB 조회/수정 처리 |
| DTO | Ajax 요청/응답에 필요한 데이터 전달 |

### 예약 화면 Ajax 예시

사용자 예약 화면에서는 `appointment.jsp`가 처음 화면 구조를 출력하고, `appointment.js`가 사용자의 선택에 따라 `/appointment/ajax.do`로 Ajax 요청을 보낸다.

예를 들어 예약 과정은 다음처럼 진행된다.

```text
진료과 선택
-> Ajax 요청: /appointment/ajax.do?action=doctorList&deptNo=...
-> UserAppointmentAjaxServlet
-> UserAppointmentService
-> UserAppointmentDAO
-> 해당 진료과 의료진 조회
-> 의료진 목록 영역만 갱신

의료진 선택
-> Ajax 요청: /appointment/ajax.do?action=schedule&dln=...
-> UserAppointmentAjaxServlet
-> UserAppointmentService
-> UserAppointmentDAO
-> 해당 의료진의 진료 가능 날짜 조회
-> 진료일정 영역만 갱신

진료 날짜 선택
-> Ajax 요청: /appointment/ajax.do?action=timeTable&date=...&dln=...
-> UserAppointmentAjaxServlet
-> UserAppointmentService
-> UserAppointmentDAO
-> 해당 날짜의 예약 가능 시간 조회
-> 시간 선택 영역만 갱신
```

이처럼 Ajax는 예약 화면에서 진료과, 의료진, 진료 날짜를 선택할 때마다 전체 페이지를 새로고침하지 않고 필요한 영역만 바꾸는 데 사용한다. 단, Ajax가 직접 DB를 조회하는 것은 아니며 실제 조회와 업무 처리는 항상 `Servlet -> Service -> DAO` 흐름을 따른다.

## 역할분담

| 담당 | 업무 영역 | 클래스 설계 및 비즈니스 로직 | 홈페이지 UI |
|---|---|---|---|
| 박정욱 | 회원/인증/마이페이지 | `LoginService`, `LoginDAO`, `MemberRegisterService`, `MemberRegisterDAO`, `FindAccountService`, `FindAccountDAO`, `UserMyPageService`, `UserMyPageDAO`, `UpdateUserInfoService`, `UpdateUserInfoDAO` | 로그인, 회원가입, 아이디/비밀번호 찾기, 마이페이지, 개인정보 수정, 예약 내역, 진료 내역 화면 |
| 조창완 | 사용자 예약 | `UserAppointmentService`, `UserAppointmentDAO`, `UserAppointmentRequestDTO`, `UserAppointmentOptionDTO`, `UserAppointmentConfirmDTO` | 진료과 선택, 의사 선택, 날짜/시간 선택, 예약 확인, 예약 완료, 예약 취소 화면 |
| 채대경 | 의료진/진료과 관리 | `AdminDoctorService`, `AdminDoctorDAO`, `AdminDepartmentService`, `AdminDepartmentDAO`, `DoctorService`, `DoctorDAO`, `DepartmentService`, `DepartmentDAO` | 관리자 의사 등록/수정, 의사 스케줄 관리, 진료과 등록/수정, 사용자 의료진 소개, 사용자 진료과 소개 화면 |
| 김민성 | 관리자 회원/예약/메모, 게시판/메인/대시보드/공통 | `AdminMemberService`, `AdminMemberDAO`, `AdminAppointmentService`, `AdminAppointmentDAO`, `AdminMemoService`, `AdminMemoDAO`, `BoardService`, `BoardDAO`, `AdminBoardService`, `AdminBoardDAO`, `MainPageService`, `AdminDashboardService`, `AdminDashboardDAO`, `PaginationUtil`, `BaseSearchDTO`, `DBConnection` | 관리자 회원 목록/상세, 예약 목록/상세, 예약 승인/취소, 예약 상태 변경, 회원 메모 관리 화면, 메인 페이지, 공지사항/FAQ, 관리자 게시판, 관리자 대시보드, 공통 header/footer/nav/layout, 공통 메시지/페이징 |

## 공용 파일 쓰임새

### Java

| 파일 | 쓰임새 |
|---|---|
| `com.hospital.common.util.DBConnection` | DB 접속 정보를 관리하고 `Connection` 생성 및 JDBC 자원 정리를 공통으로 처리한다. DAO에서 DB 연결이 필요할 때 사용한다. |
| `com.hospital.common.dto.BaseSearchDTO` | 목록 조회 검색 DTO에서 공통으로 사용하는 `currentPage`, `pageScale`, `startNum`, `endNum` 필드를 제공한다. 기능별 검색 DTO가 상속해서 사용한다. |
| `com.hospital.common.util.PaginationUtil` | 현재 페이지, 전체 데이터 수, 페이지 크기, 페이지 블록 크기를 기준으로 DAO 조회 범위와 JSP 페이지 번호 정보를 계산한다. |

### JSP

| 파일 | 쓰임새 |
|---|---|
| `views/common/taglib.jsp` | JSTL core 태그 선언을 공통으로 제공한다. JSTL을 사용하는 JSP에서 먼저 include한다. |
| `views/common/message.jsp` | `message`, `errorMessage`를 화면에 출력한다. request scope와 session scope를 모두 확인하고, session 메시지는 출력 후 제거한다. |
| `views/common/pagination.jsp` | 목록 화면의 페이지 번호, 처음/이전/다음/마지막 이동 링크를 공통으로 출력한다. `pagination` 객체 또는 `currentPage`, `totalPage`, `startPage`, `endPage` 속성을 사용한다. |
| `views/common/userHeader.jsp` | 사용자 화면의 상단 로고, 메뉴, 로그인/회원가입 영역을 출력한다. 현재 메뉴 표시는 `activeMenu` 속성을 사용한다. |
| `views/common/userBreadcrumb.jsp` | 사용자 화면의 현재 위치를 출력한다. `depth1`, `depth2` 속성을 사용한다. |
| `views/common/userFooter.jsp` | 사용자 화면의 하단 병원 정보와 인증 배지를 출력한다. |
| `views/common/adminHeader.jsp` | 관리자 화면의 상단 영역과 관리자 이름, 로그아웃 링크를 출력한다. |
| `views/common/adminSidebar.jsp` | 관리자 화면의 왼쪽 메뉴를 출력한다. 현재 메뉴 표시는 `adminMenu` 속성을 사용한다. |

## 구현 상태

> 작성 기준: 2026-06-25 22:40:26 KST(+09:00), 현재 작성된 파일 기준으로 정리한다.

### 구현 전

| 영역 | 대상 | 비고 |
|---|---|---|
| 관리자 메모 관리 | `AdminMemoService`, `AdminMemoDAO`, 메모 관리 화면 | README 역할분담에는 있으나 실제 파일은 아직 없다. |
| 사용자 의료진 소개 | `DoctorService`, `DoctorDAO`, 사용자 의료진 목록/상세 화면 | 관리자 의료진 관리 파일은 있으나 사용자 소개용 모듈은 아직 없다. |
| 사용자 진료과 소개 | `DepartmentService`, `DepartmentDAO`, 사용자 진료과 목록/상세 화면 | 관리자 진료과 관리 파일은 있으나 사용자 소개용 모듈은 아직 없다. |
| 공통 예약 DTO 통합 | `common/dto/AppointmentDTO.java` | 현재는 `member/dto/UserAppointmentDTO`와 `user/appointment/dto/*` 중심으로 나뉘어 있다. |

### 구현 중

| 영역 | 대상 | 현재 상태 |
|---|---|---|
| 회원/인증/마이페이지 | `LoginService`, `MemberRegisterService`, `FindAccountService`, `UserMyPageService`, `UpdateUserInfoService`, member JSP | 일부 마이페이지 흐름은 Servlet으로 연결되어 있으나 로그인, 회원가입, 아이디/비밀번호 찾기, 정보수정 process JSP가 남아 있다. |
| 관리자 회원 관리 | `AdminMemberService`, `AdminMemberDAO`, `AdminMemberListServlet`, `AdminMemberDetailServlet`, 관리자 회원 JSP | 목록/상세 조회 구조는 있으나 상태 변경, 메모 연동, 권한 검증, error handling 보완이 필요하다. |
| 관리자 예약 관리 | `AdminAppointmentService`, `AdminAppointmentDAO`, `AdminAppointmentListServlet`, `adminAppointmentList.jsp` | 예약 목록 조회 구조는 있으나 승인/취소/상태 변경 처리와 transaction/error handling 보완이 필요하다. |
| 관리자 의료진 관리 | `AdminDoctorService`, `AdminDoctorDAO`, `AdminDoctorListServlet`, `AdminDoctorFormServlet`, 의료진 JSP | 목록/상세 화면의 JSP 내 Service 호출은 제거했으나 DAO error handling, validation, transaction 정리가 남아 있다. |
| 관리자 진료과 관리 | `AdminDepartmentService`, `AdminDepartmentDAO`, `AdminDepartmentListServlet`, `AdminDepartmentFormServlet`, 진료과 JSP | Servlet 기반 목록/폼과 기존 process JSP가 함께 남아 있어 등록/수정 흐름 정리가 필요하다. |

### 구현 완료

| 영역 | 대상 | 완료 기준 |
|---|---|---|
| 공통 DB 연결 | `DBConnection` | Tomcat JNDI DataSource 기반 DB 연결과 JDBC 자원 정리 유틸이 작성되어 있다. |
| 공통 pagination | `PaginationUtil`, `BaseSearchDTO`, `pagination.jsp` | 목록 조회 범위와 JSP page navigation 계산을 공통화했다. |
| 공통 layout | `userHeader.jsp`, `userBreadcrumb.jsp`, `userFooter.jsp`, `adminHeader.jsp`, `adminSidebar.jsp`, `message.jsp` | 사용자/관리자 공통 include JSP와 message 출력 구조가 작성되어 있다. |
| 사용자 예약 기본 흐름 | `UserAppointment*Servlet`, `UserAppointmentService`, `UserAppointmentDAO`, 예약 JSP | 예약 화면, AJAX 조회, 예약 등록, 목록, 완료 상세, 취소 흐름을 Servlet -> Service -> DAO -> JSP 구조로 정리했다. 삭제된 legacy 예약 JSP 경로는 `web.xml`에서 새 Servlet으로 연결한다. |
| 사용자 게시판 | `BoardListServlet`, `BoardDetailServlet`, `BoardService`, `BoardDAO`, `userBoardList.jsp`, `userBoardDetail.jsp` | 공지사항/FAQ 목록, 상세, 검색, pagination 흐름이 Servlet -> Service -> DAO -> JSP 구조로 작성되어 있다. |
| 관리자 게시판 | `AdminBoardListServlet`, `AdminBoardFormServlet`, `AdminBoardSaveServlet`, `AdminBoardDeleteServlet`, `AdminBoardService`, `AdminBoardDAO`, 관리자 게시판 JSP | 관리자 공지사항/FAQ 목록, 등록/수정, 삭제 흐름이 작성되어 있다. |
| 메인/대시보드 | `MainPageServlet`, `MainPageService`, `AdminDashboardServlet`, `AdminDashboardService`, `AdminDashboardDAO` | 메인 화면과 관리자 대시보드 진입 및 조회 구조가 작성되어 있다. |

### 수정보완필요

| 우선순위 | 영역 | 보완 내용 |
|---|---|---|
| 높음 | 관리자 보안 | `/admin/*` 요청에 대한 로그인/권한 확인 filter 또는 공통 guard가 필요하다. |
| 높음 | 비밀번호 보안 | 회원가입, 로그인, 비밀번호 재설정에서 평문 password 저장/비교를 hash 기반으로 변경해야 한다. |
| 높음 | 예약 중복 방지 | 예약 가능 여부 확인과 insert 사이에 동시 요청이 들어오면 중복 예약이 생길 수 있다. DB unique constraint 또는 transaction 처리가 필요하다. |
| 높음 | XSS 방지 | 남아 있는 scriptlet JSP와 직접 출력 값을 `<c:out>` 또는 escape helper 중심으로 정리해야 한다. |
| 중간 | 회원 JSP process 제거 | `views/member/process/*.jsp`, `idDup.jsp`, `joinComplete.jsp`, `myPageInfo.jsp`, `withdrawUser.jsp`의 Service 호출을 Servlet controller로 옮겨야 한다. |
| 중간 | 관리자 진료과 process JSP 제거 | `adminDepartmentListViewProcess.jsp`, `adminDepartmentAddModalProcess.jsp`를 Servlet 기반 등록/수정 흐름으로 정리해야 한다. |
| 중간 | Error handling | member/admin DAO와 Service에 남아 있는 `printStackTrace()`와 단순 `null`, `false`, `0` 반환 흐름을 logging과 명확한 실패 처리로 정리해야 한다. |
| 낮음 | legacy URL 정리 | 예약 legacy JSP URL은 호환성을 위해 `web.xml`에서 새 Servlet으로 연결되어 있다. 내부 링크 정리가 끝난 뒤 제거 여부를 결정하면 된다. |
| 낮음 | Tomcat/DB 수동 검증 | 예약 AJAX, 예약 등록/취소, 관리자 의료진 목록/상세, 마이페이지 예약 취소는 실제 Tomcat과 DB에서 화면 기준 검증이 필요하다. |
