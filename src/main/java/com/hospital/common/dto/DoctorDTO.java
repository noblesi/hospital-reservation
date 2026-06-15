package com.hospital.common.dto;

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

public class DoctorDTO {
	private int doctorLicenseNo;
	private int deptNo;
	private String name;
	private String phoneNum;
	private String positionCode;
	private String introTitle;
	private String introContent;
	private String thumbnailUrl;
	private String detailImageUrl;
	private String createDate;
	private String specialty;
	private String statusCode;
}// class
