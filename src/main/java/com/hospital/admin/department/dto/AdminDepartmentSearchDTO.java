package com.hospital.admin.department.dto;

import com.hospital.common.dto.BaseSearchDTO;

public class AdminDepartmentSearchDTO extends BaseSearchDTO {

	private String field = "all";
	private String keyword;
	private String isActiveYn;

	public String getField() {
		return field;
	}//getField

	public void setField(String field) {
		if("deptName".equals(field) || "description".equals(field) || "deptLoc".equals(field)) {
			this.field = field;
			return;
		}// end if

		this.field = "all";
	}//setField

	public String getKeyword() {
		return keyword;
	}//getKeyword

	public void setKeyword(String keyword) {
		this.keyword = keyword == null ? null : keyword.trim();
	}//setKeyword

	public String getIsActiveYn() {
		return isActiveYn;
	}//getIsActiveYn

	public void setIsActiveYn(String isActiveYn) {
		if("Y".equalsIgnoreCase(isActiveYn) || "N".equalsIgnoreCase(isActiveYn)) {
			this.isActiveYn = isActiveYn.toUpperCase();
			return;
		}// end if

		this.isActiveYn = null;
	}//setIsActiveYn

	public boolean hasKeyword() {
		return keyword != null && !keyword.isBlank();
	}//hasKeyword

	public boolean hasActiveCondition() {
		return isActiveYn != null && !isActiveYn.isBlank();
	}//hasActiveCondition
}// class
