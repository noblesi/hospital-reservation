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
public class UserAppointmentRequestDTO {
	private String patientNo;
	private int doctorLicenseNo;
	private Date appointmentDate;
	private String appointmentTime;
	private String requirement;
	private String status;
	private Date createdAt;
	private String reservationTargetType;
	private String guardianRelation;
}
