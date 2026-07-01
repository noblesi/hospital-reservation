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
public class UserAppointmentShowDTO {
	private String appointmentNo;
	private String thumbnailUrl;
	private String deptName;
	private String doctorName;
	private Date createdAt;
	private Date appointmentDate;
	private String appointmentTime;
	private String deptLoc;
}
