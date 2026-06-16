package com.hospital.member.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 진료 기록 DTO
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserMedicalRecordDTO {

	private String recordNo;
	private String appointmentNo;
	private String patientNo;

	private Date treatmentDate;

	private String status;

	// 조인 결과
	private String deptName;
	private String doctorName;

}