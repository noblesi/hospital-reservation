package com.hospital.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MinorMemberDTO {
	private String patientNo;
	private String relationship;
	private String minorName;
	private String minorBirthDate;
}//MinorMemberDTO
