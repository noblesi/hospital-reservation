# Hospital Reservation

## 폴더 구조 기준

```text
src/main/java/com/hospital
├── common
│   ├── DBConnection.java
│   ├── dto
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
| 김민성 | 게시판/메인/대시보드/공통 | `BoardService`, `BoardDAO`, `AdminBoardService`, `AdminBoardDAO`, `MainPageService`, `AdminDashboardService`, `AdminDashboardDAO`, `PaginationUtil`, `DBConnection` | 메인 페이지, 공지사항/FAQ, 관리자 게시판, 관리자 대시보드, 공통 header/footer/nav/layout |

## 공통 SearchDTO 기준

- 목록 조회가 필요한 기능은 각 도메인별 검색 DTO를 만든다.
- 검색 DTO 이름은 기능이 드러나도록 `MemberSearchDTO`, `AppointmentSearchDTO`, `BoardSearchDTO`처럼 작성한다.
- 검색 DTO에는 목록 조회에 공통으로 필요한 `currentPage`, `pageScale`, `startNum`, `endNum` 필드를 둔다.
- 검색어가 필요한 화면은 `searchType`, `searchKeyword` 필드를 사용한다.
- 날짜 조건이 필요한 화면은 `startDate`, `endDate` 필드를 사용한다.
- 상태 조건이 필요한 화면은 `status` 필드를 사용한다.
- JSP는 request parameter를 검색 DTO에 담고, Service는 검색 DTO를 DAO에 전달한다.
- DAO는 검색 DTO 값을 SQL 조건과 조회 범위에 사용하되, 사용자가 입력한 값은 반드시 `PreparedStatement`로 바인딩한다.
- 검색 조건이 없는 경우에도 빈 문자열과 `null` 처리 기준을 각 DAO에서 일관되게 맞춘다.

## 공통 메시지 처리 기준

- 등록, 수정, 삭제, 상태 변경 결과 메시지는 요청을 처리한 JSP에서 request 또는 session scope에 담아 화면으로 전달한다.
- 단순 화면 이동 후 바로 보여줄 메시지는 request scope를 사용한다.
- redirect 이후에도 보여줘야 하는 메시지는 session scope에 담고, 화면에서 출력한 뒤 제거한다.
- 메시지 속성 이름은 `message`를 기본으로 사용한다.
- 에러 메시지는 `errorMessage`를 기본으로 사용한다.
- alert 처리가 필요한 경우 JSP에서 `message`, `errorMessage` 존재 여부를 확인해 출력한다.
- Service는 성공 여부를 `boolean`으로 반환하고, 사용자에게 보여줄 문구는 요청을 처리한 JSP에서 결정한다.
- DAO는 사용자 메시지를 만들지 않고 SQL 실행 결과와 예외 처리에 집중한다.
- 비밀번호, 주민번호, 토큰 같은 민감 정보는 메시지와 로그에 포함하지 않는다.

## DAO 자원 정리 및 트랜잭션 기준

- DAO는 DB 연결이 필요할 때 `DBConnection.getConnection()`을 사용한다.
- `Connection`, `PreparedStatement`, `ResultSet`은 사용 후 반드시 닫는다.
- 단일 SQL 실행은 try-with-resources 사용을 기본으로 한다.
- 여러 SQL을 하나의 작업으로 묶어야 하는 경우 Service에서 하나의 `Connection`을 만들고 DAO에 전달한다.
- 트랜잭션이 필요한 Service는 `setAutoCommit(false)`를 사용하고, 전체 작업 성공 시 `commit()`, 실패 시 `rollback()`을 수행한다.
- 트랜잭션을 직접 제어한 뒤에는 `setAutoCommit(true)`로 되돌리고 `Connection`을 닫는다.
- DAO의 insert, update, delete 메서드는 영향 받은 row 수를 `int`로 반환한다.
- DAO의 select 메서드는 조회 결과에 따라 DTO 또는 `List<DTO>`를 반환한다.
- SQL 실행 중 발생한 예외는 원인을 확인할 수 있도록 처리하되, 사용자 입력값이나 민감 정보는 그대로 출력하지 않는다.

## JSP Include 및 UI 공통 기준

- 사용자 페이지는 `views/common/userHeader.jsp`, `views/common/userBreadcrumb.jsp`, `views/common/userFooter.jsp`를 공통으로 사용한다.
- 관리자 페이지는 `views/common/adminHeader.jsp`, `views/common/adminSidebar.jsp`를 공통으로 사용한다.
- JSTL과 공통 taglib 선언은 `views/common/taglib.jsp`를 include해서 사용한다.
- 사용자 페이지의 현재 메뉴 표시는 `activeMenu` request 속성을 사용한다.
- 관리자 페이지의 현재 메뉴 표시는 `adminMenu` request 속성을 사용한다.
- 관리자 본문 영역은 `admin-content` 클래스를 기준으로 구성한다.
- 사용자 화면 스타일은 `resources/css/user-layout.css`를 우선 사용한다.
- 관리자 화면 스타일은 `resources/css/admin-layout.css`를 우선 사용한다.
- 사용자 화면 스크립트는 `resources/js/user-layout.js`, 관리자 화면 스크립트는 `resources/js/admin-layout.js`를 우선 사용한다.
- 목록 화면은 검색 영역, 결과 테이블, 페이지 영역 순서로 배치한다.
- 등록/수정 화면은 label, input name, DTO 필드명을 최대한 일치시킨다.
- 빈 목록, 입력 오류, 처리 실패 메시지는 화면에서 확인 가능하게 표시한다.
