package com.hospital.admin.appointment;

import com.hospital.admin.appointment.dto.AdminAppointmentSearchDTO;
import com.hospital.common.dto.AppointmentDTO;

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
        return adminAppointmentDAO.selectAppointmentDetail(appointmentNo);
    }

    // 예약 승인 처리
    public boolean approveAppointment(int appointmentNo) {
        return adminAppointmentDAO.updateAppointmentStatus(appointmentNo, "APPROVED") > 0;
    }

    // 예약 취소 처리
    public boolean cancelAppointment(int appointmentNo) {
        return adminAppointmentDAO.updateAppointmentStatus(appointmentNo, "CANCELED") > 0;
    }

    // 예약 상태 변경 처리
    public boolean changeAppointmentStatus(int appointmentNo, String status) {
        return adminAppointmentDAO.updateAppointmentStatus(appointmentNo, status) > 0;
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
}
