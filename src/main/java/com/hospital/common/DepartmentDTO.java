package com.hospital.common;

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

public class DepartmentDTO {
	private String deptNo;
	private String deptName;
	private String description;
	private String isActiveYn;
	private String deptLoc;
}// class
