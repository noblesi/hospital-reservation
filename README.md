# Hospital Reservation

Hospital reservation project for model1 practice.

## 역할분담

| 담당 | 업무 영역 | 클래스 설계 및 비즈니스 로직 | 홈페이지 UI |
|---|---|---|---|
| 박정욱 | 회원/인증/마이페이지 | `LoginService`, `LoginDAO`, `MemberRegisterService`, `MemberRegisterDAO`, `FindAccountService`, `FindAccountDAO`, `UserMyPageService`, `UserMyPageDAO`, `UpdateUserInfoService`, `UpdateUserInfoDAO` | 로그인, 회원가입, 아이디/비밀번호 찾기, 마이페이지, 개인정보 수정, 예약 내역, 진료 내역 화면 |
| 조창완 | 사용자 예약 | `UserAppointmentService`, `UserAppointmentDAO`, `UserAppointmentRequestDTO`, `UserAppointmentOptionDTO`, `UserAppointmentConfirmDTO` | 진료과 선택, 의사 선택, 날짜/시간 선택, 예약 확인, 예약 완료, 예약 취소 화면 |
| 이미연 | 관리자 회원/예약/메모 | `AdminMemberService`, `AdminMemberDAO`, `AdminAppointmentService`, `AdminAppointmentDAO`, `AdminMemoService`, `AdminMemoDAO` | 관리자 회원 목록/상세, 예약 목록/상세, 예약 승인/취소, 예약 상태 변경, 회원 메모 관리 화면 |
| 채대경 | 의료진/진료과 관리 | `AdminDoctorService`, `AdminDoctorDAO`, `AdminDepartmentService`, `AdminDepartmentDAO`, `DoctorService`, `DoctorDAO`, `DepartmentService`, `DepartmentDAO` | 관리자 의사 등록/수정, 의사 스케줄 관리, 진료과 등록/수정, 사용자 의료진 소개, 사용자 진료과 소개 화면 |
| 김민성 | 게시판/메인/대시보드/공통 | `BoardService`, `BoardDAO`, `AdminBoardService`, `AdminBoardDAO`, `MainPageService`, `AdminDashboardService`, `AdminDashboardDAO`, `PaginationUtil`, `DBConnection` | 메인 페이지, 공지사항/FAQ, 관리자 게시판, 관리자 대시보드, 공통 header/footer/nav/layout |

## 공통 작업 기준

- 각 담당자는 자신이 맡은 기능의 `DTO`, `DAO`, `Service`, UI 화면을 함께 설계한다.
- 목록 화면은 검색, 필터, pagination, empty state를 포함한다.
- 등록/수정 화면은 입력값 검증과 실패 처리 흐름을 포함한다.
- `getTotalCount`는 목록 pagination 계산에 필요한 전체 데이터 개수를 조회할 때 사용한다.
- `PaginationUtil`은 전체 개수 조회가 아니라 페이지 수, 시작 번호, 끝 번호 계산만 담당한다.
- `AdminDoctorService`의 의사 스케줄 수정 메서드는 `updateDoctorSchedule(doctorLicenseNo : int, schedules : List<DoctorScheduleDTO>) : boolean` 이름을 사용한다.
- 공통 DTO인 `MemberDTO`, `DoctorDTO`, `DepartmentDTO`, `BoardPostDTO`, `DoctorScheduleDTO`의 필드 변경은 팀 전체에 공유한다.
- UI 공통 요소인 header, footer, navigation, button, table, form, pagination 스타일은 5번 담당자가 기준을 잡고 팀 전체가 맞춘다.

## 클래스 역할 기준

| Layer | 역할 | 작성 기준 |
|---|---|---|
| Controller | 요청 파라미터 수집, Service 호출, 화면 이동 결정 | SQL 작성 금지, 복잡한 비즈니스 로직 작성 금지 |
| Service | 비즈니스 로직 처리, DAO 호출 순서 제어, 성공/실패 판단 | transaction 성격의 흐름은 Service에서 관리 |
| DAO | DB 연결, SQL 실행, ResultSet을 DTO로 변환 | 화면 로직 작성 금지, boolean 대신 영향 받은 row 수 `int` 반환 |
| DTO | 계층 간 데이터 전달 | 필드, getter/setter 중심으로 단순하게 유지 |
| Util | 공통 계산 또는 변환 | DB 접근 금지, 특정 도메인 로직 의존 금지 |

## 메서드 작성 공통 규칙

- 메서드명은 동사로 시작하고 기능이 드러나게 작성한다.
- 목록 조회는 `search...List` 또는 `select...List`를 사용한다.
- 상세 조회는 `search...Detail` 또는 `select...Detail`을 사용한다.
- 등록은 Service에서 `register...`, DAO에서 `insert...`를 사용한다.
- 수정은 Service에서 `modify...` 또는 `update...`, DAO에서 `update...`를 사용한다.
- 삭제는 Service에서 `remove...`, DAO에서 `delete...`를 사용한다.
- 상태 변경은 `change...Status`처럼 상태 변경 의도를 드러낸다.
- 중복 확인은 `check...Duplicate`, `check...Name`, `check...No`처럼 boolean 의미가 드러나게 작성한다.
- 전체 개수 조회는 Service에서 `getTotalCount`, DAO에서 `select...TotalCnt`를 사용한다.
- 메서드 하나는 하나의 책임만 갖도록 작성한다.
- 메서드 내부에서 같은 코드가 반복되면 private helper 메서드로 분리한다.

## 메서드명 예시

| 기능 | Service | DAO |
|---|---|---|
| 회원 목록 전체 개수 | `getTotalCount(searchDTO)` | `selectTotalCnt(searchDTO)` |
| 회원 목록 조회 | `searchMember(searchDTO)` | `selectMember(searchDTO)` |
| 예약 목록 조회 | `searchAppointmentList(searchDTO)` | `selectAppointmentList(searchDTO)` |
| 게시글 상세 조회 | `searchPostDetail(postNo)` | `selectPostDetail(postNo)` |
| 의사 등록 | `registerDoctor(formDTO)` | `insertDoctor(doctorDTO)` |
| 의사 수정 | `modifyDoctor(formDTO)` | `updateDoctor(doctorDTO)` |
| 의사 스케줄 수정 | `updateDoctorSchedule(doctorLicenseNo, schedules)` | `deleteDoctorSchedules(doctorLicenseNo)`, `insertDoctorSchedule(scheduleDTO)` |
| 예약 취소 | `cancelAppointment(appointmentNo, reason)` | `updateCancelAppointment(appointmentNo, reason)` |
| 조회수 증가 | `increaseViewCount(postNo)` | `updateViewCount(postNo)` |

## 반환 타입 기준

- Service의 등록, 수정, 삭제, 상태 변경 메서드는 성공 여부를 `boolean`으로 반환한다.
- DAO의 insert, update, delete 메서드는 영향 받은 row 수를 `int`로 반환한다.
- 목록 조회 메서드는 `List<...DTO>`를 반환한다.
- 상세 조회 메서드는 단일 DTO를 반환하고, 데이터가 없을 수 있으면 `null` 처리 기준을 호출부와 맞춘다.
- count 조회 메서드는 `int`를 반환한다.
- 단순 실행 결과가 필요 없는 부가 작업은 `void`를 사용할 수 있다.

## Pagination 규칙

- 목록 조회가 필요한 화면은 `SearchDTO`에 `startNum`, `endNum`을 포함한다.
- Controller 또는 Service에서 현재 페이지 번호와 page scale을 기준으로 조회 범위를 계산한다.
- 전체 개수는 각 Service의 `getTotalCount(searchDTO)`를 통해 조회한다.
- `PaginationUtil.calculateTotalPage(totalCnt, pageScale)`로 전체 페이지 수를 계산한다.
- `PaginationUtil.calculateStartNum(currentPage, pageScale)`로 시작 번호를 계산한다.
- `PaginationUtil.calculateEndNum(startNum, pageScale)`로 끝 번호를 계산한다.
- `PaginationUtil`은 DAO를 직접 호출하지 않는다.

## 입력값 검증 및 예외 처리 기준

- Controller는 필수 파라미터 누락 여부를 1차로 확인한다.
- Service는 비즈니스 규칙 위반 여부를 확인한다.
- DAO는 SQL 실행 중 발생한 예외를 로그로 확인할 수 있게 처리한다.
- 사용자가 입력한 값은 SQL 문자열에 직접 연결하지 않고 `PreparedStatement`를 사용한다.
- 등록/수정/삭제 실패 시 Service는 `false`를 반환하고 Controller는 실패 화면 또는 메시지로 처리한다.
- 비밀번호, 주민번호, 토큰 같은 민감 정보는 로그에 출력하지 않는다.

## UI 작성 공통 규칙

- 사용자 페이지는 `userHeader.jsp`, `userBreadcrumb.jsp`, `userFooter.jsp`를 공통으로 사용한다.
- 관리자 페이지는 `adminHeader.jsp`, `adminSidebar.jsp`, `admin-content` 구조를 사용한다.
- 현재 사용자 메뉴는 `activeMenu`, 관리자 메뉴는 `adminMenu` request 변수로 표시한다.
- 목록 화면은 검색 영역, 결과 table, pagination 영역을 같은 순서로 배치한다.
- 등록/수정 form은 label, input name, DTO 필드명을 최대한 일치시킨다.
- 실패 메시지와 빈 목록 메시지는 화면에 표시한다.
- 버튼 이름은 등록, 수정, 삭제, 취소, 검색처럼 사용자가 이해하기 쉬운 단어를 사용한다.

## 통합 우선순위

1. 공통 DB 연결, pagination, 공통 DTO 구조 확정
2. 각 도메인의 DAO 및 SQL 흐름 설계
3. Service에서 비즈니스 로직과 성공/실패 처리 구현
4. 담당 UI 화면 연결
5. 회원가입 → 로그인 → 진료과/의사 조회 → 예약 → 관리자 예약 확인 흐름으로 통합 테스트
