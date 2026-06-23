package com.hospital.admin.department.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AdminDepartmentSearchDTO {

	private int startNum;
	private int endNum;
	private String field;
	private String keyword;
	private String isActiveYn;
	
}// class
