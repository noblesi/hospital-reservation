package com.hospital.admin.board.dto;

import com.hospital.user.board.dto.BoardSearchDTO;

public class AdminBoardSearchDTO extends BoardSearchDTO {
    private String displayYn;

    public String getDisplayYn() {
        return displayYn;
    }

    public void setDisplayYn(String displayYn) {
        if ("Y".equalsIgnoreCase(displayYn) || "N".equalsIgnoreCase(displayYn)) {
            this.displayYn = displayYn.toUpperCase();
            return;
        }

        this.displayYn = null;
    }

    public boolean hasDisplayCondition() {
        return displayYn != null && !displayYn.isBlank();
    }
}
