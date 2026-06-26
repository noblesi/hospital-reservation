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
	/** 미성년자 성별(M: 남자, F: 여자) */
	private String minorGenderFM;
}//MinorMemberDTO
