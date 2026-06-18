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

public class DoctorStatusDTO {
	
	private String statusCode;
	private String statusName;
	
}// class
