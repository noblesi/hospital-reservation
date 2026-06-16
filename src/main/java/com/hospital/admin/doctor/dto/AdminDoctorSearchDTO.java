package com.hospital.admin.doctor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class AdminDoctorSearchDTO {
	private int statNum;
	private int endNum;
	private String deptNo;
	private String positionCode;
	private String statusCode;
	private String field;
	private String keyword;
}// class
