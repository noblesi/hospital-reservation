package com.hospital.common.dto;

import com.hospital.user.board.dto.BoardSearchDTO;

import java.sql.Timestamp;

public class BoardPostDTO {
    private int postId;
    private String category;
    private String title;
    private String content;
    private String writerId;
    private String writerName;
    private int viewCount;
    private Timestamp createdAt;

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
        if (BoardSearchDTO.CATEGORY_FAQ.equalsIgnoreCase(category) || "FAQ".equalsIgnoreCase(category)) {
            this.category = BoardSearchDTO.CATEGORY_FAQ;
            return;
        }

        this.category = BoardSearchDTO.CATEGORY_NOTICE;
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

    public boolean isNotice() {
        return BoardSearchDTO.CATEGORY_NOTICE.equals(category);
    }

    public String getCategoryName() {
        return BoardSearchDTO.CATEGORY_FAQ.equals(category) ? "FAQ" : "공지사항";
    }
}
