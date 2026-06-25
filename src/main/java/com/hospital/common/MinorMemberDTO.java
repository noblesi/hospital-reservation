package com.hospital.common;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 보호자 회원에게 연결된 미성년자 회원 정보를 전달하는 DTO
 */
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
	private String minorGenderFM;
}//MinorMemberDTO
