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
	/** 보호자 회원과 연결되는 환자번호 */
	private String patientNo;
	/** 보호자와 미성년자의 관계 */
	private String relationship;
	/** 미성년자 이름 */
	private String minorName;
	/** 미성년자 생년월일 */
	private Date minorBirthDate;
}//MinorMemberDTO
