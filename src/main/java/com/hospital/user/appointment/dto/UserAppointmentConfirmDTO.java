package com.hospital.user.appointment.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserAppointmentConfirmDTO {
	private String appointmentNo;
	private String patientNo;
	private String patientName;
	private String phoneNumber;
	private String email;
	private String deptNo;
	private String deptName;
	private int doctorLicenseNo;
	private String doctorName;
	private Date appointmentDate;
	private String appointmentTime;
	private String requirement;
	private String status;
	private Date createdAt;
}
