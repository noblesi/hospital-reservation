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
