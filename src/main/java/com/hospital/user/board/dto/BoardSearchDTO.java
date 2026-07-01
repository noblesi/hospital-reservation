package com.hospital.user.board.dto;

import com.hospital.common.dto.BaseSearchDTO;

public class BoardSearchDTO extends BaseSearchDTO {
    public static final String CATEGORY_NOTICE = "N";
    public static final String CATEGORY_FAQ = "F";

    private String category = CATEGORY_NOTICE;
    private String searchType = "titleContent";
    private String keyword;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        if (CATEGORY_FAQ.equalsIgnoreCase(category) || "FAQ".equalsIgnoreCase(category)) {
            this.category = CATEGORY_FAQ;
            return;
        }

        this.category = CATEGORY_NOTICE;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        if ("title".equals(searchType) || "content".equals(searchType)) {
            this.searchType = searchType;
            return;
        }

        this.searchType = "titleContent";
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword == null ? null : keyword.trim();
    }

    public boolean hasKeyword() {
        return keyword != null && !keyword.isBlank();
    }

    public String getCategoryName() {
        return CATEGORY_FAQ.equals(category) ? "FAQ" : "공지사항";
    }
}
