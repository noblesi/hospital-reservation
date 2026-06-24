package com.hospital.common.dto;

import com.hospital.common.util.PaginationUtil;

/**
 * 목록 조회 검색 DTO에서 공통으로 사용하는 페이징 필드를 담는 기본 DTO입니다.
 */
public class BaseSearchDTO {
    private int currentPage = PaginationUtil.DEFAULT_CURRENT_PAGE;
    private int pageScale = PaginationUtil.DEFAULT_PAGE_SCALE;
    private int startNum = PaginationUtil.calculateStartNum(currentPage, pageScale);
    private int endNum = PaginationUtil.calculateEndNum(currentPage, pageScale);

    /**
     * 기본 페이지 값으로 검색 DTO를 생성하는 생성자입니다.
     */
    public BaseSearchDTO() {
    }

    /**
     * 현재 페이지 번호를 반환하는 메서드입니다.
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * 현재 페이지 번호를 설정하는 메서드입니다.
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = Math.max(currentPage, PaginationUtil.DEFAULT_CURRENT_PAGE);
        updatePagingRange();
    }

    /**
     * 한 페이지에 보여줄 데이터 수를 반환하는 메서드입니다.
     */
    public int getPageScale() {
        return pageScale;
    }

    /**
     * 한 페이지에 보여줄 데이터 수를 설정하는 메서드입니다.
     */
    public void setPageScale(int pageScale) {
        this.pageScale = PaginationUtil.normalizePositive(pageScale, PaginationUtil.DEFAULT_PAGE_SCALE);
        updatePagingRange();
    }

    /**
     * DAO 조회 범위의 시작 번호를 반환하는 메서드입니다.
     */
    public int getStartNum() {
        return startNum;
    }

    /**
     * DAO 조회 범위의 시작 번호를 설정하는 메서드입니다.
     */
    public void setStartNum(int startNum) {
        this.startNum = startNum;
    }

    /**
     * DAO 조회 범위의 끝 번호를 반환하는 메서드입니다.
     */
    public int getEndNum() {
        return endNum;
    }

    /**
     * DAO 조회 범위의 끝 번호를 설정하는 메서드입니다.
     */
    public void setEndNum(int endNum) {
        this.endNum = endNum;
    }

    /**
     * 계산된 페이징 정보를 검색 DTO에 반영하는 메서드입니다.
     */
    public void applyPagination(PaginationUtil.Pagination pagination) {
        if (pagination == null) {
            updatePagingRange();
            return;
        }

        this.currentPage = pagination.getCurrentPage();
        this.pageScale = pagination.getPageScale();
        this.startNum = pagination.getStartNum();
        this.endNum = pagination.getEndNum();
    }

    /**
     * 현재 페이지 번호와 페이지 크기로 DAO 조회 범위를 다시 계산하는 메서드입니다.
     */
    public void updatePagingRange() {
        this.startNum = PaginationUtil.calculateStartNum(currentPage, pageScale);
        this.endNum = PaginationUtil.calculateEndNum(currentPage, pageScale);
    }
}
