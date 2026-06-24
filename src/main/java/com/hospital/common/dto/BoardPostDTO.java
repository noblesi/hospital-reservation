package com.hospital.common.dto;

import java.sql.Timestamp;

public class BoardPostDTO {
    private int postId;
    private String category;
    private String title;
    private String content;
    private String writerId;
    private String writerName;
    private String noticeYn = "N";
    private String displayYn = "Y";
    private int viewCount;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriterId() {
        return writerId;
    }

    public void setWriterId(String writerId) {
        this.writerId = writerId;
    }

    public String getWriterName() {
        return writerName;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }

    public String getNoticeYn() {
        return noticeYn;
    }

    public void setNoticeYn(String noticeYn) {
        this.noticeYn = normalizeYn(noticeYn);
    }

    public String getDisplayYn() {
        return displayYn;
    }

    public void setDisplayYn(String displayYn) {
        this.displayYn = normalizeYn(displayYn);
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isNotice() {
        return "Y".equals(noticeYn);
    }

    public boolean isDisplay() {
        return "Y".equals(displayYn);
    }

    private String normalizeYn(String value) {
        return "Y".equalsIgnoreCase(value) ? "Y" : "N";
    }
}
