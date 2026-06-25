# Hospital Reservation

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

## 역할분담

| 담당 | 업무 영역 | 클래스 설계 및 비즈니스 로직 | 홈페이지 UI |
|---|---|---|---|
| 박정욱 | 회원/인증/마이페이지 | `LoginService`, `LoginDAO`, `MemberRegisterService`, `MemberRegisterDAO`, `FindAccountService`, `FindAccountDAO`, `UserMyPageService`, `UserMyPageDAO`, `UpdateUserInfoService`, `UpdateUserInfoDAO` | 로그인, 회원가입, 아이디/비밀번호 찾기, 마이페이지, 개인정보 수정, 예약 내역, 진료 내역 화면 |
| 조창완 | 사용자 예약 | `UserAppointmentService`, `UserAppointmentDAO`, `UserAppointmentRequestDTO`, `UserAppointmentOptionDTO`, `UserAppointmentConfirmDTO` | 진료과 선택, 의사 선택, 날짜/시간 선택, 예약 확인, 예약 완료, 예약 취소 화면 |
| 이미연 | 관리자 회원/예약/메모 | `AdminMemberService`, `AdminMemberDAO`, `AdminAppointmentService`, `AdminAppointmentDAO`, `AdminMemoService`, `AdminMemoDAO` | 관리자 회원 목록/상세, 예약 목록/상세, 예약 승인/취소, 예약 상태 변경, 회원 메모 관리 화면 |
| 채대경 | 의료진/진료과 관리 | `AdminDoctorService`, `AdminDoctorDAO`, `AdminDepartmentService`, `AdminDepartmentDAO`, `DoctorService`, `DoctorDAO`, `DepartmentService`, `DepartmentDAO` | 관리자 의사 등록/수정, 의사 스케줄 관리, 진료과 등록/수정, 사용자 의료진 소개, 사용자 진료과 소개 화면 |
| 김민성 | 게시판/메인/대시보드/공통 | `BoardService`, `BoardDAO`, `AdminBoardService`, `AdminBoardDAO`, `MainPageService`, `AdminDashboardService`, `AdminDashboardDAO`, `PaginationUtil`, `BaseSearchDTO`, `DBConnection` | 메인 페이지, 공지사항/FAQ, 관리자 게시판, 관리자 대시보드, 공통 header/footer/nav/layout, 공통 메시지/페이징 |

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
