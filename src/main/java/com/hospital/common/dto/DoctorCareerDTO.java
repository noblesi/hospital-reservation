package com.hospital.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class DoctorCareerDTO {
	private int careerNo;
	private int doctorLicenseNo;
	private String careerYear;
	private String careerContent;
}// class
