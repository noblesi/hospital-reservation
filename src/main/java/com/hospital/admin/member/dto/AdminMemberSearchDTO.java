package com.hospital.admin.member.dto;

// 관리자 회원 목록 검색 조건 DTO
public class AdminMemberSearchDTO {

    // 현재 페이지 번호
    private int currentPage = 1;

    // 페이지당 게시물 수
    private int pageScale = 10;

    // 조회 시작 위치
    private int startNum;

    // 조회 끝 위치
    private int endNum;

    // 검색 타입 (예: loginId, memberName, email)
    private String searchType;

    // 검색 키워드
    private String searchKeyword;

    // 회원 상태
    private String status;

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageScale() {
		return pageScale;
	}

	public void setPageScale(int pageScale) {
		this.pageScale = pageScale;
	}

	public int getStartNum() {
		return startNum;
	}

	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}

	public int getEndNum() {
		return endNum;
	}

	public void setEndNum(int endNum) {
		this.endNum = endNum;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}

