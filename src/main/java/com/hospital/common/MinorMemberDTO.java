package com.hospital.common;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class MinorMemberDTO {
	private String patientNo;
	private String relationship;
	private String minorName;
	private Date minorBirthDate;
}//MinorMemberDTO
