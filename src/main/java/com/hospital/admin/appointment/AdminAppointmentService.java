package com.hospital.admin.appointment;

import com.hospital.admin.appointment.dto.AdminAppointmentSearchDTO;
import com.hospital.common.dto.AppointmentDTO;
import com.hospital.common.util.PaginationUtil;

import java.util.List;

// 관리자 예약 관리 Service
public class AdminAppointmentService {

    // 관리자 예약 DAO
    private final AdminAppointmentDAO adminAppointmentDAO = new AdminAppointmentDAO();

    // 예약 목록 조회
    // - 검색 조건과 페이징 정보를 적용한 뒤 DAO에 전달
    public List<AppointmentDTO> getAppointmentList(AdminAppointmentSearchDTO searchDTO) {
        applyPaging(searchDTO);
        return adminAppointmentDAO.selectAppointmentList(searchDTO);
    }

    // 예약 총 건수 조회
    // - 페이지 수 계산에 사용
    public int getAppointmentCount(AdminAppointmentSearchDTO searchDTO) {
        return adminAppointmentDAO.selectAppointmentCount(searchDTO);
    }

    // 예약 상세 조회
    // - 예약번호로 예약 1건 조회
    public AppointmentDTO getAppointmentDetail(int appointmentNo) {
        return getAppointmentDetail(String.valueOf(appointmentNo));
    }

    // 예약 상세 조회
    // - 예약번호로 예약 1건 조회
    public AppointmentDTO getAppointmentDetail(String appointmentNo) {
        return adminAppointmentDAO.selectAppointmentDetail(appointmentNo);
    }

    // 예약 목록과 pagination 정보를 한 번에 조회
    public AdminAppointmentPage getAppointmentPage(AdminAppointmentSearchDTO searchDTO) {
        if (searchDTO == null) {
            searchDTO = new AdminAppointmentSearchDTO();
        }

        PaginationUtil.Pagination pagination = getPagination(searchDTO);
        applyPagination(searchDTO, pagination);
        return new AdminAppointmentPage(adminAppointmentDAO.selectAppointmentList(searchDTO), pagination);
    }

    // 예약 목록 pagination 계산
    public PaginationUtil.Pagination getPagination(AdminAppointmentSearchDTO searchDTO) {
        if (searchDTO == null) {
            searchDTO = new AdminAppointmentSearchDTO();
        }

        int totalCount = adminAppointmentDAO.selectAppointmentCount(searchDTO);
        return PaginationUtil.create(searchDTO.getCurrentPage(), totalCount, searchDTO.getPageScale());
    }

    // 예약 승인 처리
    public boolean approveAppointment(int appointmentNo) {
        return approveAppointment(String.valueOf(appointmentNo));
    }

    // 예약 승인 처리
    public boolean approveAppointment(String appointmentNo) {
        return adminAppointmentDAO.updateAppointmentStatus(appointmentNo, "예약완료") > 0;
    }

    // 예약 취소 처리
    public boolean cancelAppointment(int appointmentNo) {
        return cancelAppointment(String.valueOf(appointmentNo));
    }

    // 예약 취소 처리
    public boolean cancelAppointment(String appointmentNo) {
        return adminAppointmentDAO.updateAppointmentStatus(appointmentNo, "예약취소") > 0;
    }

    // 예약 상태 변경 처리
    public boolean changeAppointmentStatus(int appointmentNo, String status) {
        return changeAppointmentStatus(String.valueOf(appointmentNo), status);
    }

    // 예약 상태 변경 처리
    public boolean changeAppointmentStatus(String appointmentNo, String status) {
        if (!isSupportedStatus(status)) {
            return false;
        }

        return adminAppointmentDAO.updateAppointmentStatus(appointmentNo, status) > 0;
    }

    // 예약일이 지난 예약완료 건을 실제 DB 상태값으로 진료완료 처리
    public int completeExpiredAppointments() {
        return adminAppointmentDAO.updateExpiredCompletedAppointments();
    }

    // 페이징 계산
    // - currentPage와 pageScale로 startNum 계산
    private void applyPaging(AdminAppointmentSearchDTO searchDTO) {

        if (searchDTO == null) {
            return;
        }

        // 페이지 번호가 1보다 작으면 1로 고정
        int currentPage = Math.max(searchDTO.getCurrentPage(), 1);

        // 페이지당 건수가 1보다 작으면 기본값 10 사용
        int pageScale = Math.max(searchDTO.getPageScale(), 10);

        // 시작 위치 계산
        // 예) 1페이지 -> 0, 2페이지 -> 10, 3페이지 -> 20
        int startNum = (currentPage - 1) * pageScale;

        searchDTO.setCurrentPage(currentPage);
        searchDTO.setPageScale(pageScale);
        searchDTO.setStartNum(startNum);
        searchDTO.setEndNum(startNum + pageScale);
    }

    private void applyPagination(AdminAppointmentSearchDTO searchDTO, PaginationUtil.Pagination pagination) {
        searchDTO.setCurrentPage(pagination.getCurrentPage());
        searchDTO.setPageScale(pagination.getPageScale());
        searchDTO.setStartNum((pagination.getCurrentPage() - 1) * pagination.getPageScale());
        searchDTO.setEndNum(searchDTO.getStartNum() + pagination.getPageScale());
    }

    private boolean isSupportedStatus(String status) {
        return "예약대기".equals(status)
                || "예약완료".equals(status)
                || "예약취소".equals(status);
    }

    public static class AdminAppointmentPage {
        private final List<AppointmentDTO> appointmentList;
        private final PaginationUtil.Pagination pagination;

        public AdminAppointmentPage(List<AppointmentDTO> appointmentList, PaginationUtil.Pagination pagination) {
            this.appointmentList = appointmentList;
            this.pagination = pagination;
        }

        public List<AppointmentDTO> getAppointmentList() {
            return appointmentList;
        }

        public PaginationUtil.Pagination getPagination() {
            return pagination;
        }
    }
}
