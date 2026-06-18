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

public class DoctorEducationDTO {
	
	private int educationNo;
	private int doctorLicenseNo;
	private String educationYear;
	private String educationContent;
	
}//class
