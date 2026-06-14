# Hospital Reservation

Hospital reservation project for model1 practice.

## 역할분담

| 담당 | 업무 영역 | 클래스 설계 및 비즈니스 로직 | 홈페이지 UI |
|---|---|---|---|
| 1번 | 회원/인증/마이페이지 | `LoginService`, `LoginDAO`, `MemberRegisterService`, `MemberRegisterDAO`, `FindAccountService`, `FindAccountDAO`, `UserMyPageService`, `UserMyPageDAO`, `UpdateUserInfoService`, `UpdateUserInfoDAO` | 로그인, 회원가입, 아이디/비밀번호 찾기, 마이페이지, 개인정보 수정, 예약 내역, 진료 내역 화면 |
| 2번 | 사용자 예약 | `UserAppointmentService`, `UserAppointmentDAO`, `UserAppointmentRequestDTO`, `UserAppointmentOptionDTO`, `UserAppointmentConfirmDTO` | 진료과 선택, 의사 선택, 날짜/시간 선택, 예약 확인, 예약 완료, 예약 취소 화면 |
| 3번 | 관리자 회원/예약/메모 | `AdminMemberService`, `AdminMemberDAO`, `AdminAppointmentService`, `AdminAppointmentDAO`, `AdminMemoService`, `AdminMemoDAO` | 관리자 회원 목록/상세, 예약 목록/상세, 예약 승인/취소, 예약 상태 변경, 회원 메모 관리 화면 |
| 4번 | 의료진/진료과 관리 | `AdminDoctorService`, `AdminDoctorDAO`, `AdminDepartmentService`, `AdminDepartmentDAO`, `DoctorService`, `DoctorDAO`, `DepartmentService`, `DepartmentDAO` | 관리자 의사 등록/수정, 의사 스케줄 관리, 진료과 등록/수정, 사용자 의료진 소개, 사용자 진료과 소개 화면 |
| 5번 | 게시판/메인/대시보드/공통 | `BoardService`, `BoardDAO`, `AdminBoardService`, `AdminBoardDAO`, `MainPageService`, `AdminDashboardService`, `AdminDashboardDAO`, `PaginationUtil`, `DBConnection` | 메인 페이지, 공지사항/FAQ, 관리자 게시판, 관리자 대시보드, 공통 header/footer/nav/layout |

## 공통 작업 기준

- 각 담당자는 자신이 맡은 기능의 `DTO`, `DAO`, `Service`, UI 화면을 함께 설계한다.
- 목록 화면은 검색, 필터, pagination, empty state를 포함한다.
- 등록/수정 화면은 입력값 검증과 실패 처리 흐름을 포함한다.
- `totalCnt`는 목록 pagination 계산에 사용하며, `PaginationUtil`과 함께 일관되게 처리한다.
- `AdminDoctorService`의 의사 스케줄 수정 메서드는 `updateDoctorSchedule(doctorLicenseNo : int, schedules : List<DoctorScheduleDTO>) : boolean` 이름을 사용한다.
- 공통 DTO인 `MemberDTO`, `DoctorDTO`, `DepartmentDTO`, `BoardPostDTO`, `DoctorScheduleDTO`의 필드 변경은 팀 전체에 공유한다.
- UI 공통 요소인 header, footer, navigation, button, table, form, pagination 스타일은 5번 담당자가 기준을 잡고 팀 전체가 맞춘다.

## 통합 우선순위

1. 공통 DB 연결, pagination, 공통 DTO 구조 확정
2. 각 도메인의 DAO 및 SQL 흐름 설계
3. Service에서 비즈니스 로직과 성공/실패 처리 구현
4. 담당 UI 화면 연결
5. 회원가입 → 로그인 → 진료과/의사 조회 → 예약 → 관리자 예약 확인 흐름으로 통합 테스트
