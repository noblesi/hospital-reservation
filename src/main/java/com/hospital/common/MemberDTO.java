package com.hospital.common;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * memberDTO
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MemberDTO {
	private String patientNo;
	private String loginId; 
	private String passWord;
	private String name;
	private Date birthDate;
	private String genderFM;
	private String phoneNumber;
	private String email;
	private String zipCode;
	private String adress;
	private String adressDetail;
	private Date registeredAt;
	private String hasMinorMemberYn;
	private String ip;
	private String isWithdrawnYn;
	private Date withdrawnAt;
}// memberDTO

