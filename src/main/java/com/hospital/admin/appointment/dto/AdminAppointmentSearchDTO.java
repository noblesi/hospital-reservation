package com.hospital.admin.appointment.dto;

// 관리자 예약 목록 검색 조건 DTO
public class AdminAppointmentSearchDTO {

    // 현재 페이지 번호
    private int currentPage = 1;

    // 페이지당 조회 건수
    private int pageScale = 10;

    // 조회 시작 위치
    private int startNum;

    // 조회 끝 위치
    private int endNum;

    // 검색 타입 (예: patientName, doctorName)
    private String searchType;

    // 검색 키워드
    private String searchKeyword;

    // 예약 상태 (예: WAITING, APPROVED, CANCELED)
    private String status;

    // 조회 시작일
    private String startDate;

    // 조회 종료일
    private String endDate;

    public int getCurrentPage() { return currentPage; }
    public void setCurrentPage(int currentPage) { this.currentPage = currentPage; }

    public int getPageScale() { return pageScale; }
    public void setPageScale(int pageScale) { this.pageScale = pageScale; }

    public int getStartNum() { return startNum; }
    public void setStartNum(int startNum) { this.startNum = startNum; }

    public int getEndNum() { return endNum; }
    public void setEndNum(int endNum) { this.endNum = endNum; }

    public String getSearchType() { return searchType; }
    public void setSearchType(String searchType) { this.searchType = searchType; }

    public String getSearchKeyword() { return searchKeyword; }
    public void setSearchKeyword(String searchKeyword) { this.searchKeyword = searchKeyword; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
}
