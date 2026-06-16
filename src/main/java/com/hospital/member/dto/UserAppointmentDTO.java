package com.hospital.member.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 예약 내역 DTO
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserAppointmentDTO {

	private String appointmentNo;
	private String patientNo;
	private int doctorLicenseNo;

	private Date appointmentDate;
	private String appointmentTime;

	private String requirement;
	private String status;

	private Date createdAt;
	private Date canceledAt;

	// 조인 결과
	private String departmentName;
	private String doctorName;

}